package com.example;

import io.qameta.allure.Description;
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
    @DisplayName("Create new courier")
    @Description("This is description for new courier creation")
    public void canCreateUniqueCourier201Test(){
        ArrayList<String> userArr = new ScooterRegisterCourier().registerNewCourierAndReturnLoginPassword();
        Assert.assertNotNull(userArr);
        courierLP.add(userArr.get(0));
        courierLP.add(userArr.get(1));
    }
    @Test
    public void canNotCreateNonUniqueLoginCourier409СonflictTest(){
        ArrayList<String> userArr = new ScooterRegisterCourier().registerNewCourierAndReturnLoginPassword();
        Assert.assertNotNull(userArr);
        courierLP.add(userArr.get(0));
        courierLP.add(userArr.get(1));

        String payload = makeJSON("","login", userArr.get(0), "password", userArr.get(1), "firstName", userArr.get(1));

        given()
                .header("Content-type", "application/json")
                .and()
                .body(payload)
                .when()
                .post("https://qa-scooter.praktikum-services.ru/api/v1/courier")
                .then().assertThat()
                .statusCode(409);
    }
}
