package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class C04_HamcrestMatchersBasics {

    @Test
    void hamcrestMatchersBasicsTest(){
        Response response = RestAssured.get("https://restful-booker.herokuapp.com/booking/11");
        //response.prettyPrint();

        response
                .then()// we use this for assertion, all the methods after his will return validation response and we can do method chaining
                .statusCode(200)
                .statusLine(equalTo("HTTP/1.1 200 OK")) /*OR*/.statusLine("HTTP/1.1 200 OK")
                .contentType(containsString("application/json"))/*OR*/.contentType(ContentType.JSON)
                .header("Server", equalTo("Heroku"))
                .header("Content-length", Matchers.notNullValue())
                .header("Via", Matchers.not(emptyString()))
                .time(lessThan(3000L))//L means long, if not used it will be considered as int
                .body("firstname", not(emptyString())) // bcs we don't know the exact name
                .body("",allOf( //allOf means AND operator, anyOf means OR operator
                        hasKey("firstname"),
                        hasKey("lastname"),
                        hasKey("totalprice"),
                        hasKey("depositpaid"),
                        hasKey("bookingdates"),
                        hasKey("additionalneeds"),
                        not(hasKey("password")) //it doesn't have password key (negative assertion)
                ))
                .body("totalprice", greaterThan(100)) // totalprice > 100
                .body("bookingdates", hasKey("checkin")) // bookingdates has checkin key
                .body("bookingdates", hasKey("checkout")) // bookingdates has checkout key

                ;

    }
}
