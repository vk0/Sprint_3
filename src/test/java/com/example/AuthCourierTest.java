package com.example;

import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;


public class AuthCourierTest extends BaseTest{
    int id;
    String randLogin = RandomStringUtils.randomAlphabetic(10);

    @Test
    public void courierCanAuthTest(){
        ArrayList<String> userArr = new ScooterRegisterCourier().registerNewCourierAndReturnLoginPassword();
        Assert.assertNotNull(userArr);
        String payload = makeJSON("","login", userArr.get(0), "password", userArr.get(1));

        Response response = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .when()
                .post("/api/v1/courier/login");

        id = response.then().assertThat()
                .body("id", notNullValue())
                .and()
                .statusCode(200)
                .extract()
                .path("id");

        courierIds.add(id);
    }

    @Test
    public void errorAuthIfNotValidCred404NotFoundTest(){

        String payload = makeJSON("","login", randLogin, "password", randLogin);

        Response response = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .when()
                .post("/api/v1/courier/login");

        response.then().assertThat()
                .statusCode(404);
    }
}
