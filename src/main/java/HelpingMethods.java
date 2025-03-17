import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;

public class HelpingMethods {

    public static String generatingRandomStringBounded() {

        int length = 20;
        boolean useLetters = true;
        boolean useNumbers = false;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
        return generatedString;

    }

    @Step("Compare statusCode to something")
    public static void compareStatusCode(Response response, int statusCode){
        response.then().assertThat().statusCode(statusCode);
    }

    @Step("Compare field to boolean")
    public static void compareFieldToBoolean(Response response, String field, boolean ok){
        response.then().assertThat().body(field, Matchers.comparesEqualTo(ok));
    }
    @Step("Compare field not null")
    public static void compareFieldNotNull(Response response, String field){
        response.then().assertThat().body(field, Matchers.notNullValue());
    }

    @Step("Compare field to string")
    public static void compareFieldToString(Response response, String field, String string){
        response.then().assertThat().body(field, Matchers.comparesEqualTo(string));
    }

    @Step("Compare field to int")
    public static void compareFieldToInt(Response response, String field, Integer number){
        response.then().assertThat().body(field, Matchers.comparesEqualTo(number));
    }

    // метод для шага "Вывести тело ответа в консоль":
    @Step("Print response body to console")
    public static void printResponseBodyToConsole(Response response){
        System.out.println(response.body().asString());
    }

}
