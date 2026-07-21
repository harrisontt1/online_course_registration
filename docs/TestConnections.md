# Test Connections
## Confirm your backend login endpoint works
Test this in Postman, your backend should expose:

`POST /api/login`

with a JSON body using the test user credentials:

`{
"username": "username",
"password": "password"
}`

and return:
`true   (on success)
false  (on failure)
`
* If Postman returns true, the backend is ready.
* If not, fix the backend first.

## Test Login
1. Run RegistrationApplication.java. 
2. You must see `Tomcat started on port(s): 8080
` If you don't see that line, the backend never started. 

Test the endpoint using Postman:
* From the login page `http://localhost:8080/login.html`
* From Postman, 
  * send: 
    `POST http://localhost:8080/api/login
    * Content-Type: application/json
`{
"username": "test",
"password": "test"
}`
  * you should get `true`


### Test endpoint `/api/login`
POST
`http://localhost:8080/api/login`

Body (JSON)
`{
  "username": "testuser",
  "password": "password123"
}
`
### Test dashboard `/api/student/{username}`
GET `http://localhost:8080/api/student/testuser`

Expected JSON:
`{
  "name": "Test User",
  "id": "123456"
}
`

### Test `/api/course`
GET `http://localhost:8080/api/course`

Expected JSON: 
`[
  {
    "id": "CMSC495",
    "name": "Capstone",
    "instructor": "Dr. Smith",
    "credits": 3,
    "meetingTime": "MWF 10AM"
  }
]
`
### Test `/api/financial/{username}`
 This populates the financial page
GET`http://localhost:8080/api/financial/testuser`

Expected JSON:
`{
  "status": "Paid in full"
}
`


<strong>Expected:</strong> 
* true → registration successful 
* false → registration failed