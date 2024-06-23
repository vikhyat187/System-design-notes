## Functional requirements
- User can schedule or view the job.
- User can list all the submitted jobs with current status.
- Jobs can be run once or recurring. Jobs should be executed within X threshold time after the defined scheduled time. (let x = 15 minutes)
- Individual job execution time is no more than X minutes. (let x = 5 minutes)
- Jobs can also have priority. Job the with higher priority should be executed first than lower priority
- Job output should be stored inside file system

## Non functional requirements
- Here the jobs system should be available no matter what, users should be able to add / view a job
- system should be scalable for million of jobs
- system  should run a job only once, it should run the job more than once
- system should acknowledge the user as soon as the job is accepted, the user should not wait for more time
- system should have minimal latency


## APIs
- submitting the job schedule request
  - submit(apiKey, userId, jobScheduleTime, isRecurring, priorityType, resultLocation)
  - return (JobId) to track the job status
- check status
  - jobStatus(jobId, userId)
  - returns the status of the job if its started or not
- 
