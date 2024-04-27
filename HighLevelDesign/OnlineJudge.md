## Problem Statement : Online Code Judge / Hackathon system

- Org is conducting its annual hackathon and wants to create an interactive platform to host the event. 
- Contestants need to maximize their score over this period by either solving more problems or more difficult problems. 
- Design the backend to support this platform.


#### Mandatory Functionalities

- The system should have the capability to add problems to the Questions Library.
- Contestants should be able to register themselves with their name and their department name.
- A problem should have attributes like description, tag, difficulty level (easy, medium, hard), score.
- Contestants should be able to filter problems based on difficulty level or tags and sort them based on score (design should be extensible to other attributes )
- A contestant should be able to solve a problem as well as get the list of problems solved by him/her.
- A contestant should be able to see the number of users that have solved a given problem and average time taken to solve that problem.
- Scoring strategy for a problem could simply be to award the score assigned for the problem or could be something different like a combination of score and time.
- Return the current leader of the contest
- Users should be able to get curations like Top 10 most liked problems of a certain tag.


#### Extension (bonus) Functionalities

- On solving a problem, users should get a recommendation of top 5 problems based on relevance.
  - The recommendation strategy for problems could simply be similar tags or extensible to include other factors like number of users who have solved a particular problem or a combination of factors ( Design should be extensible).
Capabilities

----------


#### Function signatures

- Below are various functions that should be supported with necessary parameters passed.
- These are just suggestions, the signatures can be altered as long as the functionalities are provided.


  - Registering a problem - `addProblem()`

  - Registering a user - `addUser()`

  - Fetch the list of problems - `fetchProblems()` 
    - Should take as input filtering and sorting criterias and return all matching problems in the right order.
    - The result should contain problem attributes like name, tag, difficulty level, score etc.
    - Should also display number of users who have solved a problem and the average time taken for that problem

  - Solve a problem - `solve()` - 
    - Exposed to a contestant to mark a problem as solved
    - [ For the extension problem, this function should return next 5 recommended problems ]

  - Fetch solved problems for a user - `fetchSolvedProblems()`

  - Get leader - `getLeader()` - Returns the name and department of the leader

  - Get top n liked problems of a certain tag - `getTopNProblems()`


-------

#### Guidelines

- You should store the data in-memory using a language-specific data-structure.
- Implement clear separation between your data layers and service layers.
- Simple and basic function are expected as entry point - no marks for providing fancy * restful API or framework implementation
- Because this is a machine coding round, heavy focus would be given on data modeling, code quality etc, candidate should not focus too much time on algo which compromises with implementation time



#### Expectations:

- Your code should cover all the mandatory functionalities explained above.
- Your code should be executable and clean.
- Your code should be properly refactored, and exceptions should be gracefully handled.
- Appropriate errors should be displayed on the console



#### How will you be evaluated?

- Code Should be working
- Code readability and testability
- Separation Of Concerns
- Abstraction
- Object-Oriented concepts.
- Language proficiency.
- Scalability
- Test Coverage (Bonus Points)
- [execution time limit] 3 seconds (java)
