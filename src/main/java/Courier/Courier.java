package Courier;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Courier {
    private String login;
    private String password;
    private String firstName;

    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public Courier(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Courier() {}


    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Step("Send POST request to /api/v1/courier/login")
    public static Response sendPostRequestCourierLogin(Courier courier){
        Response response = given().log().all()
                .contentType(ContentType.JSON)
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier/login");
        return response;
    }

    @Step("Send POST request to /api/v1/courier")
    public static Response sendPostRequestCourier(Courier courier){
        Response response = given().log().all()
                .contentType(ContentType.JSON)
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");
        return response;
    }

    public static void deleteCourier(Integer id){
        Response response = given().log().params()
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .when()
                .delete("/api/v1/courier/{id}");
        System.out.println(response.getBody());
    }

    public static Integer getIdForCourier(Courier courier) {
        Response response = sendPostRequestCourierLogin(courier);

        JsonPath jsonPathEvaluator = response.jsonPath();
        int id = jsonPathEvaluator.get("id");
        System.out.println(id);
        return id;

    }
}
