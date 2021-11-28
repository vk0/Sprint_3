package com.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class BaseTest {
    List<Integer> courierIds = new ArrayList<>();
    List<String> courierLP = new ArrayList<>();

    @Before
    public void setUp() {
        // повторяющуюся для разных ручек часть URL лучше записать в переменную в методе Before
        // если в классе будет несколько тестов, указывать её придётся только один раз
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @After
    public void tearDown(){
        if (courierIds.size() != 0) {
            // удалить использованных курьеров по id
            for(int n:courierIds) {
                //System.out.println(n);
                Response response = given()
                        .header("Content-Type", "application/json")
                        //.body(payload)
                        .when()
                        .delete("/api/v1/courier/"+n);
            }

        }
        if (courierLP.size() != 0) {
            // удалить использованных курьеров по логину (после создания курьера)
            for(int i=0;i<courierLP.size();i++) {
                String login = courierLP.get(i);
                i++;
                String password = courierLP.get(i);
                i++;

                String payload = "{\"login\":\"" + login + "\","
                        + "\"password\":\"" + password + "\""
                        + "}";
                Response response = given()
                        .header("Content-Type", "application/json")
                        .body(payload)
                        .when()
                        .post("/api/v1/courier/login");

                int id = response.then().assertThat()
                        .body("id", notNullValue())
                        .and()
                        .statusCode(200)
                        .extract()
                        .path("id");

                given()
                        .header("Content-Type", "application/json")
                        //.body(payload)
                        .when()
                        .delete("/api/v1/courier/"+id);
            }
        }
    }
}
