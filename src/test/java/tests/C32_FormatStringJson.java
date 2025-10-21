package tests;

import base_urls.BookerBaseUrl;
import io.restassured.response.Response;
import json_data.BookerData;
import org.testng.annotations.Test;
import pojos.BookingPojo;
import pojos.BookingResponsePojo;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static utilities.ObjectMapperUtils.convertJasonToJava;

public class C32_FormatStringJson extends BookerBaseUrl {
    /*
    Given
    1) Endpoint: https://restful-booker.herokuapp.com/booking
    2) Request Body:
            {
                "firstname": "Josh",
                "lastname": "Allen",
                "totalprice": 111,
                "depositpaid": true,
                "bookingdates": {
                    "checkin": "2018-01-01",
                    "checkout": "2019-01-01"
                },
                "additionalneeds": "super bowls"
            }

    When
    I send a POST request to the above URL

    Then
    1) The status code should be 200
    2) The response body should match the following structure:
       {
           "bookingid": 2243,
           "booking":
                {
                    "firstname": "Josh",
                    "lastname": "Allen",
                    "totalprice": 111,
                    "depositpaid": true,
                    "bookingdates": {
                        "checkin": "2018-01-01",
                        "checkout": "2019-01-01"
                    },
                    "additionalneeds": "super bowls"
                }
       }
*/
    @Test
    void objectMapperNestedPojoTest(){
        
        //prepare the payload
        String formattedBooking = String.format(BookerData.BOOKING_JSON_DYNAMIC,"Tom","Star", "Tea");
        BookingPojo payload = convertJasonToJava(formattedBooking, BookingPojo.class);
        System.out.println("payload = " + payload);

        //send request
        Response response = given(spec).body(payload).post("/booking");
        response.prettyPrint();

        //do assertion
        BookingResponsePojo actualData = response.as(BookingResponsePojo.class);

        assertEquals(response.statusCode(), 200);
        assertEquals(actualData.getBooking().getFirstname(), payload.getFirstname());
        assertEquals(actualData.getBooking().getLastname(), payload.getLastname());
        assertEquals(actualData.getBooking().getTotalprice(), payload.getTotalprice());
        assertEquals(actualData.getBooking().getDepositpaid(), payload.getDepositpaid());
        assertEquals(actualData.getBooking().getBookingdates().getCheckin(), payload.getBookingdates().getCheckin());
        assertEquals(actualData.getBooking().getBookingdates().getCheckout(), payload.getBookingdates().getCheckout());
        assertEquals(actualData.getBooking().getAdditionalneeds(), payload.getAdditionalneeds());


    }
}
