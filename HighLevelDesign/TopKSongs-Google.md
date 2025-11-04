## Question
- Given an API to track when a certain user listened to a certain song, build a system to track the top 10 most listened to songs and albums in the last one week / month / one year, by user / country / globally.

### clarifying questions
- We are told that the API can be used to track song wise when was that last listened, is this understanding correct?
- Because this makes me think that the API given a song can tell me the last time a user listened to that and not more info about that to identify the count of times it was listened by the users in a country / globally

### Usecases
- API - to get the top N songs / albums
- Personalisation - No
- End user customisation - No

### Scale
- 400M users - 40% are active users
- Avg of 2 hours of listening hours per user
- 70M songs are there in the catalog
- We will be getting new songs added every day 60K songs
- what is the expected latency, should the top K list be generated in realtime or should it be like a batch process?
- batch process works for now, the list can be generated at 1 hour interval
- In certain parts of the world people listen more than the others there the list should be getting updated more frequently compared to the other parts of not much active users.
- 1 hour refresh in popular regions and 1 day for the non popular regions (keep it configurable)
- Keep your design scalable as the app can gain traction and we can also get 500 to 1000qps

<img width="841" height="490" alt="image" src="https://github.com/user-attachments/assets/80c5dc2c-cd62-4cdc-95f1-a89268013edf" />
<img width="705" height="496" alt="image" src="https://github.com/user-attachments/assets/80863758-65cf-46bd-9cd9-410ac262f227" />

### Schema
- album id
- song Id
- user id
- location
- app identifier or app sessions

  Enriched schema
  - this had the info about the various things like
  - user
    - userId
    - gender
    - age
  - song
    - artist
    - genre

