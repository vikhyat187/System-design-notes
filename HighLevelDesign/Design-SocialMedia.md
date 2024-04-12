
Flipkart | Machine Coding Round - SDE 2: Build a Social Network!
Your task:
develop a console application simulating a social media platform .

Here's the scoop:

Users can:
Sign up and log in ([signup] & [login])
Share engaging content through posts ([post])
Build connections by following other users ([follow])
Spark conversations by replying to posts ([reply])
Express opinions with up/downvotes ([upvote]/[downvote])
Stay updated with a personalized news feed reflecting their interests ([shownewsfeed])
News Feed Algorithm:

The news feed prioritizes content based on a clever ranking system:

Followed Users: Posts from followed users take center stage!
Post Engagement: A higher score (upvotes minus downvotes) boosts visibility.
Conversation Starters: Posts with more replies become more alluring.
Freshness Factor: New posts trump older ones (all things being equal).
Bonus Round (unleash your creativity!)

Allow users to comment on replies and cast their votes.
Implement a time display that uses relative terms like "2m ago" or "1 hr ago".
Input & Output:

The system accepts commands and arguments through a text-based interface or a file.
Example: reply(feed_id, reply_text)

The entities can be 
- Users
- Posts
- Followers
- Replies
- 
