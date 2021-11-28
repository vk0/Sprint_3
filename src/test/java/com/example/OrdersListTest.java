package com.example;

import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class OrdersListTest extends BaseTest{
    //Проверь, что в тело ответа возвращается список заказов.

   @Test
    public void getOrdersListTets() {
            given()
                    .header("Content-Type", "application/json")
                    .when()
                    .get("/api/v1/orders")
                    .then().assertThat()
                    .statusCode(200)
                    .and()
                    .body("orders", notNullValue());
    }
}
