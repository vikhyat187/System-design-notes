## Functional requirements
- User can schedule or view the job.
- User can list all the submitted jobs with current status.
- Jobs can be run once or recurring. Jobs should be executed within X threshold time after the defined scheduled time. (let x = 15 minutes)
- Individual job execution time is no more than X minutes. (let x = 5 minutes)
- Jobs can also have priority. Job the with higher priority should be executed first than lower priority
- Job output should be stored inside file system
- Candidate: Should the scheduler support recurring tasks or only one-time scheduled tasks?
- Interviewer: Good question. For this version, let’s support both one-time and recurring tasks (e.g., every 5 minutes).
- Candidate: Should tasks be executed exactly on time, or is a small delay acceptable?
- Interviewer: A small delay is acceptable. We're not aiming for real-time precision, but tasks should execute as close as possible to the scheduled time.
- Candidate: Should the scheduler support retries if a task fails?
- Interviewer: No retries for now. However, you can keep the design open to supporting retries later. " "  Candidate: Can a task have dependencies, such that one task should only start after another completes?
- Interviewer: Let's not handle task dependencies in this version. All tasks are independent.
- Candidate: Can multiple tasks run in parallel?
- Interviewer: Yes. The system should be able to run multiple tasks concurrently using a configurable number of worker threads
1.1 Functional Requirements
Support one-time task: The system must allow a user to schedule a task (a piece of code) to be executed once at a specific future time.
Support recurring tasks: The system must support scheduling a task that runs repeatedly at a fixed interval (e.g., every 5 seconds).
Schedule with a delay: The system must allow scheduling a task to run after an initial delay.
Execution: The scheduler must execute the tasks at their designated times. Allow concurrent execution of tasks using multiple worker threads


## Non functional requirements
- Here the jobs system should be available no matter what, users should be able to add / view a job
- system should be scalable for million of jobs
- system  should run a job only once, it should run the job more than once
- system should acknowledge the user as soon as the job is accepted, the user should not wait for more time
- system should have minimal latency
1.2 Non-Functional Requirements
Thread-Safety: The scheduler must be safe to use in a multi-threaded environment. Multiple threads might try to schedule tasks concurrently.
Efficiency: The scheduler should not burn CPU cycles while waiting for the next task. It should sleep or wait efficiently.
Robustness: A long-running task should not block the scheduler from executing other tasks.
Extensibility: The design should be open to adding new types of scheduling logic in the future (e.g., CRON-based schedules) without major refactoring.

## APIs
- submitting the job schedule request
  - submit(apiKey, userId, jobScheduleTime, isRecurring, priorityType, resultLocation)
  - return (JobId) to track the job status
- check status
  - jobStatus(jobId, userId)
  - returns the status of the job if its started or not


## core concepts
- Job
- Job queue
- Dispatcher
- Job schedule
- job store
- output store

Excellent 👏 — this is a **complete, real interview-level problem**, the kind you’d see at **PhonePe, Uber, or Google** (for MTS or L4+).

Let’s go step by step like we would in an actual **system design + LLD interview**, balancing correctness, scalability, and extensibility.
We’ll aim for a **clean architecture** that can handle **millions of jobs**, concurrent execution, and still be **extensible** for future CRON-based tasks.

---

## 🧭 Step 1: Clarify Requirements

### ✅ Functional Summary

| Requirement               | Notes                           |
| ------------------------- | ------------------------------- |
| One-time & recurring jobs | Yes                             |
| Execution delay tolerance | Up to 15 min acceptable         |
| Execution time limit      | Max 5 min/job                   |
| Priority                  | Higher priority → execute first |
| Output                    | Stored in filesystem            |
| Concurrency               | Multiple jobs run in parallel   |
| Retry                     | Not required now                |
| Dependencies              | None in this version            |

---

### ✅ Non-functional Summary

| Requirement    | Notes                                        |
| -------------- | -------------------------------------------- |
| Availability   | Job submission/view must always be available |
| Scalability    | Millions of jobs                             |
| Idempotency    | Job runs **only once**                       |
| Responsiveness | Return job-acceptance immediately            |
| Low latency    | Scheduler efficiently wakes for next job     |
| Thread-safe    | Concurrent scheduling and execution          |
| Extensible     | New schedule types like CRON in future       |

---

## 🧩 Step 2: Core Concepts and Entities

| Entity            | Description                                           |
| ----------------- | ----------------------------------------------------- |
| **Job**           | Encapsulates task logic                               |
| **JobMetadata**   | Contains ID, schedule time, priority, recurrence info |
| **JobStore**      | Persistent store for all jobs (for listing/viewing)   |
| **JobScheduler**  | Orchestrates job submission and dispatching           |
| **JobQueue**      | Priority-based min-heap for next executable jobs      |
| **WorkerPool**    | Executes jobs concurrently                            |
| **Dispatcher**    | Monitors job queue, wakes up when next job is due     |
| **OutputHandler** | Writes job output to file system                      |

