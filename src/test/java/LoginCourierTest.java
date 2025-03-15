import Courier.Courier;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.net.HttpURLConnection;

import static Courier.Courier.sendPostRequestCourierLogin;

/*
Тест 1
- курьер может авторизоваться
- для авторизации нужно передать все обязательные поля
- успешный запрос возвращает id
Тест 2
- система вернёт ошибку, если неправильно указать логин
- если авторизоваться под несуществующим пользователем, запрос возвращает ошибку;
Тест 3
- система вернёт ошибку, если неправильно указать пароль;
Тест 4 (разбить на 3 теста)
если какого-то поля нет, запрос возвращает ошибку;
*/

public class LoginCourierTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Check login (send all obligatory fields, check response body, check status code)") // имя теста
    @Description("Basic positive test for /api/v1/courier/login endpoint")
    public void createCourierLoginWithAllObligatoryFieldsAndCheckBodyAndStatusCode200() {

        Courier courier = new Courier("dyurovskaya_43","Qwer1234");
        Response response = sendPostRequestCourierLogin(courier);
        HelpingMethods.compareFieldToInt(response, "id", 482891);
        HelpingMethods.compareStatusCode(response, HttpURLConnection.HTTP_OK);
        HelpingMethods.printResponseBodyToConsole(response);
    }

    @Test
    @DisplayName("Check login courier with incorrect login, check response body, check status code") // имя теста
    @Description("Negative test for /api/v1/courier/login endpoint")
    public void createCourierLoginWithIncorrectLoginAndCheckBodyAndStatusCode404() {

        Courier courier = new Courier(HelpingMethods.generatingRandomStringBounded(),"Qwer1234");
        Response response = sendPostRequestCourierLogin(courier);
        HelpingMethods.compareFieldToInt(response, "code", HttpURLConnection.HTTP_NOT_FOUND);
        HelpingMethods.compareFieldToString(response, "message", "Учетная запись не найдена");
        HelpingMethods.compareStatusCode(response, HttpURLConnection.HTTP_NOT_FOUND);
        HelpingMethods.printResponseBodyToConsole(response);
    }

    @Test
    @DisplayName("Check login courier with incorrect password, check response body, check status code")
    @Description("Negative test for /api/v1/courier/login endpoint")
    public void createCourierLoginWithIncorrectPasswordAndCheckBodyMessageAndStatusCode400() {
        Courier courier = new Courier("dyurovskaya_43","Qwer1235");
        Response response = sendPostRequestCourierLogin(courier);
        HelpingMethods.compareFieldToInt(response, "code", HttpURLConnection.HTTP_NOT_FOUND);
        HelpingMethods.compareFieldToString(response, "message", "Учетная запись не найдена");
        HelpingMethods.compareStatusCode(response, HttpURLConnection.HTTP_NOT_FOUND);
        HelpingMethods.printResponseBodyToConsole(response);
    }

    @Test
    @DisplayName("Check login courier without password field, check response body, check status code")
    @Description("Negative test for /api/v1/courier/login endpoint")
    public void createCourierLoginWithoutPasswordFieldAndCheckBodyMessageAndStatusCode404() {
        Courier courier = new Courier();
        courier.setLogin("dyurovskaya_43");
        Response response = sendPostRequestCourierLogin(courier);
        HelpingMethods.compareFieldToInt(response, "code", HttpURLConnection.HTTP_BAD_REQUEST);
        HelpingMethods.compareFieldToString(response, "message", "Недостаточно данных для входа");
        HelpingMethods.compareStatusCode(response, HttpURLConnection.HTTP_BAD_REQUEST);
        HelpingMethods.printResponseBodyToConsole(response);
    }

    @Test
    @DisplayName("Check login courier without login field, check response body, check status code")
    @Description("Negative test for /api/v1/courier/login endpoint")
    public void createCourierLoginWithoutLoginFieldAndCheckBodyMessageAndStatusCode404() {
        Courier courier = new Courier();
        courier.setPassword("Qwer1234");
        Response response = sendPostRequestCourierLogin(courier);
        HelpingMethods.compareFieldToInt(response, "code", HttpURLConnection.HTTP_BAD_REQUEST);
        HelpingMethods.compareFieldToString(response, "message", "Недостаточно данных для входа");
        HelpingMethods.compareStatusCode(response, HttpURLConnection.HTTP_BAD_REQUEST);
        HelpingMethods.printResponseBodyToConsole(response);
    }

    @Test
    @DisplayName("Check login courier with empty request, check response body, check status code")
    @Description("Negative test for /api/v1/courier/login endpoint")
    public void createCourierLoginWithEmptyRequestAndCheckBodyMessageAndStatusCode404() {
        Courier courier = new Courier();
        Response response = sendPostRequestCourierLogin(courier);
        HelpingMethods.compareFieldToInt(response, "code", HttpURLConnection.HTTP_BAD_REQUEST);
        HelpingMethods.compareFieldToString(response, "message", "Недостаточно данных для входа");
        HelpingMethods.compareStatusCode(response, HttpURLConnection.HTTP_BAD_REQUEST);
        HelpingMethods.printResponseBodyToConsole(response);
    }






}
