package in.reqres.specs;

import in.reqres.config.ApiConfig;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.aeonbits.owner.ConfigFactory;

import static in.reqres.helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;

public class Specs {
    private static ApiConfig config = ConfigFactory.create(ApiConfig.class);
    public static RequestSpecification request = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().method()
            .log().body()
            .contentType(JSON)
            .baseUri(config.BaseUrl())
            .basePath(config.BasePath());

    public static ResponseSpecification responseSpecWithStatusCode200 = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(200)
            .build();

    public static ResponseSpecification responseSpecWithStatusCode201 = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(201)
            .build();
    public static ResponseSpecification responseSpecWithStatusCode204 = new ResponseSpecBuilder()
            .expectStatusCode(204)
            .log(STATUS)
            .build();
}
