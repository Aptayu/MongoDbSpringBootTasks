#Muzix Application Case Study 1 Name of Project Muzix Application 2 Objective/Vision This is an online application that helps users manage music listed on Music Database (​ https://www.last.fm​ ). Using this app, a user should be able to search and manage music. 3 Users of the system 4 Functional Requirements All Internet users

Application should allow all the users to perform the following activities: a. Save track information such as trackId, trackName, comments of the track. b. Display saved track. c. Update comments of saved track. d. Remove track 5 Non-Functional Requirements 1. App should be accessible from any location with access to the Internet.
App should be responsive to display consistently across multiple device screens.
App should have an intuitive     UI that can be operated by novice expert Internet users. 6 Tools and technologies to be used To Be Decided



System Design QnA



Best From Timeline

1. 



Timeline && Progress



28 Dec [Zerodha - Scaling 7M+ Postgres Tables]

1. Source : https://www.youtube.com/watch?v=4TE1xErXwGc
2. Problem : 
    1. It's costly to run queries on the huge data that’s generated. 
    2. If user requests of report generation was real time then there would be huge pressure on system given user count going into millions
    3. Multiple systems written in different tech stacks required report generation of different types 
3. Impact : 
    1. Increasing pressure on system and latency as queries grew in number
    2. Caching was a problem since report data was huge and had to be made available for couple of hours to multiple users
4. Solution :
    1. Having a middle ware which would queue queries and return a reference to result table
    2. This middle ware would execute queries on actual DB and store result in another DB in a new table which is unique for each query 
5. Observations / Learnings :
    1. As long as you have disk space you can have unlimited tables
    2. The middle ware could be a queueing system also like RabbitMQ 
    3. The final result store could be a NoSql document DB too



22 December  [Reddit System Design] [Developer Conference]

1. Source : Youtube / https://www.youtube.com/watch?v=nUcO7n4hek4&t=501s
2. Components :
    1. CDN 
    2. API Gateway
    3. Frontend
    4. R2 - monolith - each set of instances serve different purpose
        1. Load Balancer 
        2. App 
        3. PostGreSql + Memcache : caching frequently occurring count queries
        4. Cassandra : For heavy writes - high scalability and consistency
        5. Queue [RabbitMQ] - Vote Processing
3. Problem 1 : Vote count / memcache / sorting by votes
    1. Scenario : 
        1. One link - i.e either search or a specific category has option to sort by vote
        2. Votes coming in - rabbitQ processing and updating DB - invalidating cache
    2. Impact : Leads to more count queries on DB as load increases thereby increasing latency
    3. Solution : 
        1. Mutate in place : Add sort info to cache and modify the cached results in place. Locking required.
        2. This becomes a persisted cache - a denormalised index of links - put in cassandra
            1. [  (ID, vote count), ....  ]
    4. Question :
        1. Why locking required ?
            1. Without locking, two threads/processes might read the same initial vote count, compute new values, and write them back, overwriting each other's changes.
4. Problem 2 : Vote queue pileup (Delayed vote processing due to too many votes at peak hours) [currently using memcache on Postgres DB and Cassandra persisted cache] 
    1. Scenario : 
        1. Vote count of multiple posts is updated very slowly (couple of hours) as a result the trending posts don’t show up at top for couple of hours. 
    2. Impact : Trending posts don’t show up at top for couple of hours. 
    3. Root Cause :
        1. The locking added in problem 1 lead to high latency. Multiple queues were trying to update vote of same url.
    4. Solution :
        1. Partitioning : 
            1. Put votes into different queues based on subReddit ID of the link being voted on. 
            2. Fewer processors competing for same lock concurrently
            3. Worked really well
5. Problem 3 : A subset of votes were performing very poorly 
    1. Scenario :
        1. For domain searches - domains are present across 
    2.  
6.  





22 November - 21 December 

No Progress

21 November  [Part 1 - Amazon Dynamo DB]  [DB Sharding - Bit Division]

1. Source : Youtube 
2. https://www.youtube.com/watch?v=wbZZftuLs4o - Part 1 - Amazon Dynamo DB - Research paper
    1. Dynamo DB is a Highly Available Key value store - WHY?
        1. No locks on tables / items
3. https://www.youtube.com/watch?v=TdhXPsDXdAI - Instagram scaled to 14 million users - how wrt DB?
    1. 1 table won’t do. Definitely need sort support to fetch posts in timeline. 
    2. Requirements :
        1. Multi-sharded DB for low latency and high scalability
        2. Data items sortable by time
        3. Data item identifiers : Ideally 64 bit 
        4. Less movement parts (less integration points)
    3. Options :
        1. Servers communicating with Zookeeper nodes to get shard Id for a given user :
            1. Leads to an integration touch point
        2. We can use something like :
            1. 41 bits for time in ms
                1. For make sure that data is sorted across all shards
            2. 13 bits for shard id
                1. Total shards we can have = 2^13
            3. 10 bits for auto increment sequence :
                1. To avoid collisions when multiple writes happen to same shard at same millisecond
            4. DB Sharding - Bit Division Explained in Detail
        3. 






DB Sharding - Bit Division Explained in Detail

1. 41 Bits for Time in Milliseconds

* Purpose:
    * Ensures IDs are time-ordered across all shards.
    * Allows for temporal sorting of records without additional indexing overhead.
* How it works:
    * This portion of the ID encodes the current timestamp in milliseconds since a fixed epoch (e.g., Unix epoch or a custom starting point).
    * By having 41 bits, it can represent 
        241−12
        41−1 milliseconds, which covers about 69 years.
* Advantages:
    * All IDs across shards maintain chronological order, enabling efficient queries based on time (e.g., retrieving posts made recently).

2. 13 Bits for Shard ID

