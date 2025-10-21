package tasks.day02;

import base_urls.SwaggerBaseUrl;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import pojos.PetStoreUserPojo;
import pojos.ReqresPojo;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

public class T02 extends SwaggerBaseUrl {

    /*
    Task: Write an automation test that creates a 'user' using the
    Petstore API at https://petstore.swagger.io
     Requirements:
     1. Review the Pet store API documentation
     2. Identify the endpoint for creating users (/user)
     3. Create User POJO with all required fields
     4. Implement POST request to create user
     5. Validate successful creation with assertions
 */

    @Test
    void test(){

        //prepare expected data
        PetStoreUserPojo payload = new PetStoreUserPojo("43","bobRoss", "Bob", "Ross", "bob.ross123@gmail.com", "bob.ross123", "1234567899", 0);

        //send post request
        Response response = given(spec).body(payload).post("/v2/user");
        response.prettyPrint();

        //do assertion
        PetStoreUserPojo actualData = response.as(PetStoreUserPojo.class);

        assertEquals(response.statusCode(), 200);

        response.then()
                .body("message", equalTo(payload.getId()));
    }
}
