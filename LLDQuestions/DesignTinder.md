## Requirements
- Users should be shown a bunch of people based on their preferences
- Users should be able to do the left / right swipe for the people
- Users should be able to register their profile
- Users can initiate a chat with the matched person
- Matching algo : There should be a matching algorithm for matching the users
- Swipe functionality: users can swipe left / right for the profiles based on their intrest
- Chat functionality : once the two users are matched they can send messages to each other in realtime
- Geolocation : Users should be shown the matches in their near by location
- Notification: Users should be notified whenever there is a match
- Subscription : the app can include the subscription
- Notification preferences : Users can adjust their notification preferences

## NFRs
- The display of matches should have a low latency, milli sec latency
- High availability for the chat thing
- The design should be scalable using the websockets thing
- Privacy and security (The users data should be secure as we are dealing with the users PII data and location data) We should having the encryption at rest and intransit.
- the images of the users should be loaded fastly (Caching them in the CDN or something)

### Questions
- We should not be showing the same match to the user more than once right? -> Yes
- How can we show the matches based on the location of the user? -> Think more on this a bit

## Entities
- User (Free user, premium user)
  - User will have the location  
- Matching strategy
- Matches (In this we can store what did the first person react and second person react) - This might help us to not show the same match again
- Swipes / reactions
  - UserId, reaction (swipe left / swipe right)
  - from user, to_user
- Notification
  - Notifies a user for the matchId
- Subscription
  - Stores the info for the subscription for the user, start date / end date
  - What is the plan (Multiple plans can occur)
 
## APIs
- show the K top candidates
- swipe left / right
- If its a match then start the chat conversation
- update notification preferences
- send notification on match
- send messages
- Update profile
- Block user

1. GET /discover/?lat=&long=&radius=&matchScore=
2. GET /users/{userId} - profile info
3. POST /users - create user
4. POST /users/{userId}/notify
5. GET /match/list
6. POST /notifications
7. GET /notifications
8. PATCH /notifications/{notificationId}/read

## Features Required:

User Registration and Authentication: Users should be able to create accounts, log in, and authenticate themselves to access the app.

User Profiles: Users should be able to create and manage their profiles, including adding pictures, a bio, and preferences.

Matching Algorithm: The app should have a matching algorithm that suggests potential matches based on user preferences and location.

Swipe Functionality: Users should be able to swipe left or right on profiles to indicate interest or disinterest in a potential match.

Chat Messaging: Once two users have matched, they should be able to send and receive messages in real-time.

Geolocation: The app should have geolocation features to suggest potential matches based on the user's location.

Push Notifications: Users should receive push notifications for new matches, messages, and other important events.

Report and Block: Users should have the option to report and block other users for inappropriate behavior.

Settings: Users should be able to adjust their preferences, privacy settings, and account details.

Subscription and Payment: The app may include subscription plans and payment features for premium services.

## Design Patterns Involved or Used:

Model-View-Controller (MVC) Pattern: The MVC pattern can be used to separate the app into three components: the model (data and business logic), the view (user interface), and the controller (handles user interactions and manages the flow of data).

Observer Pattern: The Observer pattern can be used to notify users about new matches, messages, and other important events through push notifications.

Factory Pattern: The Factory pattern can be used to create different types of objects, such as user profiles or chat messages, based on user requests.

Singleton Pattern: The Singleton pattern can be used to ensure that only one instance of certain classes, such as the user authentication manager or the chat manager, is created and shared across the app.

Proxy Pattern: The Proxy pattern can be used to handle communication between the app and the server, providing a level of indirection and encapsulation for network operations.

Command Pattern: The Command pattern can be used to encapsulate and decouple actions, such as sending messages or updating user profiles, from the specific objects or components that perform those actions.

Publish-Subscribe Pattern: The Publish-Subscribe pattern can be used to implement the real-time messaging system, where users subscribe to specific chat rooms or channels to receive messages, and publishers send messages to those channels.

Decorator Pattern: The Decorator pattern can be used to add additional features or behaviors to user profiles, such as premium features for subscribed users.

Strategy Pattern: The Strategy pattern can be used to implement different matching algorithms based on user preferences and location.

