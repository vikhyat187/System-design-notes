# System design of twitter

Requirements
- Users can login
- Users can follow people
- Users can see their tweets
- users can react to tweet
- users can retweet
- can the users see the feed of people not followed as well?
- can users embedded their tweet at different places?
- how should be the tweet ranked?
- tweet min chars? and should it allow images other things?

Non Fn req
- Scalable, available.
- 500mn active users
- 10 mb max tweet size
- read heavy system
- daily tweets?
- pull based cdn ( check this out)


Entities
- User(stores the user metadata)
- Tweets
- Follow (The index to be on follower the user so that all people followed will be grouped together)
- popular tweets on cache
- 
- 
