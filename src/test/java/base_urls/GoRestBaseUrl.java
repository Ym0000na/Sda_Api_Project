package base_urls;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeMethod;

public class GoRestBaseUrl {

    protected RequestSpecification spec;
    private String token = "65048508d295909540150ddee8b93ffcb393d73976c2ffccdd4a47a8e4194637";
    @BeforeMethod
    public void setSpec(){
        spec = new RequestSpecBuilder()
                .setBaseUri("https://gorest.co.in")
                .addHeader("Authorization","Bearer "+token)
                .setContentType(ContentType.JSON)
                .build();
    }
}
