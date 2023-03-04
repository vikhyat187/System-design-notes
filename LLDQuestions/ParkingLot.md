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
  - entry time
  - price
  - parking spot id
  
- entry gate
  - find parking space (how better it can be made ) ***
  - update parking space
  - generateticket
  
- exit gate
  - parking charge
  - payment
  - freeparking spot
  
- floors
  - No floors for now
  
- ParkingStartegy (ENUM)
  - Nearest to entrance
  - Nearest to entrance and elevator
  - any one free
  
- VehicleType 
  - 2 wheeler
  - four wheeler
  
- PricingStrategy
  - hourly pricing 
  - minute pricing
  - fixed pricing


![ParkingLot](https://user-images.githubusercontent.com/52795644/220273105-9690a19b-9574-46f4-99d4-1987956b0533.png)


## LLD design

```
class ParkingSpot{
  String id,
  Boolean isEmpty,
  Vehicle vehicle,
  int price

parkVehicle(vehicle){
  this.vehicle = vehicle;
  isEmpty= false;
}

removeVehicle(vehicle){
  vehicle = null;
  isEmpty = true;
 }
}
```

```

class TwoWheelerSpot{
  price(){
    price = 20

  }
}

class FourWheelerSpot{
  price(){
    price = 40 

  }
}


class HandicapWheelerSpot{
  price(){
     price =0; 
    
  }
}
```
```
class ParkingSpotManager{
  List<ParkingSpots> parkingSpots;
  addparkingSpot();
  removeParkingSpot();
  findParkingSpot();
  removeVehicle();
  parkVehicle();
}

TwoWheelerManager{
  List<ParkingSpots> twoWheelerParkingSpots;
  TwoWheelerManager(){
    super(twoWheelerParkingSpots, parkingStrategy);
   }
}
FourWheelerManager{
  List<ParkingSpots> fourWheelerParkingSpots;
  FourWheelerManager(){
    super(fourWheelerParkingSpots, parkingStrategy);
   }
}

``` 
```
class Vehicle{
  String vehicleNo;
  VehicleType vehicleType;
 }
 
class Ticket{
  long entryTime;
  Vehicle vehicle;
  ParkingSpot parkingSpot;
 }

class EntranceGate{
  ParkingSpotFactory parkingSpotFactory;
  ParkingSpotManager parkingSpotManager;
  Ticket ticket;
  
  findSpace(VehicleType, entranceNo){
    //based on the strategy.
  }
  
  bookSpace(){
    parkingSpotManager.bookSpace();
    generateTicket(VehicleType, parkingSpot, time);
   } 
  
}
class ExitGate{ //cost computation 
  Ticket ticket;
  CostComputationFactory costComputationFactory;
  Payment payment;
  
  costComputationFactory.price();
 }
 
 class CostComputationFactory{
  VehicleType vehicleType;
  return costComputationobject;
  }

class ParkingManagerFactory{
  ParkingSpotManager parkingSpotManger;
  getParkingManager(VehicleType vehicleType){
    ParkingManger parkingSpotManger;
    }
 }   