State Pattern: The State pattern can be used to manage the different states of user interactions, such as swiping, messaging, or browsing profiles.
Nice — let’s design **Tinder-like app** at a focused low-level design (LLD) level: classes, interfaces, key methods, data models, flow sequences, concurrency concerns, and a thorough list of edge cases + mitigations. I’ll keep it Java-centric and interview-ready so you can talk through code-level details and trade-offs.

---

# 1 — Scope & assumptions (what we design)

* User registration & profile (photos, bio, location)
* Swipe discovery (left/right/super) — never re-show same profile twice
* Matching (when two users both swipe right)
* Realtime chat between matched users (basic)
* In-app notification (match, message)
* Subscription (free/premium affecting features)
* Location-aware discovery (radius filtering)
* Keep it single-region single-process friendly (later scale out)

I’ll omit full HLD infra detail (we keep it LLD — classes & logic).

---

# 2 — Key domain entities (POJOs)

```java
// Enums
public enum Reaction { LEFT, RIGHT, SUPER }
public enum Gender { MALE, FEMALE, OTHER }
public enum SubscriptionTier { FREE, PREMIUM }

// User
public class User {
    private String id;            // uuid
    private String name;
    private LocalDate dob;
    private Gender gender;
    private String bio;
    private List<String> photoUrls;
    private Location location;    // lat, lon, lastUpdated
    private SubscriptionTier tier;
    private NotificationPreferences notificationPreferences;
    private Instant createdAt;
    // getters/setters
}

// Location
public class Location {
    double lat;
    double lon;
    Instant updatedAt;
}

// Minimal profile filter / preferences
public class SearchPreferences {
    int minAge;
    int maxAge;
    Gender interestedIn;
    double maxDistanceKm;
    boolean onlyVerified;
    // ...
}

// Match record (one per pair)
public class Match {
    private String id; // deterministic: pairKey(userA, userB)
    private String userA;
    private String userB;
    private Reaction aReaction; // reaction by userA to userB
    private Reaction bReaction;
    private boolean mutual;
    private Instant createdAt;
    private Instant updatedAt;
}
```

---

# 3 — Repos / Persistence interfaces (Repository pattern)

```java
public interface UserRepository {
    User save(User u);
    Optional<User> findById(String id);
    List<User> findNearby(Location loc, double radiusKm, SearchPreferences pref, int limit, String cursor);
    void updateLocation(String userId, Location loc);
}

public interface MatchRepository {
    Match upsertReaction(String fromUser, String toUser, Reaction reaction); // transactional
    Optional<Match> findByPair(String userA, String userB);
    List<Match> listMutualMatches(String userId, int limit, int offset);
}

public interface SwipeRepository {
    void appendSwipe(String fromUser, String toUser, Reaction reaction, Instant ts);
    boolean seenPair(String fromUser, String toUser); // used to avoid re-showing
}

public interface MessageRepository {
    void saveMessage(Message m);
    List<Message> getRecentMessages(String convoId, int limit);
}
```

Notes:

* `upsertReaction` must canonicalize pair ordering and atomically update reactions; should return whether match turned mutual.

---

# 4 — Service layer (core classes & key methods)

```java
public class UserService {
    private final UserRepository userRepo;
    public User createOrUpdateProfile(User u) { ... }
    public void updateLocation(String userId, Location loc) { ... }
    public List<User> discover(String userId, SearchPreferences pref, int limit, String cursor) { ... }
}

public class SwipeService {
    private final SwipeRepository swipeRepo;
    private final MatchRepository matchRepo;
    private final NotificationService notificationService;

    // Called by client when user swipes
    public SwipeResult swipe(String fromUser, String toUser, Reaction reaction, String idempotencyKey) {
        // 1. Idempotency check
        // 2. Append swipe event
        // 3. Upsert reaction into Match repo (transactional)
        // 4. If mutual formed -> create conversation & notify both users
    }
}

public class MatchService {
    private final MatchRepository matchRepo;
    public Optional<Match> getMatch(String userA, String userB) { ... }
    public List<Match> listMatches(String userId) { ... }
}

public class ChatService {
    private final MessageRepository messageRepo;
    private final WebSocketGateway wsGateway;
    public void sendMessage(String from, String to, String text) {
        // 1. verify mutual match
        // 2. persist message
        // 3. route via wsGateway or push notification
    }
}

public class NotificationService {
    // from earlier design: createNotification, dispatch, preferences checks
}
```

---

# 5 — Controllers / API signatures

