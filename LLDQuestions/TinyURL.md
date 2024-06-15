[Design a URL shortner](https://medium.com/@sandeep4.verma/system-design-scalable-url-shortener-service-like-tinyurl-106f30f23a82)
- how should be the operation for putting the data into the database look like
	- First you can try getting the value for the tiny URL, if its not present then only have a put.
     - In this case if they are two concurrent request both will write the long url for the short url and this will corrupt the system
  - Second you can put if its absent
    - you will need help from the database in this case
  - Third is put, then get to check if the URL put is correct and if not then put the value with new short URL again
    - This if the random url generator is efficient should only take one attempt else it can take lots of attempts and the no of requests are high
<img width="919" alt="image" src="https://github.com/vikhyat187/System-design-notes/assets/52795644/153a12bf-4298-4933-ba8c-ae95a7db5262">

- How many URLs shortened per month - 100M
- Functional Requirements:

- Service should be able to create shortened url/links against a long url
- Click to the short URL should redirect the user to the original long URL
- Shortened link should be as small as possible
- Users can create custom url with maximum character limit of 16
- Service should collect metrics like most clicked links
- Once a shortened link is generated it should stay in system for lifetime

- Assuming they are 200:1 for the read : write ratio
- No of unique URLs = 100M
- No of urls generated per / sec= 100M / 30 * 24 * 3600 ~= 40 URLs / sec
- With 200:1 read / write ratio
- number of redirections per sec = 40 * 200 = 8000

Storage 
- generating 100M urls per month and for 12 months to store for 100years
- 100M * 12 * 100 = 120B
- the attributes are (Id, shortUrl, longUrl, createdDate) = 500 bytes = 120 billion * 500 bytes = 12billion * 5kb = 60TB

Total estimates
- shortened urls = 40/s
- shortened urls in 100 years = 120B
- total url request/redirections = 8000/s
- storage for 100 years = 60tb

Shortening schemes
- URl encoding
  -   base62
  -   MD5
- Key generation service
For the size of the short url
5 len - 62^5
6 len - 62^6
7 len - 62^7
