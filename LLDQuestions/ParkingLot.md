## Parking spot (Amazon SDE 2)

Design a parking spot to park the vehicles, collect charges from them.

### Requirements gathering 
- User can park their vehicle
- Parking lot can accomodate as per the parking spot
- parking charge will based on time user parked.
- they can multiple entry and exit gates(scalable system)
- Give the user nearest parking space, in case of multiple entrances
- do we have multiple parking floors


### Objects
- Vehicle 
  - Vehicle type
  - vehicle no
  
- Parking spot
  - spot type 
  - spot id
  - isFree
  - price
  
- Ticket
  - vehicle Id
  - parking spot id
  
- entry gate
  - find parking space (how better it can be made ) ***
  - update parking space
  
- exit gate
  - parking charge
  - payment
  - freeparking spot
  
- floors




![ParkingLot](https://user-images.githubusercontent.com/52795644/220273105-9690a19b-9574-46f4-99d4-1987956b0533.png)











