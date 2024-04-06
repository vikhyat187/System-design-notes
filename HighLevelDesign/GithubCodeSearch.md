Its exact search
1. search in all public repos
2. all repos search


How do you name the APIs
1. public repos
2. all repos - in org
3. single repo - search

the basic operation is the code search now what is changing is the scope of the search

since this is a GET API focus on the pagination and filtering

Response type
- file name
- line no and column no
- do we get the entire file?
- or get the set of lines around that?

We can use a trie to store if a word exists in the file if yes at which location and column.
- then on each file upload we create the trie
- 
