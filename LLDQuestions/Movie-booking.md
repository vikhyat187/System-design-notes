To write the thought process of designing a movie booking system.<br>
[Movie booking](https://leetcode.com/discuss/interview-question/system-design/124803/design-bookmyshow#:~:text=How%20to%20handle%20concurrency%20such,Dirty%2C%20Nonrepeatable%20and%20Phantoms%20reads.)
<br> <br> 

### Requirements gathering
- User should be able to select a location.
- should we display all the cinema halls?
- the cinema hall can be multiplex or single hall as well
- Within a day a movie can have multiple shows
- User can search a movie by title, genre, language, city name
- On searching the movie name it should show the list of theatres showing that movie in that selected city
- User selects a theatre and should be shown the list of shows.
- User should be able to choose a show.
- User should select show date and timings
- User should select the seats from available set of seats.
- User should be able to block the seat for a time window of 5/10 mins
- System should serve in FIFO manner
- User should pay for them

### Assumptions
1. seats should be able to be distinguishable between vacant and occupied ones
2. should we allow user to cancel the tickets?
3. the same seat cannot be booked by multiple users.

### Non Fn req
- Highly concurrent to handle multiple request simultaneously.


#### Entities
- Cities
- Cinema (city has multiple theatres)
- Cinema halls (cinema has multiple halls)
- Movies (Theatre has multiple movies)
- Shows (Movie has multiple shows)
- seat
- booking  = 
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

### Actors
- user can be of two types (Just searching the movie) = guest and (booking the ticket) = customer
- admin
- frontdesk officer
- system user

```java
package practice;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public abstract class MovieBooking {
    abstract void lockSeats(List<Seat> seats, String showId);
    abstract void unlockSeats(List<Seat> seats, String showId);
//    String createBooking(String userId, List<Seat> seats, String showId){
//        if (isAnyOfSeatsBooked(seats)) {
//            throw new SeatNotAvailableException();
//        }
//
//        boolean lockSeats = seatLockProvider.lockSeats(seats, showId, userId);
//        final String bookingId = UUID.randomUUID().toString();
//        Booking booking = new Booking(bookingId, userId, showId, seats);
//        return bookingId;
//    }
    abstract String cancelBooking(String bookingId);

}
class User{
    private String id;
    private String name;

}
class Theatre{
    private String id;
    private String name;
    private String city;
    private String location;
    private List<Screen> screens;
}
enum ScreenType{
    MULTI_PLEX,
    SINGLE_SCREEN;
}

enum SeatType{
    RECLINER,
    NORMAL_SEAT;
}


class Screen{
    private String id;
    private String name;
    private ScreenType screenType;
    private List<Seat> seats;
}

class Seat{
    private String id;
    private SeatType seatType;
    private double price;
}

class SeatLock{
    private Seat seat;
    private Show show;
    private Integer timeoutInSeconds;
    private String lockedBy;
    private Date lockedTime;

    private boolean isSeatLocked(){
        final Instant lockInstant = lockedTime.toInstant().plusSeconds(timeoutInSeconds);
        final Instant currentInstant = new Date().toInstant();
        return currentInstant.isAfter(lockInstant);
    }

}

class Movie {
    private String id;
    private String genre;
    private String name;
    private int duration;
    private String language;
}

class Show{
    private String id;
    private Movie movie;
    private Screen screen;
    private LocalDateTime startTime;
}
class Booking{
    private String id;
    private Show show;
    private User user;
    private List<Seat> bookedSeats;
    private Payment payment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

class Payment{
    private PaymentType paymentType;
    private String id;
    private PaymentStatus paymentStatus;
}

enum PaymentType{
    CARD,
    UPI,
    NET_BANKING;
}

enum PaymentStatus{
    PENDING,
    SUCCESS,
    FAILURE;
}

/**
 * API routes
 * 1. POST /booking
 * 2. GET /booking/{bookingId}
 * 3. GET /theatres/?cityId=cityId&type=theatreType
 * 4. GET /seats/?showId=showId
 * 5. GET /movies - list all movies
 * 6. GET /movies/{movieId} - get movie details
 * 7. GET /shows/?theatreId=theatreId&movieId=movieId
 * 8. GET /shows/{showId}
 * 9. GET /theatres/{theatreId}
 * 10. GET /theatres/{theatreId}/screens
 * 11. GET /screens/{screenId}/seats
 * 12. POST /seats/lock
 * 13. POST /bookings/{bookingId}/cancel
 * 14. POST /payment
 * 15. GET /users/{userId}/bookings
 */

/**
 * Few queries that I can think of
 * 1. fetch me all the theatres for the city
 * 2. fetch all the theatres showing movie X
 * 3. find all the booked seats for show X
 * 4. find the count of vacant seats for show X
 *
 * for the concurrency handling we can use the synchronised block or the distributed locks
 */
```
