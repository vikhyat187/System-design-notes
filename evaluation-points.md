1. Scalability – Vertical vs horizontal; default to stateless horizontal scale
2. Latency & Throughput – Know P50/P95/P99 and tradeoffs
3. Capacity Estimation – Estimate QPS, storage, bandwidth
4. Networking Basics – TCP, HTTP/HTTPS, TLS, UDP, DNS
5. SQL vs NoSQL – Decide by access patterns, joins, consistency
6. Data Modeling – Structure to optimize hot queries
7. Indexing – Right indexes help reads, wrong ones hurt writes
8. Normalization – Normalize for writes, denormalize for reads
9. Caching – App/DB/CDN; write-through/back, TTLs
10. Cache Invalidation – TTLs, versioning, stampede protection

11. Load Balancing – L4/L7, RR, least-connections, hashing
12. CDN & Edge – Serve static content near users
13. Sharding – Hash/range/geo; handle hot keys, rebalancing
14. Replication – Sync/async, leader/follower, read replicas
15. Consistency Models – Strong, eventual, causal
16. CAP Theorem – In partition, pick A or C
17. Concurrency – Locks, MVCC, retries
18. Multithreading – Pools, contention, switching
19. Idempotency – Same request, same effect
20. Rate Limiting – Fairness and protection

21. Queues & Streams – Kafka, RabbitMQ; smooth spikes
22. Backpressure – Manage slow consumers/load shedding
23. Delivery Semantics – At-most/least/exactly-once
24. API Design – REST vs gRPC; human vs machine optimized
25. API Versioning – Additive only; never break clients
26. AuthN & AuthZ – OAuth2, JWT, RBAC/ABAC
27. Resilience – Circuit breakers, timeouts, retries
28. Observability – Logs, metrics, traces, SLI/SLO
29. Health Checks – Detect failures, auto-replace
30. Redundancy – Avoid SPOFs; use multi-AZ/region

31. Service Discovery – Dynamic endpoints, central config
32. Deployment – Canary, blue/green, rollbacks
33. Monolith vs Microservices – Split only if ops cost justified
34. Distributed Transactions – Use sagas, avoid 2PC
35. Event Sourcing/CQRS – Append-only logs, split models
36. Consensus – Raft, Paxos, quorum reads/writes
37. Data Privacy – Encryption, GDPR/CCPA
38. Disaster Recovery – Backups, RPO/RTO, test it
39. Serialization – JSON, Avro, Proto
40. Real-Time Delivery – Polling, SSE, WebSockets
