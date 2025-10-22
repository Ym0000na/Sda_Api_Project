package base_urls;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeMethod;

import static io.restassured.RestAssured.given;

public class BookerBaseUrl {

    protected  RequestSpecification spec;

    //This will run before each test method and initialize the spec object
    @BeforeMethod
    public void setSpec(){
        spec = new RequestSpecBuilder()
                .setBaseUri("https://restful-booker.herokuapp.com")
                .addHeader("Cookie","token="+getToken())
                .setContentType(ContentType.JSON)
                .build();
    }

    String getToken(){

        String credentials = """
                {
                    "username" : "admin",
                    "password" : "password123"
                }""";

        return given()
                .body(credentials)
                .contentType(ContentType.JSON)
                .post("https://restful-booker.herokuapp.com/auth")
                .jsonPath()
                .getString("token");

    }

}
