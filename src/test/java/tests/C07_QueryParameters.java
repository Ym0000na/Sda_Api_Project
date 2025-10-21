package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class C07_QueryParameters {

    /*
        Given https://restful-booker.herokuapp.com/booking
        When User sends get request to the URL
        Then Status code is 200
        And Among the data there should be someone whose firstname is "Jane" and lastname is "Doe"
    */

    @Test
    void queryParameterTest(){

//        Given https://restful-booker.herokuapp.com/booking
//        And Query Param firstname "Jane" and lastname "Doe"
        Response response = RestAssured.get("https://restful-booker.herokuapp.com/booking?firstname=Jane&lastname=Doe");
        response.prettyPrint();

//        When User sends get request to the URL
//        Then Status code is 200

        //HARD assertion
        response
                .then()
                .statusCode(200)
                //if u use multiple body methods for assertions, it will be hard assertion
                .body("[0]", hasKey("bookingid"))// 0 for first element, if "" means all elements
                .body("", hasSize(greaterThan(0))) // at least one data should be there
        ;
        //SOFT assertion
        response
                .then()
                .statusCode(200)
                //if u use one body methods for multiple assertions, it will be soft assertion
                .body("[0]", hasKey("bookingid"),
                        "", hasSize(greaterThan(0)))// 0 for first element, if "" means all elements// at least one data should be there
        ;

//        And Among the data there should be someone whose firstname is "Jane" and lastname is "Doe"

    }
}
