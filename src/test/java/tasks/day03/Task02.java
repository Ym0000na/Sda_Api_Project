package tasks.day03;

import base_urls.SwaggerBaseUrl;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojos.ToDoPojo;
import pojos.pet_store.PetPojo;
import utilities.ObjectMapperUtils;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Task02 extends SwaggerBaseUrl {

    /*
    Reference the API documentation at https://petstore.swagger.io/
    Create a POJO class representing a Pet object with properties like:
    id
    name
    category
    photoUrls
    status
    tags
    Send a POST request to the create pet endpoint with your POJO as the body
    Assert that the response status code is successful (200 or 201)
    Assert that the returned pet object contains the data you sent
     */

    @Test
    void test(){

        String jsonStr = """
                {
                  "id": 1,
                  "category": {
                    "id": 1,
                    "name": "bibi"
                  },
                  "name": "orange cat",
                  "photoUrls": [
                    "https://www.alleycat.org/wp-content/uploads/2019/03/FELV-cat.jpg"
                  ],
                  "tags": [
                    {
                      "id": 1,
                      "name": "bibi"
                    }
                  ],
                  "status": "available"
                }
                """;

        //prepare payload using pojo payload
        PetPojo payload = ObjectMapperUtils.convertJasonToJava(jsonStr, PetPojo.class);
        System.out.println("payload = " + payload);

        //send request
        Response response = given(spec).body(payload).post("/v2/pet");
        response.prettyPrint();

        //do assertions
        response
                .then()
                .statusCode(200)
                .body("id", equalTo(payload.getId()),
                        "category.name", equalTo(payload.getCategory().getName()),
                        "name", equalTo(payload.getName()),
                        "photoUrls", equalTo(payload.getPhotoUrls()),
                        "status", equalTo(payload.getStatus())
                );

        //OR - and use a million assertEquals for assertions
        PetPojo actualData = response.as(PetPojo.class);
        Assert.assertEquals(payload.getCategory(),actualData.getCategory());
        Assert.assertEquals(payload.getName(),actualData.getName());
        Assert.assertEquals(payload.getPhotoUrls().getFirst(),actualData.getPhotoUrls().getFirst());
        Assert.assertEquals(payload.getPhotoUrls().get(1),actualData.getPhotoUrls().get(1));
        Assert.assertEquals(payload.getStatus(),actualData.getStatus());
        Assert.assertEquals(payload.getId(),actualData.getId());
    }
}
