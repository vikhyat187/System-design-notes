Low Level design of a Bike Rental application. Major emphasis on Usecases, class structures, interfaces and their interactions. Asked to implement one of the method to searchBikes for given list of filters and location.

## Requirements
- Users can see a list of vehicles available for the rent
- User can search a vehicle type for the lease
- User can choose the vehicle for the dates
- User can make the payment for booking
- System should be able to track the vehicle inventory
- System should be able to track the late fees and other charges
- System should be able to support concurrent bookings and avoid double booking
- each vehicle store has its own inventory
- Vechicle has the registration number, type, specifications
- System manages the booking, cancellation and modification of bookings
- Users can add the extra things while making the reservation
  - For eg insurance
  - driver
  - wifi

## Entities
- User
- Vehicles
- Store
- Location
- VehicleInventory (This will be at the store level)
- Booking
- Notification
- Late fees
- 
