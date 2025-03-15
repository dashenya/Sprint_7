import Order.Order;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import java.net.HttpURLConnection;

/*
Проверь, что когда создаёшь заказ:
- можно совсем не указывать цвет;
тело ответа содержит track.*/

public class CreateOrderTest {

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
                "Позвоните за пол часа до приезда");
        Response response = Order.sendPostRequestCreateOrder(order);
        HelpingMethods.compareFieldNotNull(response, "track");
        HelpingMethods.compareStatusCode(response, HttpURLConnection.HTTP_CREATED);
        HelpingMethods.printResponseBodyToConsole(response);
    }










}
