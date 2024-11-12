import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class RestApiTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://reqres.in";
    }

    @Test
    public void testUsersListGet(){

        RestAssured.get("/api/users?page=2")
        .then()
            .statusCode(200)
            .body("data", not(empty())); 
    }

    @Test
    public void testUserGet(){

        RestAssured.get("/api/users/2")
        .then()
            .statusCode(200)
            .body("data.last_name", equalTo("Weaver"))
            .body("data.email", equalTo("janet.weaver@reqres.in")); 
    }

    @Test
    public void testUserNotFoundGet(){

        RestAssured.get("/api/users/23")
        .then()
            .statusCode(404);
    }

    @Test
    public void testUserPost(){

        //String userJson = "{ \"name\": \"Peter\";
        Map<String,String> userMap = new HashMap<>();
        userMap.put("name","Peter");
        userMap.put("job","Medic");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(userMap)
            .when()
                .post("/api/users")
            .then()
                .statusCode(201)
                .body("name", equalTo("Peter"))
                .body("job", equalTo("Medic"));
    }

    @Test
    public void testUserPut(){

        Map<String,String> userMap = new HashMap<>();
        userMap.put("name","Jack");
        userMap.put("job","Builder");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(userMap)
            .when()
                .put("/api/users/2")
            .then()
                .statusCode(200)
                .body("name", equalTo("Jack"))
                .body("job", equalTo("Builder"));
    }

    @Test
    public void testUserPatch(){

        Map<String,String> userJobMap = new HashMap<>();
        userJobMap.put("job","Engineer");

        RestAssured.given()
                .contentType(ContentType.JSON)
            .when()
                .body(userJobMap)
                .put("/api/users/2")
            .then()
                .statusCode(200)
                .body("job", equalTo("Engineer"));
    }
    
    @Test
    public void testUserDelete(){

        RestAssured.delete("/api/users/2")
        .then()
            .statusCode(204);
    }    
}
