package com.example;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
public class NewCourierTest extends BaseTest {

    @Test
    public void canCreateUniqueCourier201Test(){
        ArrayList<String> userArr = new ScooterRegisterCourier().registerNewCourierAndReturnLoginPassword();
        Assert.assertNotNull(userArr);
        //System.out.println("login = " + userArr.get(0) + " password = " + userArr.get(1) + " UserName = " + userArr.get(2));
        courierLP.add(userArr.get(0));
        courierLP.add(userArr.get(1));
    }
    @Test
    public void canNotCreateNonUniqueLoginCourier409СonflictTest(){
        ArrayList<String> userArr = new ScooterRegisterCourier().registerNewCourierAndReturnLoginPassword();
        Assert.assertNotNull(userArr);
        courierLP.add(userArr.get(0));
        courierLP.add(userArr.get(1));

        String registerRequestBody = "{\"login\":\"" + userArr.get(0) + "\","
                + "\"password\":\"" + userArr.get(1) + "\","
                + "\"firstName\":\"" + userArr.get(1) + "\"}";

        // отправляем запрос на регистрацию курьера и сохраняем ответ в переменную response класса Response
        given()
                .header("Content-type", "application/json")
                .and()
                .body(registerRequestBody)
                .when()
                .post("https://qa-scooter.praktikum-services.ru/api/v1/courier")
                .then().assertThat()
                .statusCode(409);
    }

}
