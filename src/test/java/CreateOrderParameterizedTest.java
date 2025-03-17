import Order.Order;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static Order.Order.sendPostRequestCreateOrder;

/*
Проверь, что когда создаёшь заказ:
можно указать один из цветов — BLACK или GREY;
можно указать оба цвета;
тело ответа содержит track.
Чтобы протестировать создание заказа, нужно использовать параметризацию.*/

@RunWith(Parameterized.class)
public class CreateOrderParameterizedTest {

    private final List<String> color;

    public CreateOrderParameterizedTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getParameters() {
        return new Object[][] {
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")},
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Check creation of order (send all obligatory fields, check response body, check status code)") // имя теста
    @Description("Basic positive test for /api/v1/courier endpoint")
    public void createCourierWithAllObligatoryFieldsAndCheckBodyAndStatusCode201() {

        Order order = new Order("Daria","Yurovskaya", "Moscow, Red Square, 2",
                "Tretyakovskaya", "7 910 388 05 82", 7, "2025-03-03",
                "Позвоните за пол часа до приезда", color);
        Response response = sendPostRequestCreateOrder(order);
        HelpingMethods.compareFieldNotNull(response, "track");
        HelpingMethods.compareStatusCode(response, 201);
        HelpingMethods.printResponseBodyToConsole(response);
    }



}
