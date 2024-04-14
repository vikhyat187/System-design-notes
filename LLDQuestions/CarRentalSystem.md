1. The system will support the renting of different automobiles like cars, trucks, SUVs, vans, and motorcycles.

2. Each vehicle should be added with a unique barcode and other details, including a parking stall number which helps to locate the vehicle.

3. The system should be able to retrieve information like which member took a particular vehicle or what vehicles have been rented out by a specific member.

4. The system should collect a late-fee for vehicles returned after the due date. (This can be done when we are fetching the total amount to be paid)

5. Members should be able to search the vehicle inventory and reserve any available vehicle. (Fetch the vehicles available)

6. The system should be able to send notifications whenever the reservation is approaching the pick-up date, as well as when the vehicle is nearing the due date or has not been returned within the due date.

7. The system will be able to read barcodes from vehicles.

8. Members should be able to cancel their reservations.

9. The system should maintain a vehicle log to track all events related to the vehicles.

10. Members can add rental insurance to their reservation.

11. Members can rent additional equipment, like navigation, child seat, ski rack, etc.

12. Members can add additional services to their reservation, such as roadside assistance, additional driver, wifi, etc.


Entities
- User
- Vehicle
- vehicle type
- Vehicle status
- Registration table
- Vehicle log
- Insuarence

- Vehicle Table:
VehicleID (Primary Key)
Barcode (Unique)
Type (Car, Truck, SUV, Van, Motorcycle)
ParkingStallNumber
OtherDetails (Additional vehicle details)

- Member Table:
MemberID (Primary Key)
Name
Email
Phone
Address

- Reservation Table:
ReservationID (Primary Key)
MemberID (Foreign Key)
VehicleID (Foreign Key)
PickUpDate
ReturnDate
Status (Reserved, Active, Completed, Cancelled)

- LateFee Table:
LateFeeID (Primary Key)
ReservationID (Foreign Key)
FeeAmount
PaidStatus
LateReturnDate

- VehicleLog Table:
LogID (Primary Key)
VehicleID (Foreign Key)
Event (PickUp, Return, LateReturn, ReservationCancelled, etc.)
Timestamp

- Insurance Table:
InsuranceID (Primary Key)
ReservationID (Foreign Key)
InsuranceType
InsuranceCost

- Equipment Table:
EquipmentID (Primary Key)
ReservationID (Foreign Key)
EquipmentType (Navigation, ChildSeat, SkiRack, etc.)
EquipmentCost

- AdditionalService Table:
ServiceID (Primary Key)
ReservationID (Foreign Key)
ServiceType (RoadsideAssistance, AdditionalDriver, Wifi, etc.)
ServiceCost
