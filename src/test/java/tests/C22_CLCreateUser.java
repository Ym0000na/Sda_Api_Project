package tests;

import base_urls.CLBaseUrl;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import pojos.CLPojo;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class C22_CLCreateUser extends CLBaseUrl {

    //By using the document create a user.
    //https://documenter.getpostman.com/view/4012288/TzK2bEa8

    @Test
    void test(){

        //prepare expected data
        CLPojo payload = new CLPojo("John", "Doe", "beeebeee1234@gmail.com", "John.123");


        //send the request
        Response response = given(spec).body(payload).post("/users");
        response.prettyPrint();

        //do assertions
        response.then()
                .statusCode(201)
                .body("user.firstName", equalTo(payload.getFirstName()),
                        "user.lastName",equalTo(payload.getLastName()),
                        "user.email",equalTo(payload.getEmail())

                );



    }
}