---

## 🧱 Step 3: Define High-level Architecture

```
            +-----------------------+
            |       User/API        |
            +-----------+-----------+
                        |
                [JobScheduler.submitJob()]
                        |
                        v
            +-----------------------+
            |       JobStore        | <-- persists metadata (DB / in-memory)
            +-----------------------+
                        |
                        v
            +-----------------------+
            |      JobQueue         | <-- PriorityQueue by (nextRunTime, priority)
            +-----------------------+
                        |
                        v
            +-----------------------+
            |      Dispatcher       | <-- sleeps until next job due
            +-----------------------+
                        |
                        v
            +-----------------------+
            |      WorkerPool       | <-- Thread pool runs jobs concurrently
            +-----------------------+
                        |
                        v
            +-----------------------+
            |     OutputHandler     | <-- Saves output to FS
            +-----------------------+
```

---

## 🧩 Step 4: Data Model

### **JobPriority**

```java
enum JobPriority {
    HIGH(1), MEDIUM(2), LOW(3);
    private int value;
    JobPriority(int value) { this.value = value; }
    public int getValue() { return value; }
}
```

### **Job**

```java
interface Job {
    String execute();  // returns output (to store in file system)
}
```

Example:

```java
class PrintJob implements Job {
    private String message;
    public PrintJob(String message) { this.message = message; }
    @Override
    public String execute() {
        String output = "Executed: " + message + " at " + new Date();
        System.out.println(output);
        return output;
    }
}
```

---

### **JobMetadata**

```java
class JobMetadata implements Comparable<JobMetadata> {
    private String jobId;
    private Job job;
    private long scheduledTime;   // epoch millis
    private long interval;        // for recurring jobs
    private boolean recurring;
    private JobPriority priority;
    private String outputFilePath;

    public JobMetadata(String jobId, Job job, long scheduledTime, JobPriority priority) {
        this.jobId = jobId;
        this.job = job;
        this.scheduledTime = scheduledTime;
        this.priority = priority;
        this.recurring = false;
    }

    public void markRecurring(long interval) {
        this.recurring = true;
        this.interval = interval;
    }

    @Override
    public int compareTo(JobMetadata o) {
        if (this.scheduledTime == o.scheduledTime)
            return this.priority.getValue() - o.priority.getValue();  // higher priority first
        return Long.compare(this.scheduledTime, o.scheduledTime);
    }

    public long getNextRunTime() { return scheduledTime; }
    public void updateNextRunTime() { if (recurring) scheduledTime += interval; }
    public boolean isRecurring() { return recurring; }
    public Job getJob() { return job; }
    public String getJobId() { return jobId; }
}
```

---

## 🧩 Step 5: Core Components

### **JobStore (In-Memory Implementation)**

```java
class JobStore {
    private Map<String, JobMetadata> jobs = new ConcurrentHashMap<>();

    public void addJob(JobMetadata job) { jobs.put(job.getJobId(), job); }
    public JobMetadata getJob(String jobId) { return jobs.get(jobId); }
    public Collection<JobMetadata> listAllJobs() { return jobs.values(); }
}
```

---

### **JobQueue (PriorityQueue)**

```java
class JobQueue {
    private PriorityBlockingQueue<JobMetadata> queue = new PriorityBlockingQueue<>();

    public void add(JobMetadata job) { queue.add(job); }
    public JobMetadata peek() { return queue.peek(); }
    public JobMetadata poll() { return queue.poll(); }
    public void remove(JobMetadata job) { queue.remove(job); }
}
```

---

### **WorkerPool**

```java
class WorkerPool {
    private ExecutorService executor = Executors.newFixedThreadPool(5); // configurable
    private OutputHandler outputHandler = new OutputHandler();

    public void executeJob(JobMetadata job) {
        executor.submit(() -> {
            try {
                String result = job.getJob().execute();
                outputHandler.writeOutput(job.getJobId(), result);
            } catch (Exception e) {
                outputHandler.writeOutput(job.getJobId(), "Job failed: " + e.getMessage());
            }
        });
    }
}
```

---

### **OutputHandler**

```java
class OutputHandler {
    public void writeOutput(String jobId, String output) {
        try (FileWriter writer = new FileWriter("job_output_" + jobId + ".txt")) {
            writer.write(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

---

### **Dispatcher (Heartbeat thread)**

This thread:

* Continuously checks the next job in `JobQueue`
* Sleeps until it’s time to execute
* Dispatches it to `WorkerPool`
* Re-adds recurring jobs

```java
class Dispatcher implements Runnable {
    private JobQueue queue;
    private WorkerPool workerPool;