* `POST /v1/users` — create/update profile
* `POST /v1/users/{id}/location` — update location
* `GET  /v1/discover?cursor=&limit=` — returns profiles to swipe
* `POST /v1/swipe` — `{fromUserId, toUserId, reaction, requestId}`
* `GET  /v1/matches` — mutual matches
* `POST /v1/chat/send` — `{from, to, body}`
* `GET  /v1/notifications` — read notification inbox
* `PUT  /v1/notification-preferences` — update prefs

All user endpoints require auth; internal endpoints use service auth.

---

# 6 — Important method-level details & pseudo logic

## `SwipeService.swipe(...)` pseudo

1. Validate users exist and are distinct.
2. Idempotency: check `idempotencyStore` for key (if present, return previous result).
3. Append swipe event to `SwipeRepository`.
4. Call `MatchRepository.upsertReaction(from, to, reaction)` — does:

   * canonicalize (a,b) = sort(userA,userB)
   * if row exists, set appropriate `aReaction`/`bReaction`; else insert new row
   * If now both sides are RIGHT or SUPER -> set mutual=true, createdAt set
   * Return Match object with `mutual` flag and who caused it
5. If returned match is mutual and the current call caused it:

   * Create conversation id (deterministic based on pair)
   * NotificationService.createNotification for both users (idempotent key = `match:matchId:userId`)
6. Return `SwipeResult` (e.g., `{mutual: true/false, conversationId: id}`)

Important: `upsertReaction` must be atomic to avoid race (two parallel swipes).

---

# 7 — Conversation & message model

```java
public class Message {
    private String id;
    private String convoId; // deterministic pair key
    private String fromUser;
    private String toUser;
    private String text;
    private Instant createdAt;
    private boolean delivered;
    private boolean read;
}
```

Convo id = `convo:{min(userA,userB)}:{max(userA,userB)}`

Chat flow:

* Client sends message -> ChatService verifies match -> writes to MessageRepo -> publishes to wsGateway -> gateway delivers if online else NotificationService triggers push.

---

# 8 — In-memory caches & auxiliary stores

* **Redis / local caches**:

  * Idempotency keys (TTL ~ 24-48 hours)
  * Presence: `user:{id}:online` TTL
  * Device tokens per user
  * Candidate caches: precomputed candidate lists for each user for quick discover
* **Geospatial index**:

  * For LLD, `UserRepository.findNearby` implemented via geohash prefix or PostGIS; but at LLD we expose API that consumes Location and radius.

---

# 9 — Concurrency & correctness considerations (low-level)

### Atomic match upsert

* Use DB upsert with unique constraint on (userA, userB) and `INSERT ... ON CONFLICT DO UPDATE` OR
* Use compare-and-swap (optimistic locking) with `version` column, retry loop OR
* Single-writer via leader (overkill). Best: DB atomic upsert + check returned row to detect newly mutual match.

### Idempotency

* All external actions causing side-effects (swipe, createNotification) must accept an idempotency key stored in Redis. If seen, return previous response.

### Ordering and de-duplication for messages

* Assign server-side monotonically increasing `messageSeq` per convo (DB auto-increment partitioned by convo or use timestamp + tie-break id). Clients dedupe on message id.

### Rate-limiting

* Per-user rate limits on swipes (e.g., free tier max 100/day), messages per minute, location updates, etc., enforced via token bucket in Redis.

### Prevent re-showing same profile

* Discovery must filter out any `toUser` where `SwipeRepository.seenPair(userId, toUser)` is true OR a match row exists with shown_to_X timestamp set. Mark shown timestamp when the profile was delivered to client.

---

# 10 — Testing & verifiability (unit/integration)

* Unit tests for `SwipeService`:

  * concurrent swipe race test: A->B and B->A concurrently should produce single mutual match and notify once.
  * idempotency tests: same requestId returns same result.
* Integration test with in-memory DB / transaction to verify `upsertReaction`.
* Chat tests: message ordering, offline-to-online delivery simulation.

---

# 11 — Edge cases & mitigations (comprehensive)

I’ll list each edge case and a practical mitigation.

1. **Same match shown twice**

   * Mitigation: store shown timestamps in Matches/Seen table and filter discovery queries. Also maintain client cursor and server-side candidate queue.

