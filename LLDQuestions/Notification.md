# Functional requirements
- Send notifications ✅ 
- Plugable (support any kind of notifications) Mail, whatsapp, SMS(India & abroad), in app notifications
- Create surveys on time preference.
- 100% Delivery, message to be delivered as soon as the device gets connected. (push or pull model)
- Saas
- Prioritization. (different priority of messages)
- Do we want the flow to be async?
- ankush can send notification to some users
- some users can be notified on some activity of not being active
- calendar to send notfication based on their subscription
- they need to be some dashboard to see no of notifications sent
- users preferences to be kept in mind
- 

# Non Functional requirements
- High availability (what if the user isn't avaiable)
- Analysis of our notifications sent (Do we need this?) 
- Scalable
- Correct (what if something breaks in between and still message are sent to queue)


## Priority
- OTP
- Order placed
- transactional

## Rate limiting
- I cannot accept from this user
- I cannot spam this user

## User preference
- User tells don't send me message, send me mail

IVRS handler
In app handler
Email handler 
SMS handler






## Links
[code karle](https://www.codekarle.com/system-design/Notification-system-design.html)<br>
[enjoy algorithms](https://www.enjoyalgorithms.com/blog/notification-service)<br>
[notification api (best resource)](https://www.notificationapi.com/blog/notification-service-design-with-architectural-diagrams)<br>

Perfect 👌 — let’s **simplify** the Notification System to its cleanest, interview-ready *LLD* form.

We’ll keep:

* Only the **core classes & interfaces**
* One or two **channels** (Email, SMS)
* Simple **preferences**
* Basic **flow** — create → send → store

No async queue, no retry logic, no complex infra — just **solid OO design** that still shows good separation of concerns, loose coupling, and high cohesion.

---

## 🧱 1. Core Concepts

| Class / Interface                  | Responsibility                                            |
| ---------------------------------- | --------------------------------------------------------- |
| **User**                           | Represents the user (id, name, email, phone, preferences) |
| **Notification**                   | Represents a notification to be sent                      |
| **NotificationChannel**            | Enum for EMAIL, SMS, etc.                                 |
| **NotificationSender (interface)** | Defines how to send a notification                        |
| **Concrete Senders**               | Implement `NotificationSender` for each channel           |
| **NotificationService**            | Creates and dispatches notifications based on preferences |
| **NotificationPreferences**        | Stores user preferences (what channels are allowed)       |

---

## 🧩 2. Class Diagram (conceptual)

```
        +------------------+
        |   Notification   |
        +------------------+
        | id, user, msg... |
        +------------------+

                 |
                 v
        +----------------------+
        |  NotificationSender  |<---interface
        +----------------------+
          ^                ^
          |                |
 +----------------+   +-----------------+
 | EmailSender    |   | SmsSender       |
 +----------------+   +-----------------+

        ^
        |
        |
+---------------------+
| NotificationService |
+---------------------+
| createNotification()|
| sendNotification()  |
+---------------------+

        ^
        |
+----------------+
| User           |
+----------------+
| id, name, prefs|
+----------------+
```

---

## 💻 3. Code Design (Java-like pseudocode)

### Enums

```java
enum NotificationChannel {
    EMAIL, SMS
}
```

---

### User & Preferences

```java
class NotificationPreferences {
    private boolean emailEnabled;
    private boolean smsEnabled;

    public NotificationPreferences(boolean emailEnabled, boolean smsEnabled) {
        this.emailEnabled = emailEnabled;
        this.smsEnabled = smsEnabled;
    }

    public boolean isEmailEnabled() { return emailEnabled; }
    public boolean isSmsEnabled() { return smsEnabled; }
}

class User {
    private String id;
    private String name;
    private String email;
    private String phone;
    private NotificationPreferences preferences;

    public User(String id, String name, String email, String phone, NotificationPreferences preferences) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.preferences = preferences;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public NotificationPreferences getPreferences() { return preferences; }
}
```

---

### Notification Model

```java
class Notification {
    private String id;
    private User user;
    private String message;

    public Notification(String id, User user, String message) {
        this.id = id;
        this.user = user;
        this.message = message;
    }

    public String getMessage() { return message; }
    public User getUser() { return user; }
}
```

---

### Sender Interface and Implementations

```java
interface NotificationSender {
    void send(Notification notification);
}

class EmailSender implements NotificationSender {
    @Override
    public void send(Notification notification) {
        System.out.println("Sending EMAIL to " +
            notification.getUser().getEmail() + " : " + notification.getMessage());
    }
}

class SmsSender implements NotificationSender {
    @Override
    public void send(Notification notification) {
        System.out.println("Sending SMS to " +
            notification.getUser().getPhone() + " : " + notification.getMessage());
    }
}
```

---

### NotificationService

```java
class NotificationService {
    private NotificationSender emailSender;
    private NotificationSender smsSender;

    public NotificationService() {
        this.emailSender = new EmailSender();
        this.smsSender = new SmsSender();
    }

    public void sendNotification(User user, String message) {
        Notification notification = new Notification(UUID.randomUUID().toString(), user, message);

        NotificationPreferences prefs = user.getPreferences();
        if (prefs.isEmailEnabled()) {
            emailSender.send(notification);
        }
        if (prefs.isSmsEnabled()) {
            smsSender.send(notification);
        }
    }
}
```

---

### Example Usage

```java
public class Main {
    public static void main(String[] args) {
        NotificationPreferences prefs = new NotificationPreferences(true, false); // only email
        User user = new User("1", "Akshat", "akshat@email.com", "9999999999", prefs);

        NotificationService service = new NotificationService();
        service.sendNotification(user, "You have a new match!");
    }
}
```

**Output:**

```
Sending EMAIL to akshat@email.com : You have a new match!
```

---

## ✅ 4. Why this is good design

* **Loose coupling:**
  `NotificationService` depends on `NotificationSender` interface — not on concrete implementations.
* **High cohesion:**
  Each class has a single responsibility (User holds data, Sender sends, Service orchestrates).
* **Open for extension:**
  Adding `PushSender` or `InAppSender` doesn’t require changing existing code — just add a new implementation.
* **Encapsulation:**
  User preferences are contained inside the `User` object, not handled globally.
* **Polymorphism:**
  `NotificationService` can treat all senders uniformly using the `NotificationSender` interface.

---

## ⚠️ 5. Simple Edge Cases

| Case                             | Handling                                      |
| -------------------------------- | --------------------------------------------- |
| User opted out (all prefs false) | Service silently skips send                   |
| User missing email/phone         | Sender can skip or log warning                |
| Adding new channel               | Implement new `NotificationSender`            |
| Bulk send                        | Loop over users and call `sendNotification()` |
| Same message multiple times      | Use notification ID (UUID) to deduplicate     |

---

## 💡 Next-Level (if interviewer pushes)

If you want to extend this slightly:

* Add `NotificationType` enum (MESSAGE, FRIEND_REQUEST).
* Store sent notifications in `List<Notification>` inside `NotificationService`.
* Introduce an interface `NotificationChannelSelector` that decides which channel(s) to use based on preferences or frequency.

---

Would you like me to show the **slightly extended version** (with `NotificationType` and in-app inbox) next — still keeping it simple but closer to production use?


## Requirements asked in machine coding round of PhonePe:

Handle multiple communication request types (Email, SMS, Soundbox, etc.).

Different request types have different mandatory input fields (e.g., Email: sender, receiver, subject, message; SMS: mobile number, message)

Providers can support one or multiple channels; multiple providers per channel allowed.

Each provider can have multiple accounts, segregated for better handling (e.g., critical OTP communication).

Randomly select one active provider from eligible providers for each request


Excellent 💥 — this is *exactly* the kind of **machine coding + design** problem asked at PhonePe / Swiggy / Paytm interviews.

The question combines:

* **LLD design (OO concepts, extensibility)**
* **Polymorphism (different request types)**
* **Abstraction (providers, accounts)**
* **Randomized selection logic**
* And optional future extensibility (adding new channels or providers easily).

Let’s design this **cleanly and code it** in a **machine-coding–ready way** — short, modular, and adhering to SOLID.

---

# 🎯 Requirements Recap

| Category          | Requirement                                                                   |
| ----------------- | ----------------------------------------------------------------------------- |
| **Request types** | Email, SMS, Soundbox, etc. Each has its own required fields.                  |
| **Providers**     | Each provider supports one or more channels.                                  |
| **Accounts**      | Each provider has multiple accounts (critical, normal, etc.).                 |
| **Selection**     | Randomly select *one active provider account* that supports the request type. |
| **Extensible**    | Easy to add new request type or provider later.                               |

---

# 🧩 1. High-level object model

```
                +------------------+
                | CommunicationReq |
                +------------------+
                | validate()       |
                +------------------+
                          ^
        +---------+-------+---------+
        |                   |        |
     +--------+       +---------+  +------------+
     | Email  |       | SMSReq  |  | Soundbox  |
     +--------+       +---------+  +------------+
     | sender,subj... | number,msg| message ... |

                   |
                   v
         +---------------------+
         | Provider            |
         +---------------------+
         | supports(channel)   |
         | send(req, account)  |
         +---------------------+
                    |
          +----------+----------+
          |                     |
   +--------------+     +---------------+
   | EmailProvider|     | SmsProvider   |
   +--------------+     +---------------+

Each Provider has multiple ProviderAccounts
and we randomly pick an active one.
```

---

# 🧱 2. Enum and Base Models

```java
enum ChannelType {
    EMAIL, SMS, SOUNDBOX
}
```

---

# 📨 3. Communication Request Hierarchy

```java
abstract class CommunicationRequest {
    protected ChannelType channelType;

    public ChannelType getChannelType() { return channelType; }

    public abstract boolean validate();
}

class EmailRequest extends CommunicationRequest {
    private String sender;
    private String receiver;
    private String subject;
    private String message;

    public EmailRequest(String sender, String receiver, String subject, String message) {
        this.channelType = ChannelType.EMAIL;
        this.sender = sender;
        this.receiver = receiver;
        this.subject = subject;
        this.message = message;
    }

    @Override
    public boolean validate() {
        return sender != null && receiver != null && subject != null && message != null;
    }

    public String getSender() { return sender; }
    public String getReceiver() { return receiver; }
    public String getSubject() { return subject; }
    public String getMessage() { return message; }
}

class SmsRequest extends CommunicationRequest {
    private String mobileNumber;
    private String message;

    public SmsRequest(String mobileNumber, String message) {
        this.channelType = ChannelType.SMS;
        this.mobileNumber = mobileNumber;
        this.message = message;
    }

    @Override
    public boolean validate() {
        return mobileNumber != null && message != null;
    }

    public String getMobileNumber() { return mobileNumber; }
    public String getMessage() { return message; }
}
```

---

# 🏢 4. Provider Account and Provider Abstraction

```java
class ProviderAccount {
    private String accountId;
    private boolean active;

    public ProviderAccount(String accountId, boolean active) {
        this.accountId = accountId;
        this.active = active;
    }

    public boolean isActive() { return active; }
    public String getAccountId() { return accountId; }
}
```

### Base Provider Interface

```java
interface Provider {
    boolean supports(ChannelType type);
    void send(CommunicationRequest request, ProviderAccount account);
    List<ProviderAccount> getAccounts();
}
```

---

# 📧 5. Concrete Providers

### Email Provider

```java
class EmailProvider implements Provider {
    private String name;
    private List<ProviderAccount> accounts;

    public EmailProvider(String name, List<ProviderAccount> accounts) {
        this.name = name;
        this.accounts = accounts;
    }

    @Override
    public boolean supports(ChannelType type) {
        return type == ChannelType.EMAIL;
    }

    @Override
    public void send(CommunicationRequest request, ProviderAccount account) {
        EmailRequest email = (EmailRequest) request;
        System.out.println("[" + name + "] Sending EMAIL via account " + account.getAccountId() +
            " from " + email.getSender() + " to " + email.getReceiver() +
            " : " + email.getMessage());
    }

    @Override
    public List<ProviderAccount> getAccounts() { return accounts; }
}
```

### SMS Provider

```java
class SmsProvider implements Provider {
    private String name;
    private List<ProviderAccount> accounts;

    public SmsProvider(String name, List<ProviderAccount> accounts) {
        this.name = name;
        this.accounts = accounts;
    }

    @Override
    public boolean supports(ChannelType type) {
        return type == ChannelType.SMS;
    }

    @Override
    public void send(CommunicationRequest request, ProviderAccount account) {
        SmsRequest sms = (SmsRequest) request;
        System.out.println("[" + name + "] Sending SMS via account " + account.getAccountId() +
            " to " + sms.getMobileNumber() + " : " + sms.getMessage());
    }

    @Override
    public List<ProviderAccount> getAccounts() { return accounts; }
}
```

---

# ⚙️ 6. Communication Service (Main Orchestrator)

```java
class CommunicationService {
    private List<Provider> providers;
    private Random random = new Random();

    public CommunicationService(List<Provider> providers) {
        this.providers = providers;
    }

    public void handleRequest(CommunicationRequest request) {
        if (!request.validate()) {
            System.out.println("❌ Invalid request fields");
            return;
        }

        // Find eligible providers that support this channel
        List<Provider> eligible = providers.stream()
            .filter(p -> p.supports(request.getChannelType()))
            .toList();

        if (eligible.isEmpty()) {
            System.out.println("❌ No provider supports this channel");
            return;
        }

        // Randomly select one provider
        Provider selectedProvider = eligible.get(random.nextInt(eligible.size()));

        // From provider, select one active account
        List<ProviderAccount> activeAccounts = selectedProvider.getAccounts()
                .stream().filter(ProviderAccount::isActive).toList();

        if (activeAccounts.isEmpty()) {
            System.out.println("❌ No active accounts in provider " + selectedProvider);
            return;
        }

        ProviderAccount chosenAccount = activeAccounts.get(random.nextInt(activeAccounts.size()));

        // Finally send
        selectedProvider.send(request, chosenAccount);
    }
}
```

---

# 🧪 7. Example Execution

```java
public class Main {
    public static void main(String[] args) {
        // setup providers and accounts
        ProviderAccount acc1 = new ProviderAccount("email-critical-1", true);
        ProviderAccount acc2 = new ProviderAccount("email-general-1", true);
        ProviderAccount acc3 = new ProviderAccount("sms-otp-1", true);

        Provider emailProvider = new EmailProvider("SendGrid", List.of(acc1, acc2));
        Provider smsProvider = new SmsProvider("Twilio", List.of(acc3));

        CommunicationService service = new CommunicationService(List.of(emailProvider, smsProvider));

        // create requests
        EmailRequest emailReq = new EmailRequest("noreply@phonepe.com", "akshat@mail.com", "Welcome", "Your OTP is 1234");
        SmsRequest smsReq = new SmsRequest("9999999999", "Your OTP is 1234");

        service.handleRequest(emailReq);
        service.handleRequest(smsReq);
    }
}
```

---

# 🧩 8. Sample Output

```
[SendGrid] Sending EMAIL via account email-general-1 from noreply@phonepe.com to akshat@mail.com : Your OTP is 1234
[Twilio] Sending SMS via account sms-otp-1 to 9999999999 : Your OTP is 1234
```

---

# ✅ 9. Key Design Points

| Principle                | How it’s Achieved                                                           |
| ------------------------ | --------------------------------------------------------------------------- |
| **Open/Closed**          | Adding new channel → new Request class + Provider implementing `supports()` |
| **Liskov Substitution**  | All `Provider` & `CommunicationRequest` subclasses are interchangeable      |
| **Dependency Inversion** | `CommunicationService` depends only on the abstract `Provider` interface    |
| **High Cohesion**        | Each class has one clear job                                                |
| **Loose Coupling**       | Service doesn’t know internal details of each provider                      |

---

# ⚠️ 10. Edge Cases to Handle in Interview Discussion

| Edge Case                                    | Handling                                                      |
| -------------------------------------------- | ------------------------------------------------------------- |
| No provider supports given channel           | Return failure message                                        |
| All accounts inactive                        | Log / skip sending                                            |
| Multiple providers support same channel      | Random selection logic (current)                              |
| Need deterministic routing (e.g., by region) | Replace `Random` with strategy object                         |
| Retry if one provider fails                  | Wrap `send()` in try/catch, fallback to another               |
| Extend to async (queue)                      | Offload `handleRequest()` to background thread or queue later |
| Config-driven                                | Register providers dynamically from JSON/config file          |

---

# 🚀 11. Optional Extensions (if interviewer asks “how to scale”)

* Add `ProviderRegistry` that auto-registers providers by reading config.
* Add `ChannelRouterStrategy` (Strategy pattern) — random, round-robin, weighted.
* Add monitoring / retry mechanism for failed sends.
* Add a `RateLimiter` per provider or per account.

---

✅ **Summary for Interview**

> “I’ve designed it with abstraction at two levels:
> 1️⃣ `CommunicationRequest` hierarchy (different input fields per channel)
> 2️⃣ `Provider` hierarchy (different implementations for each channel).
>
> The `CommunicationService` acts as a coordinator that finds eligible providers and randomly selects an active account to send the message.
> This approach ensures loose coupling, easy extensibility, and testability.”

---

Would you like me to extend this a *tiny bit* to show **how provider routing (random / weighted)** can be abstracted into a separate `ProviderSelectionStrategy` class (a favorite follow-up in PhonePe machine rounds)?


Whether you’re waiting for a login OTP, a delivery update, or a push notification for your favourite creator’s livestream — notifications are deeply woven into our digital lives. They’re expected to arrive instantly, be reliable, and contextually accurate.

But behind the scenes, handling millions of notifications across channels like Email, SMS, and Push is far from simple.

In this blog, I’ll break down how to design a scalable notification system, starting with the High-Level Design (HLD) and then drilling into the Low-Level Design (LLD). Along the way, we’ll explore architectural decisions, scalability patterns, and even some code.

Let’s dive in.

Before jumping into the design, it’s important to understand what we’re building.

Functional Requirements:

Send notifications through Email, SMS, and Push.
Support user preferences (opt-in/out for certain types).
Handle failures with retries.
Support scheduled (future-dated) notifications.
Log delivery status for analytics and auditing.
Non-Functional requirements:

High scalability and availability.
Low latency for real-time sends.
Observability (logs, metrics, alerts).
Extensibility to add new channels in the future.
System Requirements and Assumptions
Before jumping into the technical details, let’s lay down some assumptions and define the goals for our notification system:

Throughput: The system should be able to process 10,000 notifications per second during peak times.
Channels: Notifications can be sent through multiple channels like email, SMS, and push notifications.
Resiliency: The system should be fault-tolerant, meaning that if a notification delivery fails, it should be retried, and if it continues to fail, it should go into a dead-letter queue (DLQ).
Scalability: The system should be horizontally scalable, allowing you to scale specific components (e.g., workers for each channel) independently.
Components Overview
1. Notification API
This is the entry point for sending notifications to users. The API accepts a payload containing the notification details (e.g., user preferences, message content, target channels).
The API is responsible for validating the request, formatting it correctly, and then publishing the notification message to a message queue (e.g., Kafka or SQS).
2. Validator & Router
Checks payload, validates required fields, and routes to the correct queue or worker.

3. Message Queue (Kafka / SQS)
Helps decouple producers and consumers, improves fault tolerance and scalability.

4. Workers per Channel
Each channel has its own consumer/worker service that reads from the queue and sends notifications using third-party providers like Twilio (SMS) or SendGrid (Email).

5. Retry Mechanism & Dead Letter Queue:
If a notification fails to be delivered, it’s retried with exponential backoff. If the notification continues to fail, it’s moved to a dead-letter queue for further investigation or manual intervention.

Scalable Design
Horizontal Scaling:

Each worker service (email, SMS, push) can scale independently. If there’s a surge in SMS notifications, you can scale the SMS worker service without impacting the others.

Queue Management:

The use of Kafka or SQS ensures that you can handle high throughput. Each worker can process messages asynchronously, allowing the system to continue functioning even if some workers are temporarily unavailable.

Fault Tolerance:

With a retry mechanism and dead-letter queue, the system ensures that transient errors do not cause a notification to be lost. For persistent errors, notifications can be inspected in the dead-letter queue.

Back-of-the-Envelope Calculations
Let’s break down a few key calculations to understand the system’s requirements:

Notifications per Second (NPS):

Assuming we want to handle 10,000 notifications per second, we can calculate how many worker threads we need:
If each worker can process 100 notifications per second, you’ll need 100 workers to handle this load.
Queue Latency:

If you’re using Kafka or SQS, assume a 10ms latency per message. If the queue is properly sized, the system should be able to handle up to 10,000 notifications per second without major bottlenecks.
Scaling Workers:
Each notification channel (email, SMS, push) needs its own set of workers. If each channel needs to handle 3,333 NPS, and each worker processes 100 notifications per second, you’ll need approximately 34 workers per channel.
But do we actually need 34? Let’s consider:
Can you batch notifications?
If you can send notifications in batches (e.g., 10 at a time), each worker could technically handle 1,000 NPS, reducing the worker count to ~4 per channel. Most notification providers support some kind of bulk endpoint (Firebase for push, Twilio Messaging Services, etc.).

2. Are messages evenly distributed?

Load often comes in bursts, not steady streams. You may be able to autoscale workers or use queues with temporary surges instead of overprovisioning.

3. Are you measuring peak or average?

If 3,333 NPS is peak, you might design for lower capacity and implement:

Retry mechanisms
Backpressure
Queue buffering
4. Can workers be multi-threaded or async?

If a worker can handle multiple tasks concurrently, the number of instances goes down significantly.

5. What’s your latency tolerance?

If you’re okay with a few seconds of delay, then you can buffer and throttle rather than trying to achieve real-time throughput.

APIs required
To facilitate interaction with the notification system, here are the essential APIs you’ll need:

POST /notifications:
This API allows clients to send notifications. It accepts a payload containing user preferences, message content, and notification channels.
Example:
{
  "userId": "user123",
  "channels": ["email", "sms"],
  "message": "Your order has shipped",
  "notificationType": "order_shipped",
  "preferences": {"email": true, "sms": false}
}
2. GET /notifications/{notificationId}:

This API allows clients to track the status of a notification. The response will include details such as delivery status, timestamp, and the channel used.
3. POST /retry:

This endpoint triggers a retry for xa failed notification.
High-Level Design (HLD)
Here’s a simplified architecture diagram of the notification system:

Press enter or click to view image in full size

Press enter or click to view image in full size

Low-Level Design (LLD)
Let’s break down the core data models and flow

Channel enum
public enum Channel {
    EMAIL, SMS, PUSH
}
2. Notification class

public interface Notification {
    Channel getChannel();
    String getRecipient();
    String getContent();
}

public final class Email implements Notification {
    private final String toEmail;
    private final String body;
    private final String subject;

    public Email(String toEmail, String body, String subject) {
        this.toEmail = toEmail;
        this.body = body;
        this.subject = subject;
    }

    @Override
    public Channel getChannel() {
        return Channel.EMAIL;
    }

    @Override
    public String getRecipient() {
        return toEmail;
    }

    @Override
    public String getContent() {
        return body;
    }

    public String getSubject() {
        return subject;
    }
}

class Push implements Notification {
    private final String toDeviceId;
    private final String title;
    private final String payload;

    public Push(String toDeviceId, String title, String payload) {
        this.toDeviceId = toDeviceId;
        this.title = title;
        this.payload = payload;
    }

    @Override
    public Channel getChannel() {
        return Channel.PUSH;
    }

    @Override
    public String getRecipient() {
        return toDeviceId;
    }

    @Override
    public String getContent() {
        return payload;
    }

    public String getTitle() {
        return title;
    }
}

class SMS implements Notification {
    private final String toPhoneNumber;
    private final String message;

    public SMS(String toPhoneNumber, String message) {
        this.toPhoneNumber = toPhoneNumber;
        this.message = message;
    }

    @Override
    public Channel getChannel() {
        return Channel.SMS;
    }

    @Override
    public String getRecipient() {
        return toPhoneNumber;
    }

    @Override
    public String getContent() {
        return message;
    }
}
3. Notification Sender

public interface NotificationSender {
    void send(Notification notification);
}

public interface SchedulableNotificationSender extends NotificationSender {
    void schedule(Notification notification, LocalDateTime dateTime);
}

public class EmailNotificationSender implements SchedulableNotificationSender {
    @Override
    public void send(Notification notification) {
        System.out.println("Sending EMAIL to " + notification.getRecipient());
    }

    @Override
    public void schedule(Notification notification, LocalDateTime dateTime) {
        System.out.println("Scheduling EMAIL to " + notification.getRecipient() + " at " + dateTime);
    }
}

public class PushNotificationSender implements SchedulableNotificationSender {
    @Override
    public void send(Notification notification) {
        System.out.println("Sending PUSH to " + notification.getRecipient());
    }

    @Override
    public void schedule(Notification notification, LocalDateTime dateTime) {
        System.out.println("Scheduling PUSH to " + notification.getRecipient() + " at " + dateTime);
    }
}

public class SMSNotificationSender implements SchedulableNotificationSender {
    @Override
    public void send(Notification notification) {
        System.out.println("Sending SMS to " + notification.getRecipient());
    }
    
    @Override
    public void schedule(Notification notification, LocalDateTime dateTime) {
        System.out.println("Scheduling SMS to " + notification.getRecipient() + " at " + dateTime);
    }
}
4. Notification Sender Factory

public interface NotificationSenderFactory {
    Optional<NotificationSender> getSender(Channel channel);
    Optional<SchedulableNotificationSender> getSchedulableSender(Channel channel);
}

public class DefaultNotificationSenderFactory implements NotificationSenderFactory {
    private final Map<Channel, NotificationSender> senderMap;
    private final Map<Channel, SchedulableNotificationSender> schedulableSenderMap;

    public DefaultNotificationSenderFactory() {
        this.senderMap = new HashMap<>();
        this.schedulableSenderMap = new HashMap<>();

        // Register senders externally
        EmailNotificationSender emailSender = new EmailNotificationSender();
        PushNotificationSender pushSender = new PushNotificationSender();
        SMSNotificationSender smsSender = new SMSNotificationSender();

        senderMap.put(Channel.EMAIL, emailSender);
        senderMap.put(Channel.PUSH, pushSender);
        senderMap.put(Channel.SMS, smsSender);

        schedulableSenderMap.put(Channel.EMAIL, emailSender);
        schedulableSenderMap.put(Channel.PUSH, pushSender);
        schedulableSenderMap.put(Channel.SMS, smsSender);
    }

    @Override
    public Optional<NotificationSender> getSender(Channel channel) {
        return Optional.ofNullable(senderMap.get(channel));
    }

    @Override
    public Optional<SchedulableNotificationSender> getSchedulableSender(Channel channel) {
        return Optional.ofNullable(schedulableSenderMap.get(channel));
    }
}
5. Notification Dispatcher

public class NotificationDispatcher {
    private final NotificationSenderFactory senderFactory;

    public NotificationDispatcher(NotificationSenderFactory senderFactory) {
        this.senderFactory = senderFactory;
    }

    public void dispatch(Notification notification) {
        senderFactory.getSender(notification.getChannel())
            .ifPresentOrElse(
                sender -> sender.send(notification),
                () -> System.out.println("Unsupported notification channel.")
            );
    }

    public void schedule(Notification notification, LocalDateTime dateTime) {
        senderFactory.getSchedulableSender(notification.getChannel())
            .ifPresentOrElse(
                sender -> sender.schedule(notification, dateTime),
                () -> System.out.println("Scheduling not supported for channel: " + notification.getChannel())
            );
    }
}
6. Usage

public class NotificationSystemApp {
    public static void main(String[] args) {
        NotificationSenderFactory factory = new DefaultNotificationSenderFactory();
        NotificationDispatcher dispatcher = new NotificationDispatcher(factory);

        Notification email = new Email("user@example.com", "Hello!", "Welcome");
        dispatcher.dispatch(email);
        dispatcher.schedule(email, LocalDateTime.now().plusHours(2));

        Notification sms = new SMS("1234567890", "Hi there");
        dispatcher.dispatch(sms);
        dispatcher.schedule(sms, LocalDateTime.now().plusMinutes(30));  // Will print unsupported
    }
}
Applying SOLID Principles
S — Single Responsibility Principle (SRP)

Each class should have one, and only one, reason to change.

A class should do one thing, and do it well.

EmailNotificationSender only sends emails.
NotificationDispatcher just dispatches to the appropriate sender.
O — Open/Closed Principle (OCP)

Software entities should be open for extension but closed for modification.

You should be able to add new behaviour without modifying existing code.

Add a new Channel (e.g., WHATSAPP) by:
Creating WhatsApp implements Notification
Creating WhatsAppNotificationSender
Registering in the senderRegistry
Existing code doesn’t change — you just extend.

L — Liskov Substitution Principle (LSP)

Subtypes must be substitutable for their base types.

If Notification is expected, you should be able to pass an Email, SMS, or Push without breaking things.

All Notification types correctly implement getChannel(), getRecipient(), etc.
You can call dispatcher.dispatch(any Notification) without worrying which type it is.
I — Interface Segregation Principle (ISP)

Clients shouldn’t be forced to depend on interfaces they don’t use.

Prefer small, focused interfaces over bloated ones.

You can have a new channel where you can choose to implement NotificationSender and not SchedulableNotificationSender.

D — Dependency Inversion Principle (DIP)

High-level modules shouldn’t depend on low-level modules; both should depend on abstractions.

Don’t hardcode dependencies — rely on interfaces and inject them.

NotificationDispatcher depends on the abstraction NotificationSender, not concrete classes like EmailNotificationSender. The actual implementations are injected via the constructor. This allows us to inject mock or new channel senders easily, making the system extensible and testable.

Design Patterns in Use
Factory Pattern
NotificationSenderFactory decouples object creation logic and allows dynamic dispatch based on Channel. You don’t want to hardcode the creation logic for each channel’s sender (Email, SMS, Push). H Based on the input Channel, the factory decides which concrete NotificationSender to return. This decouples instantiation from usage — adding a new channel requires only updating the factory, not the business logic.
Strategy Pattern
Each NotificationSender (Email, SMS, Push) is a separate strategy implementing a common interface (send, schedule). You can plug in new senders without changing core logic. Open/closed principle in action.
Interface Segregation
SchedulableNotificationSender extends NotificationSender, allowing support for both real-time and scheduled messages only where needed.
Dependency Injection
NotificationDispatcher receives the factory via constructor, making it easier to plug in mocks, test stubs, or alternate factories.
Command Pattern (implicit)
Each notification acts like a command object containing data + logic to be executed by a sender.
Decoupling with Kafka/Queues
To make the system resilient and scalable, we use a Message Queue like Kafka or AWS SQS between the Notification API and the Worker Services.

Why a Message Queue?

Decouples producers (API) and consumers (workers): They don’t need to be up at the same time.
Scales horizontally: You can add more workers for any channel to handle increased load.
Buffering: In case of spikes, messages are queued instead of being dropped.
Fault tolerance: If a worker crashes, the message stays in the queue and can be retried.
Scalability Approaches
Channel-wise Horizontal Scaling
Deploy independent consumer services for each channel. You can scale email workers separately from SMS or Push based on load.
Topic Partitioning (Kafka)
Kafka topics can be partitioned by user ID, channel, or priority, allowing concurrent processing without conflict.
Retry Queues
Failed notifications can be moved to a DLQ (Dead Letter Queue) or Retry Queue with exponential backoff.
Rate Limiting & Throttling
Each channel service can enforce provider-specific limits. For example, only X SMS per minute to comply with telecom regulations.
Scheduled Notification Handling
Scheduled notifications can be persisted in a dedicated scheduler DB or topic and processed by a cron-based worker or timer service.
Challenges and Considerations
Delivery Guarantees: Depending on the notification channel (e.g., SMS, email), you may not always be able to guarantee 100% delivery. Implementing mechanisms like delivery receipts and fallback strategies (e.g., retry on failure) can help improve reliability.
Cost Management: Managing the costs associated with third-party services like Twilio for SMS or SendGrid for emails is important. Make sure to implement a strategy to keep track of costs and scale the system in a cost-efficient manner.
Monitoring & Alerting: Integrate monitoring tools (e.g., Prometheus, Grafana) to keep an eye on queue lengths, worker health, and failure rates. Alerting should be set up for any anomalies.
Consistency Considerations
In distributed systems, consistency often trades off with availability and performance. For a notification system, here’s how we approach it:️

Eventual Consistency

We adopt eventual consistency in most parts of the system. For example:

If a notification is sent to Kafka but the Email worker is briefly down, it will still be delivered once the worker is back online.
Delivery status updates may not be immediately visible but will eventually sync to analytics/logging stores.
Idempotency

To avoid duplicate notifications, especially during retries, the system ensures idempotent operations:

Each notification is assigned a unique request ID.
Workers check if a notification has already been sent before re-sending.
Delivery Guarantees
We aim for at-least-once delivery:

Messages in Kafka/SQS are not removed until acknowledged.
This may result in duplicates (which idempotency handles), but avoids dropped messages.
You could use exactly-once delivery with more complexity (e.g., Kafka transactions, deduplication cache, redis+bloom filter), but at scale, at-least-once with idempotency is simpler and more practical.

Handling Failures with Dead Letter Queues (DLQ)

In any scalable system, failures are inevitable. While retry mechanisms help, some failures can’t be recovered on the first try — whether due to permanent issues like invalid recipient data or provider failures. This is where a Dead Letter Queue (DLQ) comes into play.

What is a Dead Letter Queue (DLQ)? A DLQ is a special queue used to capture messages that can’t be processed successfully after multiple attempts. Instead of letting failed messages be lost or dropped, they are stored in the DLQ for further inspection and action.

How DLQ fits into the Notification System:

Retry Mechanism: When a worker fails to process a notification (e.g., due to a temporary issue), the system can retry the operation a set number of times. If it still fails, the message is moved to the DLQ for manual intervention.
Monitoring: Messages in the DLQ can be monitored, allowing you to track recurring issues and improve the system by addressing root causes.
Error Handling: The DLQ allows for systematic error handling, ensuring that failed notifications don’t block the processing of other messages.
By integrating DLQs into our notification system, we can ensure that even in the face of failure, our system remains resilient, and notifications that require special attention are handled appropriately.

Conclusion: Building a Robust and Scalable Notification System
Designing a notification system that can scale to handle millions of users across various channels is no small feat. However, by leveraging the right architectural patterns — such as decoupling with message queues, scaling horizontally by channel, and ensuring resilience with retry mechanisms and idempotency — you can build a system that is both reliable and efficient.

Applying SOLID principles and design patterns like Factory and Strategy ensures that your system remains modular, maintainable, and extensible. Furthermore, keeping an eye on scalability, observability, and consistency will ensure that your system can grow and evolve without sacrificing performance or reliability.

Have you built a scalable notification system before? Or maybe you’re facing challenges in designing one for your application? Share your experiences in the comments below or reach out with your thoughts on what you would add to this architecture!
