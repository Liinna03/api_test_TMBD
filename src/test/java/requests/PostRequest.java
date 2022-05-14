package requests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import utils.SetProperties;

import static io.restassured.RestAssured.given;


public class PostRequest extends SetProperties {
    JSONObject jsonObject = new JSONObject();
    GetRequest getRequest = new GetRequest();
    private String validateTokenPath = "/authentication/token/validate_with_login";
    private String createSession = "/authentication/session/new";
    private String createList = "/list";
    private String addMovie = "/add_item";
    private String clearList = "/clear";


    public PostRequest(){
        super();
    }

    public String validateToken(){
        jsonObject
                .put("username", getUsername())
                .put("password", getPassword())
                .put("request_token", getRequest.generateToken());
        Response response = given()
                .contentType(ContentType.JSON)
                .queryParam("api_key", getApi_key())
                .body(jsonObject.toString())
                .when()
                .post(getBase_url() + validateTokenPath)
                .then()
                .statusCode(200)
                .extract()
                .response();
        Assert.assertEquals("true", response.jsonPath().getString("success"));
        return response.jsonPath().getString("request_token");
    }

    public String createSession(){
        jsonObject
                .put("request_token", validateToken());
        Response response = given()
                .contentType(ContentType.JSON)
                .queryParam("api_key", getApi_key())
                .body(jsonObject.toString())
                .when()
                .post(getBase_url() + createSession)
                .then()
                .statusCode(200)
                .extract()
                .response();
        Assert.assertEquals("true", response.jsonPath().getString("success"));
        return response.jsonPath().getString("session_id");
    }

    public String createList(String name, String description, String language){
        jsonObject
                .put("name", name)
                .put("description", description)
                .put("language", language);
        Response response = given()
                .contentType(ContentType.JSON)
                .queryParam("api_key", getApi_key())
                .queryParam("session_id", createSession())
                .body(jsonObject.toString())
                .when()
                .post(getBase_url() + createList)
                .then()
                .statusCode(201)
                .extract()
                .response();
        Assert.assertEquals("The item/record was created successfully.", response.jsonPath().getString("status_message"));
        return response.jsonPath().getString("list_id");
    }
    public void addMovie(String media_id, String list_id){
        jsonObject
                .put("media_id", media_id);
        Response response = given()
                .contentType(ContentType.JSON)
                .queryParam("api_key", getApi_key())
                .queryParam("session_id",createSession())
                .body(jsonObject.toString())
                .when()
                .post(getBase_url() + createList +"/"+ list_id + addMovie)
                .then()
                .statusCode(201)
                .extract()
                .response();
        Assert.assertEquals("The item/record was updated successfully.", response.jsonPath().getString("status_message"));
    }

    public void clearList(String list_id){
        Response response = given()
                .contentType(ContentType.JSON)
                .queryParam("api_key", getApi_key())
                .queryParam("session_id",createSession())
                .queryParam("confirm", true)
                .body(jsonObject.toString())
                .when()
                .post(getBase_url() + createList + "/"+ list_id+ clearList)
                .then()
                .statusCode(201)
                .extract()
                .response();
        Assert.assertEquals("true", response.jsonPath().getString("success"));
    }
}
