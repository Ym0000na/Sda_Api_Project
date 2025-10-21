package tests;

import base_urls.JPHBaseUrl;
import io.restassured.response.Response;
import json_data.JPHData;
import org.testng.annotations.Test;
import pojos.ToDoPojo;
import utilities.ObjectMapperUtils;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

public class C25_TestData extends JPHBaseUrl {
    /*
           Given
               https://jsonplaceholder.typicode.com/todos
               {
                   "userId": 55,
                   "title": "Tidy your room",
                   "completed": false
               }
           When
               I send a POST Request to the URL
           Then
               Status code is 201
           And
               Response body is like:
               {
                   "userId": 55,
                   "title": "Tidy your room",
                   "completed": false,
                   "id": 201
               }
       */
    @Test
    void objectMapperTestMap()  {

        // Prepare the payload

        Map payload = ObjectMapperUtils.convertJasonToJava(JPHData.TO_DO_JSON, Map.class);
        System.out.println("payload = " + payload);

        //send request
        Response response = given(spec).body(payload).post("/todos");
        response.prettyPrint();

        //do assertion
        Map actualData = response.as(Map.class);
        assertEquals(response.statusCode(),201);
        assertEquals(actualData.get("userId"), payload.get("userId"));
        assertEquals(actualData.get("title"), payload.get("title"));
        assertEquals(actualData.get("completed"), payload.get("completed"));

        //or
        response
                .then()
                .statusCode(201)
                .body("userId", equalTo(payload.get("userId")),
                        "title", equalTo(payload.get("title")),
                        "completed", equalTo(payload.get("completed"))
                );

    }

    @Test
    void objectMapperTestPojo()  {

        // Prepare the payload

        ToDoPojo payload = ObjectMapperUtils.convertJasonToJava(JPHData.TO_DO_JSON, ToDoPojo.class);
        System.out.println("payload = " + payload);

        //send request
        Response response = given(spec).body(payload).post("/todos");
        response.prettyPrint();

        //do assertion
        response
                .then()
                .statusCode(201)
                .body("userId", equalTo(payload.getUserId()),
                        "title", equalTo(payload.getTitle()),
                        "completed", equalTo(payload.getCompleted())
                );

    }
}
