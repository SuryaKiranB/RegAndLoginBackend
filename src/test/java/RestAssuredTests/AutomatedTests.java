package RestAssuredTests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import net.minidev.json.JSONObject;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

public class AutomatedTests {

    @Test
    public void testRegistrationSuccess() {
        JSONObject userJson = new JSONObject();
        userJson.put("firstName", "John");
        userJson.put("lastName", "Doe");
        userJson.put("email", "johndoey@example.com");
        userJson.put("password", "password123");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(userJson.toString())
                .when()
                .post("http://localhost:8082/api/v1/auth/registration")
                .then()
                .assertThat().statusCode(200)
                .body(containsString("Registered Successfully"));
    }

    @Test
    public void testRegistrationWithEmailExists() {
        JSONObject userJson = new JSONObject();
        userJson.put("firstName", "John");
        userJson.put("lastName", "Doe");
        userJson.put("email", "janedoe@example.com");
        userJson.put("password", "password123");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(userJson.toString())
                .when()
                .post("http://localhost:8082/api/v1/auth/registration")
                .then()
                .assertThat().statusCode(200)
                .body(containsString("Email already exists"));
    }

    @Test
    public void testTokenVerificationSuccess() {
        String validToken = "3fa11527-9799-476e-81a0-1d30ddc3c208";

        JSONObject verifyEmailJson = new JSONObject();
        verifyEmailJson.put("token", validToken);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(verifyEmailJson.toString())
                .when()
                .post("http://localhost:8082/api/v1/auth/confirm")
                .then()
                .assertThat().statusCode(200)
                .body(containsString("Verification Successful"));
    }

    @Test
    public void testTokenVerificationInvalidToken() {
        String invalidToken = "invalidToken123";

        JSONObject verifyEmailJson = new JSONObject();
        verifyEmailJson.put("token", invalidToken);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(verifyEmailJson.toString())
                .when()
                .post("http://localhost:8082/api/v1/auth/confirm")
                .then()
                .statusCode(200)
                .body(containsString("Invalid Token"));
    }

    @Test
    public void testTokenVerificationExpiredToken() {
        String expiredToken = "9860ce04-4c4b-45ac-9af7-a74c038b7cc9";

        JSONObject verifyEmailJson = new JSONObject();
        verifyEmailJson.put("token", expiredToken);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(verifyEmailJson.toString())
                .when()
                .post("http://localhost:8082/api/v1/auth/confirm")
                .then()
                .statusCode(200)
                .body(containsString("Token got Expired"));
    }
    @Test
    public void testResendTokenSuccess() {
        String email = "johndoe@example.com";

        JSONObject resendTokenJson = new JSONObject();
        resendTokenJson.put("email", email);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(resendTokenJson.toString())
                .when()
                .post("http://localhost:8082/api/v1/auth/resendToken")
                .then()
                .statusCode(200)
                .body(not(containsString("Invalid Email")));
    }

    @Test
    public void testResendTokenInvalidEmail() {
        String email = "nonexistent@example.com";

        JSONObject resendTokenJson = new JSONObject();
        resendTokenJson.put("email", email);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(resendTokenJson.toString())
                .when()
                .post("http://localhost:8082/api/v1/auth/resendToken")
                .then()
                .statusCode(200)
                .body(containsString("Invalid Email"));
    }

    @Test
    public void testSendFpTokenSuccess() {
        String email = "johndoe@example.com";

        JSONObject resendTokenJson = new JSONObject();
        resendTokenJson.put("email", email);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(resendTokenJson.toString())
                .when()
                .post("http://localhost:8082/api/v1/auth/sendFpToken")
                .then()
                .statusCode(200)
                .body(not(containsString("Invalid Email")));
    }
    @Test
    public void testSendFpTokenInvalidEmail() {
        String email = "nonexistent@example.com";

        JSONObject resendTokenJson = new JSONObject();
        resendTokenJson.put("email", email);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(resendTokenJson.toString())
                .when()
                .post("http://localhost:8082/api/v1/auth/sendFpToken")
                .then()
                .statusCode(200)
                .body(containsString("Invalid Email"));
    }

    @Test
    public void testForgotPasswordSuccess() {
        String fpToken = "1c615d29-6cb6-4c32-b496-abe349e7b589";
        String newPassword = "newPassword123";

        JSONObject forgotPasswordJson = new JSONObject();
        forgotPasswordJson.put("fpToken", fpToken);
        forgotPasswordJson.put("newPassword", newPassword);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(forgotPasswordJson.toString())
                .when()
                .post("http://localhost:8082/api/v1/auth/forgot-password")
                .then()
                .statusCode(200)
                .body(containsString("Changed Password Successfully"));
    }

