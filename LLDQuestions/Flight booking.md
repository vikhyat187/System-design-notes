## functional requirements 
- Users should be able to search for the flights for the given date + src/ dest
- Users should be allowed to book multiple flights
- Users should be able to view the schedule of the flights
- Users should be able to cancel the tickets
- Users can book multiple tickets
- Users should be able to pay for the tickets
- Users should get notification for their booking made
- Admin - should be able to add the flights / change the flights schedule
- Users should be allowed to select the desired seats for booking

```java
package practice;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.List;
import java.util.Observer;

public class FlightManagement {
}
class Flight{
    private String flightNo;
    Airline company;
    private Airport start;
    private Airport destination;
    private int duration;
    private BigDecimal baseFare;
    private Aircraft aircraft;
}
class Airline{
    private String name;
    private List<Flight> flights;
}


enum FlightStatus{
    DELAY,
    ONTIME,
    CANCELLED;
}

class Aircraft{
    private String id;
    private int seatCapacity;
    private SeatLayout seatLayout;
    private String model;
}

class SeatLayout{
    private String id;
    private List<Seat> seats;
}

class Airport{
    private String id;
    private String name;
    private String city;
    private List<Flight> flights;
}

class FlightSchedule{
    private Flight flight;
    private Airport start;
    private Airport destination;
    private Time departureTime;
    private Time arrivalTime;
    private BigDecimal dynamicFare;
    private List<FlightSeat> seats;
    private FlightStatus status;
}

class FlightSeat extends Seat{
    private BigDecimal fare;
    private SeatStatus seatStatus;
}
enum SeatStatus{
    BOOKED,
    AVAILABLE;
}

enum SeatType{
    ECONOMY,
    BUSINESS;
}

enum PaymentType{
    PENDING,
    DONE,
    FAILURE,
    REFUND;
}

enum BookingStatus{
    BOOKED,
    CANCELLED;
}

enum NotificationStatus{
    PENDING,
    SENT;
}

enum NotificationChannel{
    SMS,
    EMAIL,
    PUSH_NOTIFICATION;
}


class Seat{
    private String number;
    private SeatType seatType;
}

class User{
    private String id;
    private String name;
    private String phone;
    private String email;
}

class Notifications{
    private String id;
    private Booking booking;
    private NotificationStatus notificationStatus;
    private NotificationChannel notificationChannel;

}

class Booking{
    private String id;
    private User user;
    private List<FlightSeat> flightSeats;
    private BookingStatus bookingStatus;
    private Payment payment;
    private FlightSchedule flightSchedule;
}


class Payment{
    private String id;
    private PaymentType paymentType;
    private PaymentStatus paymentStatus;
}


class FlightManagement {
    private static FlightManagement instance;
    private FlightManagement() {}

    public static synchronized FlightManagement getInstance(){
        if (instance == null){
            instance = new FlightManagement();
        }
        return instance;
    }
}

// src/main/java/practice/PaymentFactory.java
class PaymentFactory {
    public static Payment createPayment(PaymentType type) {
        Payment payment = new Payment();
        payment.setPaymentType(type);
        payment.setPaymentStatus(PaymentStatus.PENDING);
        return payment;
    }
}



// src/main/java/practice/NotificationStrategy.java
public interface NotificationStrategy {
    void sendNotification(User user, String message);
}

class EmailNotification implements NotificationStrategy {
    public void sendNotification(User user, String message) {
        // send email logic
    }
}

class SMSNotification implements NotificationStrategy {
    public void sendNotification(User user, String message) {
        // send SMS logic
    }
}

interface BookingObserver {
    void update(Booking booking);
}

class BookingNotificationService implements BookingObserver {
    public void update(Booking booking) {
        // send notification logic
    }
}
```
