## ACID properties
- Atomicity
- Isolation
- Consistency
- Durability

## Read phenomena
- Dirty read (Read a value which is not commited)
- Lost update
- Not repeatable read (Not able to read your update)
- Phantom read(getting extra rows on range query)

## Isolation levels
- Read uncommitted
- Read commited
- Repeatable read (only read the commited values before the start of the transaction)
- Serialisable (Using lock and doing in serial)

## Consistency
- Consistency in data
- consistency in reads
  - Strong consistency
  - Eventual consistency
- YT only shows 95M subscribers and don't show the other values after 95 like 10lakhs
- They prefer performance over consistency

## Durability
- Storing the commited data in non volatile storage, so that its not lost.

## Row vs Column based database

| Row | Column |
| --- | -------| 
| stores data in rows | stores data in cols| 
| optimal read/ writes | not optimal | 
| good for multi col query |  Not good with multi col query | 
| OLTP | OLAP| 
| Compressison isn't efficient | efficient | 
| aggregation isn't efficient | efficient | 
| efficient query with multi col | not efficient with multi col | 
| ------------------| --------------------- | 
