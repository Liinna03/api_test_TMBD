package requests;

import io.restassured.response.Response;
import org.testng.Assert;
import utils.SetProperties;

import static io.restassured.RestAssured.given;

public class GetRequest extends SetProperties {

    private String token_path = "/authentication/token/new";
    private String createList01 = "/list/";

    public GetRequest(){
        super();
    }

    public String generateToken(){
        Response response = given()
                .queryParam("api_key", getApi_key())
                .when()
                .get(getBase_url() + token_path)
                .then()
                .statusCode(200)
                .extract()
                .response();
        Assert.assertEquals("true", response.jsonPath().getString("success"));
        return response.jsonPath().getString("request_token");
    }

    public void detailsList(String list_id){
        Response response = given()
                .queryParam("api_key", getApi_key())
                .when()
                .get(getBase_url() + createList01 + list_id )
                .then()
                .statusCode(200)
                .extract()
                .response();
        Assert.assertEquals(list_id, response.jsonPath().getString("id"));
    }
}
