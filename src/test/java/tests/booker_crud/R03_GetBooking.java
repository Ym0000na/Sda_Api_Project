package tests.booker_crud;

import base_urls.BookerBaseUrl;
import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utilities.ObjectMapperUtils;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static tests.booker_crud.R02_CreateBooking.bookingId;

public class R03_GetBooking extends BookerBaseUrl {

    /*
    Given
        https://restful-booker.herokuapp.com/booking/:id
    When
        Send get request
    Then
        status code should be 200
    And
        response body should be
        {
            "firstname": "Sally",
            "lastname": "Brown",
            "totalprice": 111,
            "depositpaid": true,
            "bookingdates": {
                "checkin": "2013-02-23",
                "checkout": "2014-10-23"
            },
            "additionalneeds": "Breakfast"
        }
     */

    //We have to run the package as all to get the bookingId <- xml
    @Test
    void getBookingTest(){

        //prepare the expected data
        JsonNode expectedData = ObjectMapperUtils.getJsonNode("booking");

        //send the request
        Response response = given(spec).get("/booking/"+bookingId);
        //response.prettyPrint();

        //do assertion
        response
                .then()
                .statusCode(200)
                .body(
                        "firstname", equalTo(expectedData.get("firstname").asText()),
                        "lastname", equalTo(expectedData.get("lastname").asText()),
                        "totalprice", equalTo(expectedData.get("totalprice").asInt()),
                        "depositpaid", equalTo(expectedData.get("depositpaid").asBoolean()),
                        "bookingdates.checkin", equalTo(expectedData.get("bookingdates").get("checkin").asText()),
                        "bookingdates.checkout", equalTo(expectedData.get("bookingdates").get("checkout").asText()),
                        "additionalneeds", equalTo(expectedData.get("additionalneeds").asText())
                );
    }
}
