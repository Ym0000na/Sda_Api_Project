package tests.booker_crud;

import base_urls.BookerBaseUrl;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class R01_GetBookingIds extends BookerBaseUrl {
    /*
        Write an automation test to test all endpoints using the documentation available at
        https://restful-booker.herokuapp.com/apidoc/index.html.
*/

    /*
    Given
        https://restful-booker.herokuapp.com/booking
    When
        send request
    Then
        status code should be 200
    And
        response body should contain booking field as not null
     */

    @Test
    void getBookingIds() {

        //send the request https://restful-booker.herokuapp.com/booking
        Response response = given(spec).get("/booking");
        //response.prettyPrint();

        //do assertion
        response
                .then()
                .statusCode(200)
                .body("[0].bookingid", Matchers.notNullValue()
                );
    }
}
