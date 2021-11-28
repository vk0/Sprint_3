package com.example;

import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static io.restassured.RestAssured.given;

@RunWith(Parameterized.class)
public class AuthNoCredTest extends BaseTest{

    private final String payload;
    public AuthNoCredTest(String checkedText) {
        this.payload = checkedText;
    }

    @Parameterized.Parameters
    public static Object[] getPayload() {
        return new Object[][]{
                {"{" + "\"password\":\"" + "rand_Pass_26_11_2021" + "\"" + "}"},
                {"{ \"login\":\"" + "rand_Login_26_11_2021" + "\""  + "}"},
                {"{}"}
        };
    }

    @Test
    public void errorAuthIfNoCred400BadRequestTest(){

        Response response = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .when()
                .post("/api/v1/courier/login");

        response.then().assertThat()
                .statusCode(400);
    }
}