    @Test
    public void testForgotPasswordInvalidToken() {
        String fpToken = "invalidToken123";
        String newPassword = "newPassword123";

        JSONObject forgotPasswordJson = new JSONObject();
        forgotPasswordJson.put("fpToken", fpToken);
        forgotPasswordJson.put("newPassword", newPassword);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(forgotPasswordJson.toString())
                .when()
                .post("http://localhost:8082/api/v1/auth/forgot-password")
                .then()
                .statusCode(200)
                .body(containsString("Invalid Token"));
    }

    @Test
    public void testForgotPasswordExpiredToken() {
        String fpToken = "cf7ace88-af8b-41e5-b548-3e054109e7a7";
        String newPassword = "newPassword123";

        JSONObject forgotPasswordJson = new JSONObject();
        forgotPasswordJson.put("fpToken", fpToken);
        forgotPasswordJson.put("newPassword", newPassword);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(forgotPasswordJson.toString())
                .when()
                .post("http://localhost:8082/api/v1/auth/forgot-password")
                .then()
                .statusCode(200)
                .body(containsString("Token got Expired"));
    }
    @Test
    public void testLoginSuccess() {
        String email = "s90@gmail.com";
        String password = "Surya@2002";

        JSONObject loginJson = new JSONObject();
        loginJson.put("email", email);
        loginJson.put("password", password);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(loginJson.toString())
                .when()
                .post("http://localhost:8082/api/v1/auth/login")
                .then()
                .statusCode(200)
                .body(not(containsString("User did not Registered")))
                .body(not(containsString("Email not verified")));
    }
    @Test
    public void testLoginIncorrectPassword() {
        String email = "s90@gmail.com";
        String password = "Surya@22";

        JSONObject loginJson = new JSONObject();
        loginJson.put("email", email);
        loginJson.put("password", password);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(loginJson.toString())
                .when()
                .post("http://localhost:8082/api/v1/auth/login")
                .then()
                .statusCode(403);
    }

    @Test
    public void testLoginUserNotRegistered() {
        String email = "nonexistent@example.com";
        String password = "password123";

        JSONObject loginJson = new JSONObject();
        loginJson.put("email", email);
        loginJson.put("password", password);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(loginJson.toString())
                .when()
                .post("http://localhost:8082/api/v1/auth/login")
                .then()
                .statusCode(200)
                .body(containsString("User did not Registered"));
    }

    @Test
    public void testLoginEmailNotVerified() {
        String email = "johndoe@example.com";
        String password = "password123";

        JSONObject loginJson = new JSONObject();
        loginJson.put("email", email);
        loginJson.put("password", password);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(loginJson.toString())
                .when()
                .post("http://localhost:8082/api/v1/auth/login")
                .then()
                .statusCode(200)
                .body(containsString("Email not verified"));
    }

    @Test
    public void testLogoutValidToken(){
        String jwtToken="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzOTBAZ21haWwuY29tIiwiaWF0IjoxNjg2NjQyNDYwLCJleHAiOjE2ODY2NDM5MDB9.Eng-Yu0-OoRAywW8T_XGH-5bJtRkmG3skpKea-HsC5I";
        RestAssured.given()
                .header("Authorization", "Bearer " + jwtToken)
                .when()
                .get("http://localhost:8082/api/v1/auth/logout")
                .then()
                .statusCode(200);
    }

    @Test
    public void testHelloInvalidJwt(){
        String jwtToken="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzOTBAZ21haWwuY29tIiwiaWF0IjoxNjg2NjQyNDYwLCJleHAiOjE2ODY2NDM5MDB9.Eng-Yu0-OoRAywW8T_XGH-5bJtRkmG3skpKea-HsC5I";
        RestAssured.given()
                .header("Authorization","Bearer "+ jwtToken)
                .when()
                .get("http://localhost:8082/demo/hi")
                .then()
                .statusCode(403);
    }
    @Test
    public void testHelloValidJwt(){
        String jwtToken="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzOTBAZ21haWwuY29tIiwiaWF0IjoxNjg2NjQ1MTg3LCJleHAiOjE2ODY2NDY2Mjd9.qbomjEkiok1I6rsePgrxANyUOWSrRkGlJYB3ROYPl_4";
        RestAssured.given()
                .header("Authorization","Bearer "+ jwtToken)
                .when()
                .get("http://localhost:8082/demo/hi")
                .then()
                .statusCode(200)
                .body(containsString("Hello"));
    }
}