    public Dispatcher(JobQueue queue, WorkerPool pool) {
        this.queue = queue;
        this.workerPool = pool;
    }

    @Override
    public void run() {
        while (true) {
            try {
                JobMetadata job = queue.peek();
                if (job == null) {
                    Thread.sleep(1000);
                    continue;
                }

                long delay = job.getNextRunTime() - System.currentTimeMillis();
                if (delay > 0) {
                    Thread.sleep(Math.min(delay, 1000));
                    continue;
                }

                queue.poll(); // remove it
                workerPool.executeJob(job);

                if (job.isRecurring()) {
                    job.updateNextRunTime();
                    queue.add(job);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
```

---

### **JobScheduler (Coordinator)**

```java
class JobScheduler {
    private JobStore jobStore = new JobStore();
    private JobQueue jobQueue = new JobQueue();
    private WorkerPool workerPool = new WorkerPool();
    private Thread dispatcherThread;

    public JobScheduler() {
        dispatcherThread = new Thread(new Dispatcher(jobQueue, workerPool));
        dispatcherThread.start();
    }

    public String submitJob(Job job, long delayMillis, boolean recurring, long interval, JobPriority priority) {
        String jobId = UUID.randomUUID().toString();
        long scheduleTime = System.currentTimeMillis() + delayMillis;
        JobMetadata metadata = new JobMetadata(jobId, job, scheduleTime, priority);
        if (recurring) metadata.markRecurring(interval);

        jobStore.addJob(metadata);
        jobQueue.add(metadata);
        System.out.println("✅ Job accepted with ID: " + jobId);
        return jobId;
    }

    public Collection<JobMetadata> listAllJobs() {
        return jobStore.listAllJobs();
    }

    public JobMetadata viewJob(String jobId) {
        return jobStore.getJob(jobId);
    }
}
```

---

## ▶️ Step 6: Example Run

```java
public class Main {
    public static void main(String[] args) throws Exception {
        JobScheduler scheduler = new JobScheduler();

        scheduler.submitJob(new PrintJob("Send Email"), 2000, false, 0, JobPriority.HIGH);
        scheduler.submitJob(new PrintJob("DB Backup"), 4000, true, 5000, JobPriority.MEDIUM);
        scheduler.submitJob(new PrintJob("Generate Report"), 1000, false, 0, JobPriority.LOW);

        Thread.sleep(20000);
        System.out.println("All jobs:");
        scheduler.listAllJobs().forEach(j -> System.out.println(j.getJobId()));
    }
}
```

---

## ✅ Step 7: How This Meets the Requirements

| Requirement                                  | Implementation                                                     |
| -------------------------------------------- | ------------------------------------------------------------------ |
| One-time + recurring                         | Supported via flag + interval                                      |
| Threshold execution (15 min delay tolerance) | Dispatcher ensures near-real-time scheduling                       |
| Max 5 min execution                          | Can enforce timeout in worker thread (using `Future.get(timeout)`) |
| Priority                                     | Handled in `JobMetadata.compareTo()`                               |
| Output to file system                        | Done in `OutputHandler`                                            |
| Concurrency                                  | WorkerPool with configurable threads                               |
| View/list jobs                               | Via `JobStore`                                                     |
| Immediate acknowledgment                     | `submitJob()` returns immediately                                  |
| Thread-safe                                  | `ConcurrentHashMap`, `PriorityBlockingQueue` used                  |
| Efficient sleeping                           | Dispatcher sleeps until next job due                               |
| Robust                                       | Job failures isolated to threads                                   |
| Extensible                                   | Easily add new ScheduleTypes (e.g. CRON)                           |

---

## 🧠 Step 8: Future Extensions

1. **Persistent JobStore** (DB/Redis) for millions of jobs.
2. **Sharded Schedulers** (multiple nodes with leader election).
3. **RetryPolicy** (for transient job failures).
4. **Monitoring / Metrics** dashboard.
5. **Distributed Locking** (ensure single execution globally).
6. **Job Dependencies / DAG Scheduling** for advanced workflows.

---

## 💡 Interview Summary (How to explain)

> “I designed a scalable, thread-safe job scheduler supporting one-time and recurring jobs, prioritization, concurrent execution, and file output.
> It uses a priority queue ordered by time and priority, a dispatcher thread that wakes efficiently for due jobs, and a worker pool for concurrent execution.
> The architecture is extensible for retries, persistence, and CRON-like scheduling, while ensuring low latency and high availability.”

---

Would you like me to now show how to **extend this design to support distributed scheduling** (e.g., multiple scheduler instances using DB or Redis as a coordination layer)? That’s the typical *follow-up question* in a PhonePe LLD round for this system.

