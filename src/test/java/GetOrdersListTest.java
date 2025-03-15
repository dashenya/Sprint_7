import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.HttpURLConnection;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

/*
Проверь, что в тело ответа возвращается список заказов.
(Т.к нет доп требований по проверкам, то достаточно выполнить тест без параметров)
 Известно, что на первой странице 30 элементов order
 В каждом order 15 полей*/

public class GetOrdersListTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Check orders list (send get witout parameters, check response body, check status code)") // имя теста
    @Description("Basic positive test for /api/v1/orders endpoint")
    public void checkBodySizeOrderArray30AndSizeOrderItemIs15AndStatusCode200() {

        Response response = given()
                .contentType(ContentType.JSON)
                .and()
                .when()
                .get("/api/v1/orders");
        response.then().assertThat().body("orders", notNullValue())
                .and().statusCode(HttpURLConnection.HTTP_OK);

        JsonPath jsonPathEvaluator = response.jsonPath();
        int sizeOrderItem = jsonPathEvaluator.get("orders[0].size()");
        Integer sizeOrderArray = jsonPathEvaluator.get("orders.size()");

        MatcherAssert.assertThat(sizeOrderArray, equalTo(30));
        MatcherAssert.assertThat(sizeOrderItem, equalTo(15));

    }


}
