- overload IDE
- scaling is difficult (1 line bug also takes a lot time, tightly coupled)


Questions
- advantages and disadvantages of microservices
  - seperation of concerns is difficult and it will cost in future they should be loosely coupled
  - if a new feature goes live and some change in the output then debugging will get difficult as some service might break.
  - transaction management is difficult
  - <img width="779" alt="Screenshot 2023-02-24 at 11 28 25 PM" src="https://user-images.githubusercontent.com/52795644/221254661-7e997367-e8ba-40a4-9cbb-fd411f78572b.png">

- How to divide microservices

| decomposition | database | communication | integration | 
| --------------| -------- | ------------- | ------------|
| decompose via business domain | database per service|  api | api gateway | 
| decompose via subdomain (DDD) domain driven design | database shared | event  based | |

<img width="515" alt="Screenshot 2023-02-24 at 11 55 59 PM" src="https://user-images.githubusercontent.com/52795644/221260107-d61a4fab-17bc-4fce-8ec7-e153378e5627.png">
