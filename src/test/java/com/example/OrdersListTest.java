package com.example;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class OrdersListTest extends BaseTest{
    //Проверь, что в тело ответа возвращается список заказов.

    @Step("Send POST request to /api/v1/orders")
    public Response sendPostRequest(){
        Response response = given()
                .header("Content-Type", "application/json")
                .when()
                .get("/api/v1/orders");

        return response;
    }
    @Step("verify Status Code")
    public void verifyStatusCode(Response response, int code){
        response.then().assertThat()
                .statusCode(code)
                .and()
                .body("orders", notNullValue());
    }

   @Test
    public void getOrdersListTest() {
       Response response = sendPostRequest();
       verifyStatusCode(response,200);
    }
}
