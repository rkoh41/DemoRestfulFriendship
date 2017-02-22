# DemoRestfulFriendship
Just my demo material

Environment details
- IDE Eclipse Neon
- Tomcat v8.0 Server at localhost
- Data source: remote (a free mysql db), please ensure you run on terminal that has internet connection

Demo requirement topics
1. The suggested json inputs are not well-formed, I have set up the API to accept well-formed json input only but the general field structure remains the same. I shall provide the sample urlencoded param values (assuming you will invoke using tools like Postman) below in each user story so that you can simply create a variety of results by changing the email IDs highlighted in yellow.
2. I did run out of time to do the things I wanted to do, namely
	- add more test cases into suite
	- detect @mentions in json input "text" field of user story #6


Test inputs

Use Case #1
1. As a user, I need an API to create a friend connection between two email addresses.

Sample json input param:
{
	"friends": [
		"USER_A@example.com",
		"USER_B@example.com"
	]
}

Urlencoded json input param:
%7B%0A%09%22friends%22%3A%20%5B%0A%09%09%22USER_A%40example.com%22%2C%0A%09%09%22USER_B%40example.com%22%0A%09%5D%0A%7D

POST URI complete
http://localhost:8080/DemoRestfulFriendship/demo/friendconnection/create?jsonString=%7B%0A%09%22friends%22%3A%20%5B%0A%09%09%22USER_A%40example.com%22%2C%0A%09%09%22USER_B%40example.com%22%0A%09%5D%0A%7D


Use Case #2
2. As a user, I need an API to retrieve the friends list for an email address.

Sample json input param:
{
  "email": "USER_A@example.com"
}

Urlencoded json input param:
%7B%0D%0A++%22email%22%3A+%22USER_A%40example.com%22%0D%0A%7D

POST URI complete
http://localhost:8080/DemoRestfulFriendship/demo/friendconnection/friendlist?jsonString=%7B%0D%0A++%22email%22%3A+%22USER_A%40example.com%22%0D%0A%7D


Use Case #3
3. As a user, I need an API to retrieve the common friends list between two email addresses.

Sample json input param:
{
  "friends":
    [
      "USER_A@example.com",
      "USER_B@example.com"
    ]
}

Urlencoded json input param:
%7B%0D%0A++%22friends%22%3A%0D%0A++++%5B%0D%0A++++++%22USER_A%40example.com%22%2C%0D%0A++++++%22USER_B%40example.com%22%0D%0A++++%5D%0D%0A%7D

POST URI complete
http://localhost:8080/DemoRestfulFriendship/demo/friendconnection/commonfriends?jsonString=%7B%0D%0A++%22friends%22%3A%0D%0A++++%5B%0D%0A++++++%22USER_A%40example.com%22%2C%0D%0A++++++%22USER_B%40example.com%22%0D%0A++++%5D%0D%0A%7D


Use Case #4
4. As a user, I need an API to subscribe to updates for an email address.

Sample json input param:
{
  "requestor":"USER_A@example.com",
  "target":"USER_B@example.com"
}

Urlencoded json input param:
%7B%0D%0A++%22requestor%22%3A%22USER_A%40example.com%22%2C%0D%0A++%22target%22%3A%22USER_B%40example.com%22%0D%0A%7D

POST URI complete
http://localhost:8080/DemoRestfulFriendship/demo/subscription/subscribe?jsonString=%7B%0D%0A++%22requestor%22%3A%22USER_A%40example.com%22%2C%0D%0A++%22target%22%3A%22USER_B%40example.com%22%0D%0A%7D


Use Case #5
5. As a user, I need an API to block updates for an email address.

Sample json input param:
{
  "requestor":"USER_A@example.com",
  "target":"USER_C@example.com"
}

Urlencoded json input param:
%7B%0D%0A++%22requestor%22%3A%22USER_A%40example.com%22%2C%0D%0A++%22target%22%3A%22USER_C%40example.com%22%0D%0A%7D

POST URI complete
http://localhost:8080/DemoRestfulFriendship/demo/subscription/subscribe?jsonString=%7B%0D%0A++%22requestor%22%3A%22USER_A%40example.com%22%2C%0D%0A++%22target%22%3A%22USER_C%40example.com%22%0D%0A%7D


Use Case #6
6. As a user, I need an API to retrieve all email addresses that can receive updates for an email address.

Sample json input param:
{
  "sender":  "USER_C@example.com",
  "text": "Hello World! kate@example.com"
}

Urlencoded json input param:
%7B%0D%0A++%22sender%22%3A++%22USER_C%40example.com%22%2C%0D%0A++%22text%22%3A+%22Hello+World%21+kate%40example.com%22%0D%0A%7D

POST URI complete
http://localhost:8080/DemoRestfulFriendship/demo/subscription/canreceiveupdate?jsonString=%7B%0D%0A++%22sender%22%3A++%22USER_C%40example.com%22%2C%0D%0A++%22text%22%3A+%22Hello+World%21+kate%40example.com%22%0D%0A%7D
