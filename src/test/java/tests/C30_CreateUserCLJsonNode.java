package tests;

import base_urls.CLBaseUrl;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import pojos.CLPojo;
import utilities.ObjectMapperUtils;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class C30_CreateUserCLJsonNode extends CLBaseUrl {

    //By using the document create a user.
    //https://documenter.getpostman.com/view/4012288/TzK2bEa8

    @Test
    void test(){

        //prepare expected data
        JsonNode payload = ObjectMapperUtils.getJsonNode("cl_user");
        ObjectMapperUtils.updateJsonNode(payload, "email", Faker.instance().internet().emailAddress());

        //send the request
        Response response = given(spec).body(payload).post("/users");
        response.prettyPrint();

        //do assertions
        response.then()
                .statusCode(201)
                .body("user.firstName", equalTo(payload.get("firstName").textValue()),
                        "user.lastName",equalTo(payload.get("lastName").textValue()),
                        "user.email",equalTo(payload.get("email").textValue())

                );



    }
}
