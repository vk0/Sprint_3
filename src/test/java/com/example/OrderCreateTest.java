package com.example;

import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreateTest extends BaseTest{

    private final String payload;

    public OrderCreateTest(String checkedText) {
        this.payload = checkedText;
    }

    @Parameterized.Parameters
    public static Object[] getPayload2() {
        return new Object[][]{
                {makeJSON("BLACK,GRAY","firstName","Naruto","lastName","Uchiha","address","Konoha, 142 apt.","metroStation","4","phone","+7 800 355 35 35","rentTime","5","deliveryDate","2020-06-06","comment","Saske, come back to Konoha")},
                {makeJSON("BLACK","firstName","Naruto","lastName","Uchiha","address","Konoha, 142 apt.","metroStation","4","phone","+7 800 355 35 35","rentTime","5","deliveryDate","2020-06-06","comment","Saske, come back to Konoha")},
                {makeJSON("GRAY","firstName","Naruto","lastName","Uchiha","address","Konoha, 142 apt.","metroStation","4","phone","+7 800 355 35 35","rentTime","5","deliveryDate","2020-06-06","comment","Saske, come back to Konoha")},
                {makeJSON("","firstName","Naruto","lastName","Uchiha","address","Konoha, 142 apt.","metroStation","4","phone","+7 800 355 35 35","rentTime","5","deliveryDate","2020-06-06","comment","Saske, come back to Konoha")},
        };
    }

    @Test
    public void createOrderByDifferentOptionsTest(){
        Response response = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .when()
                .post("/api/v1/orders");

        int id = response.then().assertThat()
                .body("track", notNullValue())
                .and()
                .statusCode(201)
                .extract()
                .path("track");
    }
}
