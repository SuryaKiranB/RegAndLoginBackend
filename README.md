# RegAndLoginBackend

This is a Spring Boot project that provides registration and login functionalities. The application includes features such as email verification, password reset, and JWT (JSON Web Token) with the combination of Spring Security for authentication to secure API endpoints. Additionally, JUnit and RestAssured tests have been written to ensure the application's correctness.

## Prerequisites
Before running the application, ensure that you have the following:  
1)Java Development Kit (JDK) installed (version 19).  
2)Apache Maven build tool installed.  
3)MySQL or any other compatible database installed and running.  

## Setup
1)Download the code.  
2)Extract the zip file and open the file in your preferred IDE.  
3)Navigate to pom.xml and update maven to install all the dependencies to your project.
4)Navigate to application.properties file and edit the properties of the database and the server port if required.  
5)Go to the class named RegAndLoginApiApplication, right click on it and click on run as SpringBoot Application.  
6)You can view the logs and the serverport in which your application is running in.  
7)The application will be running on 'http://localhost:{your-specified-port}'

## Usage
1)Use an RestApi Client like Postman to interact with the application.  
2)Set the method as post and give the url for registration 'http://localhost:{your-specified-port}/api/v1/auth/registration'.  
Give the details in the body. Select the typr JSON.  
{
    "firstName":"Surya",
    "lastName":"Kiran",
    "email":"s3450@gmail.com",
    "password":"1234567"
}  
Click on send. Make sure you have given an unique email.  
On successful registration, You will receive a token in the response. This token will only be valid for 15v minutes. Any token can be generated using 'http://localhost:{your-specified-port}/api/v1/auth/resendToken'. You have to pass the email in the body. Take a note of it.  
3)Use the 'http://localhost:{your-specified-port}/api/v1/auth/confirm' endpoint to verify the email then only you can perform login.  
{
    "token":"9860ce04-4c4b-45ac-9af7-a74c038b7cc9"
}  
Follow this format for passing the token. Click on the send. On successful verification, A message 'Verfication Successful will be displayed'.  
4)You can now proceed with login since your email verification is done.  
Use the 'http://localhost:{your-specified-port}/api/v1/auth/login' for logging in.  
{
    "email":"s3450@gmail.com",
    "password":"1234567"
}  
On successful login, a jwt token is generated and printed on the response. take a note of it. If the email or password is incorrect then http status will forbidden with code 403. The JWT token will be valid only for 24 hours.  
5)Use the 'http://localhost:{your-specified-port}/demo/hi'. This is a secured application. For using this api, the jwt token have to provided in the authentication tab and selct the token type as 'Bearer'. The api will display hello on successful interaction.  
6)You can use reset password. First you have to provide the email to the url 'http://localhost:{your-specified-port}/api/v1/auth/sendFpToken' for requesting the token.  
{
  "email":"s345@gmail.com"
}  
The generated token will be valid only for 15 minutes. On delay reapeat the same process.  
7)Use the 'http://localhost:{your-specified-port}/api/v1/auth/forgot-password' for setting the new password.  
{
    "fpToken":"2e87e7bd-0bdd-48e8-96be-11a81564498a",
    "newPassword":"1234567"
}  
The password will be changed in the database.   
8)Set the method to GET for performing logout 'http://localhost:{your-specified-port}/api/v1/auth/logout'. Pass the jwt token as wou did with the secured api. You will be logged out and won't be able to access secured api's.  

## Security Features
1) Hashed Password.  
2) JWT.
3) Spring Security using Security Filter Chain.

## UI
Navigate to find the React Frontend I created for this backend project using this url 'https://github.com/SuryaKiranB/RegAndLoginFrontEnd/tree/master'.

## Testing
I have created unit testcases using JUnit and Api Automation tests using RestAssured.
I used JUnit with a combination of Mockito and creted tests for service layer.  
Below is the detailed description of API Automation testing with RestAssured.    

Here are the test cases for RestAssured Automation :  
1)testRegistrationSuccess: Verifies successful user registration.  
2)testRegistrationWithEmailExists: Ensures appropriate response when registering with an existing email.  
3)testTokenVerificationSuccess: Verifies successful email token verification.  
4)testTokenVerificationInvalidToken: Checks response when an invalid token is used for verification.  
5)testTokenVerificationExpiredToken: Simulates verification with an expired token.  
6)testResendTokenSuccess: Verifies successful resending of an email verification token.  
7)testResendTokenInvalidEmail: Checks response when resending a token to an invalid email.  
8)testSendFpTokenSuccess: Verifies successful sending of a forgot password token.  
9)testSendFpTokenInvalidEmail: Checks response when sending a token to an invalid email.  
10)testForgotPasswordSuccess: Verifies successful password reset.  
11)testForgotPasswordInvalidToken: Checks response when an invalid token is provided for password reset.    
The code uses RestAssured for making HTTP requests and JSONObject for constructing JSON payloads. Each test follows a similar structure of setting up the request, sending it, and verifying the response.  

Note: The provided code assumes a basic understanding of API testing and does not include setup or teardown steps. Depending on the testing framework or environment used, additional steps may be required. Check if your ide has a TestNG plugin installed. The data have to be changed on every iteration of the tests. 