* Purpose:
    * Identifies the shard where the data is stored, ensuring IDs are unique across all shards.
* How it works:
    * Each shard is assigned a unique ID between 0 and 
        213−12
        13−1 (8191 shards maximum).
    * When a record is created in a specific shard, the shard ID is embedded in the ID.
* Advantages:
    * Supports scalability by enabling up to 8191 shards.
    * Avoids conflicts when generating IDs in parallel across multiple shards.

3. 10 Bits for Auto-Increment Sequence

* Purpose:
    * Resolves conflicts when multiple records are created simultaneously in the same shard and millisecond.
* How it works:
    * Each shard maintains a 10-bit counter (sequence) that auto-increments for every new record created in the same millisecond.
    * The counter resets to 0 at the start of a new millisecond.
    * With 10 bits, the sequence can represent up to 
        210−12
        10−1 (1024) records per millisecond per shard.
* Advantages:
    * Prevents ID collisions for high-traffic shards.
    * Supports a high write throughput of up to 1024 records per millisecond per shard.



20 November [Part 4 - Amazon Dynamo DB - Research paper]

1. Source : Youtube 
2. https://www.youtube.com/watch?v=vhcz2D10s_g : Part 4 - Amazon Dynamo DB - Research paper
    1. Reconciliation of conflicts :
        1. Problem : Multiple writes happened across multiple nodes since partitioning is there
        2. Question :
            1. How to calculate final state for a read operation ?
        3. Explanation :
            1. Can’t use timestamp since time can vary across servers even within same data centre by couple of seconds
            2. Vector clocks (timestamp) is associated with each write and update to keep track of updates 
            3. Reconciliation is handed off to be performed at client side
    2. Vector Clocks :
        1. Vector clocks explained better :
    3. A quorum system :
        1. R reads / W writes are parameters of this kind of system. 
            1. Number of reads (R) and writes (W) happen within server for any incoming request from clients.
        2. CAP theorem - Consistency / Availability / Partitioning 
            1. Partitioning is there as of now so tradeoff would be amongst Consistency and Availability
            2. If W is high then we are increasing Consistency and trading off Latency
            3. If W is low then we are reducing Latency and trading off Consistency



Vector clocks explained better :

1. Server and data are different entities
2. Node = server instance 
3. Replica = data replica and not server replica
4. Each node has its own vector clock since its a property of each row of each table

Layman's Analogy :

Imagine a group of people working on a shared shopping list. Each person keeps track of how many times they've added something to the list:
Person A writes "Apples" on the list → Vector Clock: { "PersonA": 1, "PersonB": 0 }.
Person A writes "Oranges" on the list → Vector Clock: { "PersonA": 2, "PersonB": 0 }.
Person B writes "Bananas" on the list → Vector Clock: { "PersonA": 2, "PersonB": 1 }.

Here:
The "shopping list" is the shared data.
Each person (A or B) is a node.
The vector clock tracks how many updates each person has made.


FAQ’s :

1. 1. Where is the vector clock stored?
    1. The vector clock is stored alongside the data it tracks.
2. 2. How is the vector clock accessed?
    1. Whenever a node (server) processes an update to an item, it first reads the vector clock from the item's metadata.
3. How come it doesn’t get messed up with multiple updates?
    1. Conflict Detection:
    2. When two nodes update the same item simultaneously, they may generate vector clocks that cannot be compared directly (e.g., { "NodeA": 3, "NodeB": 2 } vs. { "NodeA": 2, "NodeB": 3 }).
    3. Such vector clocks indicate concurrent updates.
    4. Conflict Resolution:
    5. The system may:
        1. Merge updates intelligently (e.g., combine changes to different fields).
        2. Use application-specific rules to choose one update over the other.
        3. Use strategies like last write wins if a simpler resolution is acceptable.
4. Is a vector clock present for each item in the data store?
    1. Yes, typically each item in the data store has its own vector clock.
    2. What does “item” mean?
    3. An item refers to a single record or entry in the data store, not the entire table.
    4. For example:
        1. In DynamoDB, an item is a single row in a table.
        2. If you have a table for users, each user (record) is an item.
        3. Each item’s vector clock is independent, so updates to one item don’t affect the vector clocks of other items.
5. Is it stored in shared location ?
    1. No



19 Nov

1. How would you store the Order details in your db, so that ordered for the past 6 months would be queried faster?

Touch Point:: Dynamic filtering in AWS, hot storage and cold storage


1. How would you process one huge file in one go, say 1 TB?

Touchpoint:: Divide it in chunks and use multithreading, parallel stream.





Wednesday, November 20, 2024

1. Your microservice architecture uses multiple databases, including both SQL and NoSQL databases, for different services. How do you ensure data consistency across these databases, especially when a business process involves changes across both types?


Sources :

1. https://www.youtube.com/@thestupidcsguy8948  - The Stupid CS Guy
2. https://www.youtube.com/@Techie007 - Sukhad Anand
3. https://www.youtube.com/@gkcs - Gaurav Sen
4. https://www.youtube.com/@preetam.keshari - Preetam Keshari
5. https://www.youtube.com/@IGotAnOffer-Engineering - IGotAnOffer: Engineering
6. https://www.youtube.com/@SDFC - System Design Fight Club
7. https://www.youtube.com/@ThinkSoftware - Think Software
8. https://www.youtube.com/@SystemDesignInterview - System Design Interview
9. https://www.youtube.com/@getsdeready - GET SDE READY
10. https://www.youtube.com/@SystemDesignSchool - System Design School
11. 
12. https://youtube.com/@perfology?si=5uP0sFcrePRHafaH - Perfology [Real Design Overviews]
13. https://youtube.com/@infoq?si=SlQI2F0Jo0F3EqZM - InfoQ [Real Design Overviews]
14. 





