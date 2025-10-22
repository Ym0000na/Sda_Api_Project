package tests.booker_crud;

import base_urls.BookerBaseUrl;
import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utilities.ObjectMapperUtils;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static tests.booker_crud.R02_CreateBooking.bookingId;

public class R04_UpdateBooking extends BookerBaseUrl {

    /*
    Given
        https://restful-booker.herokuapp.com/booking/:id
    And
        {
            "firstname" : "James",
            "lastname" : "Brown",
            "totalprice" : 111,
            "depositpaid" : true,
            "bookingdates" : {
                "checkin" : "2018-01-01",
                "checkout" : "2019-01-01"
            },
            "additionalneeds" : "Breakfast"
        }
    When
        send put request
    Then
        status code should be 200
    And
        response body should be like this
            {
            "firstname" : "James",
            "lastname" : "Brown",
            "totalprice" : 111,
            "depositpaid" : true,
            "bookingdates" : {
                "checkin" : "2018-01-01",
                "checkout" : "2019-01-01"
            },
            "additionalneeds" : "Breakfast"
        }
     */

    @Test
    void updateBookingTest(){

        //prepare the payload
//        JsonNode payload = ObjectMapperUtils.getJsonNode("booking");
//        ObjectMapperUtils.updateJsonNode(payload, "firstname", "James");
//        ObjectMapperUtils.updateJsonNode(payload, "lastname", "Brown");
//        ObjectMapperUtils.updateJsonNode(payload, "totalprice", 111);
//        ObjectMapperUtils.updateJsonNode(payload, "depositpaid", true);
//        ObjectMapperUtils.updateJsonNode(payload, "additionalneeds", "Breakfast");
//        ObjectMapperUtils.updateJsonNode(payload.get("bookingdates"), "checkin", "2025-10-22");
//        ObjectMapperUtils.updateJsonNode(payload.get("bookingdates"), "checkout", "2025-10-27");


        //or u could just create a new booking json
        JsonNode payload = ObjectMapperUtils.getJsonNode("booking_updated");

        //send request - we added a header with the token to baseUrl
        Response response = given(spec).body(payload).put("/booking/"+bookingId);
        response.prettyPrint();

        //do assertions
        response
                .then()
                .statusCode(200)
                .body(
                        "firstname",equalTo(payload.get("firstname").asText()),
                        "lastname",equalTo(payload.get("lastname").asText()),
                        "totalprice",equalTo(payload.get("totalprice").asInt()),
                        "depositpaid",equalTo(payload.get("depositpaid").asBoolean()),
                        "bookingdates.checkin",equalTo(payload.get("bookingdates").get("checkin").asText()),
                        "bookingdates.checkout",equalTo(payload.get("bookingdates").get("checkout").asText()),
                        "additionalneeds",equalTo(payload.get("additionalneeds").asText())

                );

    }
}
