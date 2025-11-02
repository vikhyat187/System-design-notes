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

Code: Detailed Implementation of Classes Based on Each Design Pattern Mentioned Above
```java
// User class
class User {
    private String userId;
    private String username;
    private String password;
    private String bio;
    private List<String> pictures;
    // Other attributes and methods

    public User(String userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.pictures = new ArrayList<>();
    }

    // Getters and setters
    // Methods to manage pictures, bio, and preferences
}

// Match class
class Match {
    private User user1;
    private User user2;
    private LocalDateTime matchTime;
    // Other attributes and methods

    public Match(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
        this.matchTime = LocalDateTime.now();
    }

    // Getters and setters
}

// Message class
class Message {
    private String messageId;
    private User sender;
    private User receiver;
    private String content;
    private LocalDateTime timestamp;
    // Other attributes and methods

    public Message(String messageId, User sender, User receiver, String content) {
        this.messageId = messageId;
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and setters
}

// Location class
class Location {
    private double latitude;
    private double longitude;
    // Other attributes and methods

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and setters
}

// ProfileDecorator interface (Decorator)
interface ProfileDecorator {
    String decorateProfile(User user);
}

// PremiumFeatureDecorator class (Decorator)
class PremiumFeatureDecorator implements ProfileDecorator {
    private User user;

    public PremiumFeatureDecorator(User user) {
        this.user = user;
    }

    @Override
    public String decorateProfile(User user) {
        // Add premium features to the user's profile
        return "Premium: " + user.getBio();
    }
}

// PresenceObserver interface
interface PresenceObserver {
    void onPresenceChange(User user, boolean online);
}

// PresenceManager class (Singleton)
class PresenceManager {
    private static PresenceManager instance;
    private Map<User, Boolean> presenceMap;
    private List<PresenceObserver> observers;

    private PresenceManager() {
        this.presenceMap = new HashMap<>();
        this.observers = new ArrayList<>();
    }

    public static synchronized PresenceManager getInstance() {
        if (instance == null) {
            instance = new PresenceManager();
        }
        return instance;
    }

    public void setPresence(User user, boolean online) {
        presenceMap.put(user, online);
        notifyObservers(user, online);
    }

    public void addObserver(PresenceObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(PresenceObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers(User user, boolean online) {
        for (PresenceObserver observer : observers) {
            observer.onPresenceChange(user, online);
        }
    }
}

// MessagingSystem class (Publish-Subscribe)
class MessagingSystem {
    private Map<String, List<Message>> messageHistory;
    private Map<User, List<String>> userChannels;
    // Other attributes and methods

    public MessagingSystem() {
        this.messageHistory = new HashMap<>();
        this.userChannels = new HashMap<>();
    }

    public void subscribe(User user, String channel) {
        List<String> channels = userChannels.computeIfAbsent(user, k -> new ArrayList<>());
        channels.add(channel);
    }

    public void unsubscribe(User user, String channel) {
        List<String> channels = userChannels.get(user);
        if (channels != null) {
            channels.remove(channel);
        }
    }

    public void publishMessage(Message message, String channel) {
        List<Message> channelMessages = messageHistory.computeIfAbsent(channel, k -> new ArrayList<>());
        channelMessages.add(message);
        sendMessageToSubscribers(message, channel);
    }

    private void sendMessageToSubscribers(Message message, String channel) {
        List<User> subscribers = new ArrayList<>();
        for (Map.Entry<User, List<String>> entry : userChannels.entrySet()) {
            User user = entry.getKey();
            List<String> channels = entry.getValue();
            if (channels.contains(channel) && !user.equals(message.getSender())) {
                subscribers.add(user);
            }
        }

        for (User subscriber : subscribers) {
            subscriber.receiveMessage(message);
        }
    }

    // Other messaging system methods
}

// MatchingAlgorithm interface (Strategy)
interface MatchingAlgorithm {
    List<User> findPotentialMatches(User user, List<User> users);
}

// SimpleMatchingAlgorithm class (Strategy)
class SimpleMatchingAlgorithm implements MatchingAlgorithm {
    @Override
    public List<User> findPotentialMatches(User user, List<User> users) {
        // Implement a simple matching algorithm based on user preferences and location
        // Return a list of potential matches
    }
}

// SwipeState interface (State)
interface SwipeState {
    void handleSwipe(User user, User profile, boolean isSwipeRight);
}

// BrowsingState class (State)
class BrowsingState implements SwipeState {
    @Override
    public void handleSwipe(User user, User profile, boolean isSwipeRight) {
        // Handle swipe in browsing state (show profile details)
    }
}

// MatchingState class (State)
class MatchingState implements SwipeState {
    @Override
    public void handleSwipe(User user, User profile, boolean isSwipeRight) {
        // Handle swipe in matching state (add to matches or continue browsing)
    }
}

// SwipeManager class
class SwipeManager {
    private SwipeState currentState;

    public SwipeManager() {
        this.currentState = new BrowsingState();
    }

    public void handleSwipe(User user, User profile, boolean isSwipeRight) {
        currentState.handleSwipe(user, profile, isSwipeRight);
        // Update current state based on matching or browsing results
    }
}

// SubscriptionPlan class
class SubscriptionPlan {
    private String planId;
    private String name;
    private double price;
    // Other attributes and methods

    public SubscriptionPlan(String planId, String name, double price) {
        this.planId = planId;
        this.name = name;
        this.price = price;
    }

    // Getters and setters
    // Other subscription plan methods
}

// SubscriptionManager class
class SubscriptionManager {
    private List<SubscriptionPlan> subscriptionPlans;

    public SubscriptionManager() {
        this.subscriptionPlans = new ArrayList<>();
    }

    public void addSubscriptionPlan(SubscriptionPlan plan) {
        subscriptionPlans.add(plan);
    }

    public void removeSubscriptionPlan(SubscriptionPlan plan) {
        subscriptionPlans.remove(plan);
    }

    public List<SubscriptionPlan> getSubscriptionPlans() {
        return Collections.unmodifiableList(subscriptionPlans);
    }

    // Other subscription management methods
}

// Main Class
public class TinderDatingApp {
    public static void main(String[] args) {
        // Create users
        User user1 = new User("user1", "John", "password1");
        User user2 = new User("user2", "Alice", "password2");

        // Create profiles
        user1.setBio("Hi, I'm John!");
        user2.setBio("Hello, I'm Alice!");
        user1.getPictures().add("picture1.jpg");
        user2.getPictures().add("picture2.jpg");

        // Create matching algorithm
        MatchingAlgorithm matchingAlgorithm = new SimpleMatchingAlgorithm();
        List<User> potentialMatches = matchingAlgorithm.findPotentialMatches(user1, List.of(user2));

        // Create messaging system
        MessagingSystem messagingSystem = new MessagingSystem();
        messagingSystem.subscribe(user1, "user2");
        messagingSystem.subscribe(user2, "user1");
        messagingSystem.publishMessage(new Message("message1", user1, user2, "Hello, Alice!"), "user2");

        // Create swipe manager
        SwipeManager swipeManager = new SwipeManager();
        swipeManager.handleSwipe(user1, user2, true);

        // Create presence manager
        PresenceManager presenceManager = PresenceManager.getInstance();
        presenceManager.setPresence(user1, true);
        presenceManager.setPresence(user2, true);

        // Create subscription manager
        SubscriptionManager subscriptionManager = new SubscriptionManager();
        SubscriptionPlan plan1 = new SubscriptionPlan("plan1", "Gold Plan", 29.99);
        SubscriptionPlan plan2 = new SubscriptionPlan("plan2", "Platinum Plan", 49.99);
        subscriptionManager.addSubscriptionPlan(plan1);
        subscriptionManager.addSubscriptionPlan(plan2);
    }
}

```
In this code example, the User class represents a user of the dating app, the Match class represents a successful match between two users, the Message class represents a chat message between users, the Location class represents the geolocation of a user, the PremiumFeatureDecorator class adds premium features to user profiles, and the SubscriptionPlan class represents different subscription plans for premium services.

The app uses various design patterns, such as the Model-View-Controller (MVC) pattern for separating the app into three components, the Observer pattern for push notifications and presence updates, the Factory pattern for creating different types of objects based on user requests, the Singleton pattern for creating a single instance of the presence manager, the Proxy pattern for handling communication between the app and the server, the Command pattern for encapsulating actions, the Publish-Subscribe pattern for implementing the real-time messaging system, the Decorator pattern for adding premium features to user profiles, the Strategy pattern for different matching algorithms, and the State pattern for managing different user interactions during swiping.

Please note that this is a simplified example, and a complete implementation of a dating app involves more complex components, such as user authentication and authorization, user preferences and matching criteria, location-based matching, image processing for profile pictures, message persistence, user reporting and blocking mechanisms, handling user privacy and data security, and integration with external payment gateways for premium subscriptions.
