package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.*;

public class C03_NegativeAssertion {


    @Test
    void negativeAssertion(){

//        Given https://restful-booker.herokuapp.com/booking/0
//        When User send a GET Request to the url
        Response response = RestAssured.get("https://restful-booker.herokuapp.com/booking/0");
        response.prettyPrint();// prints nothing because link is invalid

//        Then HTTP Status code should be 404
        assertEquals(404, response.statusCode());

//        And Status Line should be HTTP/1.1 404 Not Found
        assertEquals("HTTP/1.1 404 Not Found", response.statusLine());

//        And Response body contains "Not Found"
        assertTrue(response.asString().contains("Not Found"));

//        And Response body does not contain "Clarusway"And
        assertFalse(response.asString().contains("Clarusway"));

//        Server is "Heroku"
        assertEquals("Heroku", response.header("Server"));

    }
}
