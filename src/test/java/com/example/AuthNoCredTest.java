package com.example;

import io.qameta.allure.Step;
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
                {makeJSON("","password", "rand_Login_26_11_2021")},
                {makeJSON("","login", "rand_Login_26_11_2021")},
                {makeJSON("")},
        };
    }

    @Step("Send POST request to /api/v1/courier/login")
    public Response sendPostRequest(){
        Response response = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .when()
                .post("/api/v1/courier/login");
        return response;
    }
    @Step("verify Status Code")
    public void verifyStatusCode(Response response, int code){
        response.then().assertThat().statusCode(code);
    }

    @Test
    public void errorAuthIfNoCred400BadRequestTest(){
        Response response = sendPostRequest();
        verifyStatusCode(response, 400);
    }

}
