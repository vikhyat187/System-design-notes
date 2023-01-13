To write the thought process of designing a movie booking system.<br>
[Movie booking](https://leetcode.com/discuss/interview-question/system-design/124803/design-bookmyshow#:~:text=How%20to%20handle%20concurrency%20such,Dirty%2C%20Nonrepeatable%20and%20Phantoms%20reads.)
<br> <br> 

### Requirements gathering
- User should be able to select a location.
- User should be shown the list of theatres in that location.
- User selects a theatre and should be shown the list of shows.
- User should be able to choose a show.
- User should select show date and timings
- User should select the seats from available set of seats.
- User should be able to block the seat for a time window of 5/10 mins
- System should serve in FIFO manner
- User should pay for them

### Non Fn req
- Highly concurrent to handle multiple request simultaneously.


#### Entities
- Cities
- Cinema (city has multiple theatres)
- Cinema halls (cinema has multiple halls)
- Movies (Theatre has multiple movies)
- Shows (Movie has multiple shows)
- seat
- booking 
- user
- payment

Relationships
- City has multiple cinemas
- cinema has multiple cinema halls
- cinema hall has a movie screening
- cinema has multiple movies
- cinema halls has multiple seats
- movie can have multiple shows
- user can have multiple bookings


