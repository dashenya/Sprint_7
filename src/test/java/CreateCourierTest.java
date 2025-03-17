import Courier.Courier;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.HttpURLConnection;

import static Courier.Courier.sendPostRequestCourier;
import static Courier.Courier.sendPostRequestCourierLogin;
import static io.restassured.RestAssured.given;

/*
Тест 1.
- курьера можно создать
- чтобы создать курьера, нужно передать в ручку все обязательные поля
- запрос возвращает правильный код ответа
- успешный запрос возвращает ok: true
Тест 2.
если одного из полей нет, запрос возвращает ошибку (разбить на 3 теста по каждому полю)
Тест 3.
если создать пользователя с логином, который уже есть, возвращается ошибка.*/

public class CreateCourierTest {

    private String courierLogin;
    private String courierPassword;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Check creation of courier (send all obligatory fields, check response body, check status code)") // имя теста
    @Description("Basic positive test for /api/v1/courier endpoint")
    public void createCourierWithAllObligatoryFieldsAndCheckBodyAndStatusCode201() {

        courierLogin = HelpingMethods.generatingRandomStringBounded();
        courierPassword = "Qwer1234";

        Courier courier = new Courier(courierLogin, courierPassword, "Ivan" );
        Response response = sendPostRequestCourier(courier);
        HelpingMethods.compareFieldToBoolean(response, "ok", true);
        HelpingMethods.compareStatusCode(response, HttpURLConnection.HTTP_CREATED);
        HelpingMethods.printResponseBodyToConsole(response);

        //удаляем созданного курьера
        Integer id = Courier.getIdForCourier(courier);
        Courier.deleteCourier(id);
    }

    @Test
    @DisplayName("Check creation of two equal courier, check response body, check status code") // имя теста
    @Description("Negative test for /api/v1/courier endpoint")
    public void createCourierWithEqualFieldsAndCheckBodyAndStatusCode409() {

        Courier courier = new Courier(HelpingMethods.generatingRandomStringBounded(),"Qwer1234", "Ivan" );
        Response response = sendPostRequestCourier(courier);
        response = sendPostRequestCourier(courier);
        HelpingMethods.compareFieldToInt(response, "code", HttpURLConnection.HTTP_CONFLICT);
        HelpingMethods.compareFieldToString(response, "message", "Этот логин уже используется. Попробуйте другой.");
        HelpingMethods.compareStatusCode(response, HttpURLConnection.HTTP_CONFLICT);
        HelpingMethods.printResponseBodyToConsole(response);
    }

    @Test
    @DisplayName("Check creation of courier without login field, check response body, check status code")
    @Description("Negative test for /api/v1/courier endpoint")
    public void createCourierWithoutLoginFieldAndCheckBodyMessageAndStatusCode400() {
        Courier courier = new Courier();
        courier.setFirstName("Ivan");
        courier.setPassword("Qwer1234");
        Response response = sendPostRequestCourier(courier);
        HelpingMethods.compareFieldToInt(response, "code", HttpURLConnection.HTTP_BAD_REQUEST);
        HelpingMethods.compareFieldToString(response, "message", "Недостаточно данных для создания учетной записи");
        HelpingMethods.compareStatusCode(response, HttpURLConnection.HTTP_BAD_REQUEST);
        HelpingMethods.printResponseBodyToConsole(response);
    }

    @Test
    @DisplayName("Check creation of courier without password field, check response body, check status code")
    @Description("Negative test for /api/v1/courier endpoint")
    public void createCourierWithoutPasswordFieldAndCheckBodyMessageAndStatusCode400() {
        Courier courier = new Courier();
        courier.setFirstName("Ivan");
        courier.setLogin(HelpingMethods.generatingRandomStringBounded());
        Response response = sendPostRequestCourier(courier);
        HelpingMethods.compareFieldToInt(response, "code", HttpURLConnection.HTTP_BAD_REQUEST);
        HelpingMethods.compareFieldToString(response, "message", "Недостаточно данных для создания учетной записи");
        HelpingMethods.compareStatusCode(response, HttpURLConnection.HTTP_BAD_REQUEST);
        HelpingMethods.printResponseBodyToConsole(response);
    }

    @Test
    @DisplayName("Check creation of courier without firstName field, check response body, check status code")
    @Description("Negative test for /api/v1/courier endpoint")
    public void createCourierWithoutFirstNameFieldAndCheckBodyMessageAndStatusCode400() {
        Courier courier = new Courier();
        courier.setLogin(HelpingMethods.generatingRandomStringBounded());
        courier.setPassword("Qwer1234");
        Response response = sendPostRequestCourier(courier);
        HelpingMethods.compareFieldToInt(response, "code", HttpURLConnection.HTTP_BAD_REQUEST);
        HelpingMethods.compareFieldToString(response, "message", "Недостаточно данных для создания учетной записи");
        HelpingMethods.compareStatusCode(response, HttpURLConnection.HTTP_BAD_REQUEST);
        HelpingMethods.printResponseBodyToConsole(response);
    }

    @Test
    @DisplayName("Check creation of courier without any fields, check response body, check status code")
    @Description("Negative test for /api/v1/courier endpoint")
    public void createCourierWithoutAnyFieldsAndCheckBodyMessageAndStatusCode400() {
        Courier courier = new Courier();
        Response response = sendPostRequestCourier(courier);
        HelpingMethods.compareFieldToInt(response, "code", HttpURLConnection.HTTP_BAD_REQUEST);
        HelpingMethods.compareFieldToString(response, "message", "Недостаточно данных для создания учетной записи");
        HelpingMethods.compareStatusCode(response, HttpURLConnection.HTTP_BAD_REQUEST);
        HelpingMethods.printResponseBodyToConsole(response);
    }


}
