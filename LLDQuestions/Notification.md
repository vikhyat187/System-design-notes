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
