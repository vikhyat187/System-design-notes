FK - Delivery Service

Overview
Write an application that allows delivery agents to pick up orders and deliver them to a Pincode. Application should be able to do the following -

Users should be able to create an order, requiring the following details
Order name
Order Pincode.
For example: Order A for Pincode 560087

Admin should be able to create delivery agents who can deliver the order created.
[ Multiple delivery agents can be registered to a single Pincode ]

[ Agent can deliver to only one Pincode ] - Extension Bonus - Agent should be able to deliver to multiple Pincode.

For example: Agent A who can deliver to Pincode 560087

A driver function that would be able to execute this process and show metrics for the status of order delivery.
For example:
Order A has been picked up by Agent A for Pincode 560087
Order A has been delivered by agent A for Pincode 560087

Test Case (Basic Functionalities)

createOrder(Order A, 560087)
createOrder(Order B, 560088)
createOrder(Order C, 560089)
createOrder(Order D, 560087)
createAgent(AgentA, 560087)
createAgent(AgentB, 560088)
createAgent(AgentC, 560089)

On execution:

Agent A has picked up Order A
Agent A has delivered Order A to 560087
Agent B has picked up Order B
Agent B has delivered Order B to 560088
Agent C has picked up Order C
Agent C has delivered Order C to 560089
Agent A has picked up Order D
Agent A has delivered Order D to 560087

Bonus
Add functionality so that Agent can deliver to multiple pincodes

Add functionality wherein the application also accounts for the time required for a particular scheduled order delivery -
createOrder(Order A, 560087, “10:30 - Mar 22, 2025”, 30) which means Order A scheduled for 10:30 am on Mar 22, 2025 will take 30 mins to deliver in pincode 560087

Change the execution output accordingly like -
Agent A has picked up Order A at 10:30 AM, Mar 22, 2025
Agent A has completed delivery of Order A to 560087 at 11:00 AM, Mar 22, 2025


## Solution
