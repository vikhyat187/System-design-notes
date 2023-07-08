Structure 
Nature 
Scalability
Property


## Relational
- Structure: Predefined 
- Nature: Concentrated
- Scalability: vertical is more supported( not horizontal)
- Property ACID property. Data integrirty, consistent

## No SQL
- sturcture: works on unstructured data
    - Key value : redis, dynamo db
    - Document db : mongo db  ```can query on both key and values```
    - Column db : cassandra
    - Graph db : Neo4J


## Mongodb vs Sql 
- Mongodb is optimised for write performance
- Sql is optimised for joins
- mongo db acid but against a single doc in the table.

<img width="958" alt="image" src="https://github.com/vikhyat187/System-design-notes/assets/52795644/fb96b4bb-4de2-48af-bf4c-2c28b58a9963">

A good Book software architect elevator

How to choose a database
<img width="1219" alt="image" src="https://github.com/vikhyat187/System-design-notes/assets/52795644/916abc95-076b-4365-bd74-74e45c47fbbe">

<img width="1247" alt="image" src="https://github.com/vikhyat187/System-design-notes/assets/52795644/97e7a457-ee52-4fa9-9aeb-a13269df81d7">

<img width="1014" alt="image" src="https://github.com/vikhyat187/System-design-notes/assets/52795644/3c55bbcd-f277-4268-950d-91e1365a9fa4">

<img width="1255" alt="image" src="https://github.com/vikhyat187/System-design-notes/assets/52795644/c564aa4a-8c46-49e0-a864-e6e265470d3f">

# Normalisation

1. First normal form (1NF): A table in 1NF is in a state where every column contains only unique data. This means that there are no duplicate rows in the table.
2. Second normal form (2NF): A table in 2NF is in a state where every column that is not the primary key is functionally dependent on the primary key. This means that the value of a column can be determined by the value of the primary key.
3. Third normal form (3NF): A table in 3NF is in a state where there are no transitive dependencies. A transitive dependency is a dependency between two columns where the value of one column is determined by the value of another column, which is in turn determined by the primary key.
<img width="831" alt="image" src="https://github.com/vikhyat187/System-design-notes/assets/52795644/5400e6ef-00bf-4564-838a-550ce247bff8">



<img width="1299" alt="image" src="https://github.com/vikhyat187/System-design-notes/assets/52795644/6b452c0a-ae85-44a5-9c6c-7cc374a5c6dc">
