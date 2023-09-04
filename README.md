# WCC Postcode app

Simple postcode management API developed using Java with Spring Boot.

# Building the app
Prerequisite:
- Java JDK  17
- Maven 3.8 ++
- MariaDB
- Docker (*if you wish not to install MariaDB on your machine*)

*If you have MariaDB run locally, you can skip to step two.*

1. Build the docker-compose.yml to run MariaDB as a container:

	    docker compose -f docker-compose.yml up

2. Build the JAR file with following command:

	    mvn package install
    
3. Locate the JAR file in folder target, then run using this command:

	    java -jar <jar-file-name>.jar


# Testing the API
Successful build of this app will run locally on port 8080. We can test the following APIs:
- Calculate distance between two postcode
- Update postcode coordinates

These APIs require user authentication, see Authentication below.

## Authentication
You must perform authentication before sending request to certain APIs. Successful authentication will generated a JWT token to be use in header request later.

1. First, let's register a new user as follows;

	> POST http://localhost:8080/register/user
	> Body request:
	> `{	"username":"johndoe", "password":"password"	}`

2. You can now use the credentials to authenticate.
	> POST http://localhost:8080/auth/authenticate
	> Body request:
	> `{	"username":"johndoe", "password":"password"	}`
	
3. Successful login will generate a JWT token;

   >`{ "token": "eyJhbGciOiJIUzI1NiJ9...." }`

4. Copy the token response and include it on the header request.
	> Authorization: Bearer \<paste jwt-token here>
	
## Calculate distance between two postcode

Send request as following:

> GET http://localhost:8080/postcode/distance
> Query Param: postcode-a, postcode-b

API will response as below:

    {
	    "postcodeList": [
	        {
	            "id": 1,
	            "postcode": "AB10 1XG",
	            "latitude": 1.1234568,
	            "longitude": 2.1234568
	        },
	        {
	            "id": 2,
	            "postcode": "AB10 6RN",
	            "latitude": 57.137871,
	            "longitude": -2.121487
	        }
	    ],
	    "distance": 6239.946913964538,
	    "unit": "km"
    }

## Update postcode coordinates
Send request as following:

> PUT http://localhost:8080/postcode/detail/{postcode}
> Request body: `{ "latitude":  1.234,	"longitude":  1.78	}`

API will response as below:

    {
	    "id": 1,
	    "postcode": "AB10 1XG",
	    "latitude": 1.12345678,
	    "longitude": 2.12345678
    }



# Environment Variables
Please set environment variables below before running the app:
- DB_URL
- DB_PASSWORD
- DB_USERNAME
- JWT_ISSUER
- JWT_SIGNKEY

These variables can be found at **application.yml** and **docker-compose.yml**. You can either hardcode the value in the mentioned files or set the environment locally.

# OpenAPI

You can import JSON/YML OpenAPI definition at http://localhost:8080/swagger-ui/index.html

<br><br>
@author: hamizannordin