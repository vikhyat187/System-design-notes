# Design doc for airline-checkin system

## Functional requirements
- Booking for a particular airline
- searching for an airline
- book a flight
- payments 3rd party

#### Optional
- analytics
- notification service


## Non functional requirements
- No of people searching airlines - 100k/day
- No of people transacting - 1k/day
- 100 flights per day -> each flight 250 seats
- Highly available service
- latency should be as low as possible
- clients are from website
- Users from all over the world can book the flight
- Highly consistent 

## Capacity estimation
-  1 user data = 100 bytes
-  No of users = 10k 
-  1GB/year -> storing for 5 years = 7gb data 
-  should go for partition database
-  the data should be highly available and eventual consitency


<img width="585" alt="Screenshot 2023-04-23 at 3 18 13 PM" src="https://user-images.githubusercontent.com/52795644/233832432-f1b3973b-70ea-4dd9-8f87-9cd012fd2528.png">
