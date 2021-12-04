package com.example;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.ArrayList;

import static io.restassured.RestAssured.given;

@RunWith(Parameterized.class)
public class NewCourierNotAllFieldTest extends BaseTest{

    private final String payload;

    public NewCourierNotAllFieldTest(String checkedText) {
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

    @Test
    public void needAllFieldForCreateCourier400BadRequestTest(){
        given()
                .header("Content-type", "application/json")
                .and()
                .body(payload)
                .when()
                .post("/api/v1/courier")
                .then().assertThat()
                .statusCode(400);
    }
}
