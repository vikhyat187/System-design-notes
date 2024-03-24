Functional Requirements 
- Users should be able to book a room
- They can be multiple hotels
- search hotels
- reservation
- cancellation
- payments


Non functional 
- concurrency (no double booking) for a room type
- scalable
- availability
- latency - (view and search should be fast)
- Security - IAM, DDOS / Rate limiters

we can store the room data as Inventory can help us have the concurrency mech
<img width="724" alt="image" src="https://github.com/vikhyat187/System-design-notes/assets/52795644/55e6c202-07b5-467a-b96f-55e75e690972">

The race condition can be avoided using transaction
if we use the serialisable - it can cause deadlock, waiting time increases
f
<img width="851" alt="image" src="https://github.com/vikhyat187/System-design-notes/assets/52795644/5335f406-1606-4c8f-b8a5-ccc0749235c8">
