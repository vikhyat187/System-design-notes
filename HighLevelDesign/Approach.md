<img width="1133" height="721" alt="image" src="https://github.com/user-attachments/assets/94adbb2a-9147-4920-9e9d-2f278997594a" />
- Steps to approach the data modelling in the system design interview

### Various database options
- Relational
- Document
- Key value store
- graph database
- wide column database

#### Relational
<img width="836" height="723" alt="image" src="https://github.com/user-attachments/assets/45769090-e07a-44aa-bc3e-9ec5a8533db6" />

#### document db
- we are using this when we don't know the what all fields are going to be added, schema fleixibility
<img width="836" height="334" alt="image" src="https://github.com/user-attachments/assets/cafd47af-f20c-452b-b802-ed35b6bdb215" />

#### key value store
<img width="798" height="383" alt="image" src="https://github.com/user-attachments/assets/5e420549-75ce-41ef-99ab-420062a1f3f8" />

#### wide column databases
- each row can have different number of columns
- we will have the faster writes here, as we appending new columns only
- time series dbs 
<img width="812" height="444" alt="image" src="https://github.com/user-attachments/assets/46cb9b0e-cd8b-451c-8f3a-7a9218bfbc15" />

#### Graph databases
- nodes and edges
- Don't use this in the interview
- Even facebook models their social graph using Mysql and not graph dbs
- <img width="448" height="395" alt="image" src="https://github.com/user-attachments/assets/a674934e-50a6-4df1-8e1f-1f92f93b03ac" />


### Schema design
#### three key factors
1. Data volume - what is the volume of the data (single or sharded db)
2. Access pattern - what APIs do you support
3. Consistency requirements - ACID / Eventual consistency

<img width="664" height="428" alt="image" src="https://github.com/user-attachments/assets/58a24e80-ea51-40bb-86b6-7dffbf2e11ff" />

### Normalisation and denormalisation
<img width="795" height="362" alt="image" src="https://github.com/user-attachments/assets/b6d6e104-1aa7-44da-a546-05366671a455" />

### indexes
<img width="886" height="426" alt="image" src="https://github.com/user-attachments/assets/52f5c7f3-471c-4d01-98ae-9919b3023149" />

### sharding
- we should avoid the cross shard joins
<img width="373" height="431" alt="image" src="https://github.com/user-attachments/assets/825b4f93-a835-4e55-9bf6-745c0f971495" />


## RPC
- this is good for the internal services communication
- as the data is sent in the binary format
- and both the sender and receiver should agree on a common format for the communication
- This avoids the HTTP overheads of HTTP headers, URL parsing and other things
<img width="762" height="406" alt="image" src="https://github.com/user-attachments/assets/d0f486e9-8a5f-40eb-b521-73b5e48ee86e" />

