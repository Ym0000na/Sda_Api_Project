package tasks.day04;

import base_urls.GoRestBaseUrl;
import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import utilities.ObjectMapperUtils;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Task01 extends GoRestBaseUrl {

    /*
    Get all users
    Create a user
    Get that user
    Update user
    Partial Update User
    Delete User
    Get User Negative
     */
    public static int goRestId;

    @Test
    void r01getAllUsers(){

        //send the request
        Response response = given(spec).get("/public/v2/users");
        response.prettyPrint();

        //do assertion
        response
                .then()
                .statusCode(200)
                .body("[0].id", Matchers.notNullValue()
                );
    }


    @Test
    void r02createUser(){

        // prepare the data
        JsonNode payload = ObjectMapperUtils.getJsonNode("go_rest");

        //send the request
        Response response = given(spec).body(payload).post("/public/v2/users");
        response.prettyPrint();

        //do assertion
        response
                .then()
                .statusCode(201)
                .body(
                       "name", equalTo(payload.get("name").asText()),
                       "email", equalTo(payload.get("email").asText()),
                       "gender", equalTo(payload.get("gender").asText()),
                       "status", equalTo(payload.get("status").asText())
                );

        //Saving the booking id that they created
        goRestId = response.jsonPath().getInt("id");
    }

    @Test
    void r03getUser(){

        //prepare the expected data
        JsonNode expectedData = ObjectMapperUtils.getJsonNode("go_rest");

        //send the request
        Response response = given(spec).get("/public/v2/users/"+goRestId);
        response.prettyPrint();

        //do assertion
        response
                .then()
                .statusCode(200)
                .body(
                        "id", equalTo(goRestId),
                        "name", equalTo(expectedData.get("name").asText()),
                        "email", equalTo(expectedData.get("email").asText()),
                        "gender", equalTo(expectedData.get("gender").asText()),
                        "status", equalTo(expectedData.get("status").asText())
                );
    }

    @Test
    void r04updateUser(){

        //prepare the payload
        JsonNode payload = ObjectMapperUtils.getJsonNode("go_rest");
        ObjectMapperUtils.updateJsonNode(payload, "name", "Kujo Jolyne");
        ObjectMapperUtils.updateJsonNode(payload, "email", "Jolyneee@hotmail.com");
        ObjectMapperUtils.updateJsonNode(payload, "gender", "female");
        ObjectMapperUtils.updateJsonNode(payload, "status", "active");

        //send the request
        Response response = given(spec).body(payload).put("/public/v2/users/"+goRestId);
        response.prettyPrint();

        //do assertion
        response
                .then()
                .statusCode(200)
                .body(
                        "id", equalTo(goRestId),
                        "name", equalTo(payload.get("name").asText()),
                        "email", equalTo(payload.get("email").asText()),
                        "gender", equalTo(payload.get("gender").asText()),
                        "status", equalTo(payload.get("status").asText())
                );
    }

    @Test
    void r05partialUpdate(){

        //prepare the payload
        JsonNode payload = ObjectMapperUtils.getJsonNode("go_rest_patch");

        //send the request
        Response response = given(spec).body(payload).patch("/public/v2/users/"+goRestId);
        response.prettyPrint();

        //do assertion
        response
                .then()
                .statusCode(200)
                .body(
                        "id", equalTo(goRestId),
                        "name", equalTo(payload.get("name").asText()),
                        "gender", equalTo(payload.get("gender").asText())
                );
    }

    @Test(dependsOnMethods = "r02createUser")
    void r06deleteUser(){

        //send the request
        Response response = given(spec).delete("/public/v2/users/"+goRestId);
        response.prettyPrint();

        response
                .then()
                .statusCode(204);
    }

    @Test (dependsOnMethods = "r06deleteUser")
    void r07getUserNegative(){

        //send the request
        Response response = given(spec).get("/public/v2/users/"+goRestId);
        response.prettyPrint();

        //do assertion
        response
                .then()
                .statusCode(404);



    }

}
