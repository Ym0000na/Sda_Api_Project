package tasks.day02;

import base_urls.FakeRestApiBaseUrl;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import pojos.FakeActivityPojo;
import pojos.PetStoreUserPojo;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class T03 extends FakeRestApiBaseUrl{

    /*
    Task: Write code that performs all CRUD operations on "activities"
    using the Fake REST API at https://fakerestapi.azurewebsites.net
     Requirements:
     1. Use POJO classes for all operations
     2. Implement CREATE (POST) - Add new activity
     3. Implement READ (GET) - Retrieve activity details
     4. Implement UPDATE (PUT) - Modify existing activity
     5. Implement DELETE - Remove activity
     6. Add appropriate assertions for each operation
 */

    @Test
    void test(){

        Integer id =21;
        //prepare expected data
        FakeActivityPojo postPayload = new FakeActivityPojo(id,"Activity 21", "2025-10-22T17:44:26.6228135+00:00", false);
        FakeActivityPojo putPayload = new FakeActivityPojo(id,"Activity 21", "2025-10-22T17:44:26.6228135+00:00", true);

        //send post request
        Response postResponse = given(spec).body(postPayload).post("/api/v1/Activities");
        postResponse.prettyPrint();

        postResponse
                .then()
                .statusCode(200)
                .body("id", equalTo(postPayload.getId()),
                        "title", equalTo(postPayload.getTitle()),
                        "dueDate", equalTo(postPayload.getDueDate()),
                        "completed", equalTo(postPayload.isCompleted())
                );


        Response getResponse = given(spec).get("/api/v1/Activities/"+id);
        getResponse.prettyPrint();

        getResponse
                .then()
                .statusCode(200)
                .body("id", equalTo(postPayload.getId()),
                        "title", equalTo(postPayload.getTitle()),
                        "completed", equalTo(postPayload.isCompleted())
                );

        Response putResponse = given(spec).body(putPayload).put("/api/v1/Activities/"+id);
        putResponse.prettyPrint();

        putResponse
                .then()
                .statusCode(200)
                .body("id", equalTo(putPayload.getId()),
                        "title", equalTo(putPayload.getTitle()),
                        "dueDate", equalTo(putPayload.getDueDate()),
                        "completed", equalTo(putPayload.isCompleted())
                );


        Response deleteResponse = given(spec).delete("/api/v1/Activities/"+id);
        deleteResponse.prettyPrint();

        deleteResponse
                .then()
                .statusCode(200);



    }
}