2. **Race condition creating duplicate mutual events**

   * Mitigation: atomic DB upsert to Match table returning whether mutual newly formed. Use idempotency key `match:matchId:userId` before sending notifications.

3. **Idempotent failures (retries cause duplicate writes)**

   * Mitigation: require clients to pass `X-Request-Id`; store mapping in Redis to original result.

4. **Spoofed locations**

   * Mitigation: heuristic checks (IP vs GPS), throttle frequent moves, obscuring exact coordinates (only store coarse geohash).

5. **Bots / spam (mass swipes/messages)**

   * Mitigation: phone verification, CAPTCHAs during signup, machine learning abuse detection, rate limits, manual review flags.

6. **Unreliable push delivery**

   * Mitigation: fallback to email for critical notifications; maintain device tokens; remove tokens on invalid push responses.

7. **User deletes account right after match**

   * Mitigation: soft-delete (mark `deleted_at`) and keep matches/messages for retention policy; prevent sending notifications to deleted users.

8. **Message ordering issues**

   * Mitigation: server assigns sequence or uses `createdAt` + unique id; clients order by sequence then id; server enforces monotonic increments per convo.

9. **Offline user, unread notifications accumulate**

   * Mitigation: batch notifications with throttling; client pulls on open to sync and server supports incremental fetch (cursor-based).

10. **High QPS on discovery (hot users)**

    * Mitigation: caching candidate lists and precomputing top-K for users; use Redis / sharded caches.

11. **Geo boundary inaccuracies with geohash**

    * Mitigation: when using geohash neighbors, always run Haversine final filter to ensure correct distance.

12. **Payment & subscription sync issues**

    * Mitigation: idempotent webhook handling; store payment event logs; avoid granting premium until payment confirmed.

13. **Eventual consistency between services**

    * Mitigation: design idempotent events, keep retries, and expose compensating actions (e.g., if notification failed, re-enqueue).

14. **User blocks another user after sending message**

    * Mitigation: check block list before delivering messages; maintain block relation in Match/Swipe logic.

15. **Data privacy (leak of exact location)**

    * Mitigation: only surface approximate distance (e.g., "within 5 km") and never raw coordinates to other users.

16. **Scaling WebSocket connections**

    * Mitigation: use session registry in Redis for routing, use pub/sub backbone so any WS node can deliver messages; handle reconnection gracefully.

17. **Simultaneous profile updates**

    * Mitigation: optimistic locking (version numbers) on profile updates; inform client on conflict.

18. **Database deadlocks on heavy concurrent upserts**

    * Mitigation: ensure consistent ordering of primary key accesses, use smaller transactions, add retry on serialization failures.

19. **Malicious user creating thousands of accounts**

    * Mitigation: rate-limit signups per IP, SMS verification, device fingerprinting.

20. **GDPR right-to-be-forgotten**

    * Mitigation: implement soft-delete and a deletion pipeline that erases PII after retention period, provide wipe endpoints.

---

# 12 — Small UML-ish ASCII (class relationships)

```
[UserService] <>-- uses --> [UserRepository]
[SwipeService] <>-- uses --> [SwipeRepository]
[SwipeService] <>-- uses --> [MatchRepository]
[MatchRepository] <>-- persists --> [Match table]
[ChatService] <>-- uses --> [MessageRepository]
[NotificationService] <>-- uses --> [NotificationSender (Strategy)]
[WebSocketGateway] <--> [ChatService]  (deliver messages)
```

---

# 13 — Interview talking points (concise)

* “We store swipes as an append-only event stream and keep a Match table (canonical pair key) for dedupe and mutual detection.”
* “Use DB atomic upsert for match reactions to avoid double notifications on concurrent swipes.”
* “Discovery filters by `seen` and `matches` to avoid re-showing; shown timestamps are persisted when results are returned to client.”
* “Idempotency keys for operations that cause side-effects (swipe, notification) to protect against retries.”
* “Rate-limit, phone verification and ML-based anti-spam for abuse prevention.”
* “Chat uses persistent message store + WebSocket for realtime—server assigns sequence numbers to guarantee ordering.”

---

If you want next:

* I can produce a **concrete Java class** for `SwipeService` showing `swipe()` implementation with pseudo DB calls and idempotency handling, **or**
* generate a **sequence diagram** (text) for Swipe → Match → Notification → Chat flow.

Which one should I produce now?
