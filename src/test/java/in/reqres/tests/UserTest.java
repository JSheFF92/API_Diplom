package in.reqres.tests;


import in.reqres.models.*;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static in.reqres.specs.Specs.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {

    @Test
    @Owner("Евгений Шевчук")
    @DisplayName("Проверка имени фамилии и айди пользователя")
    void getFirstAndLastNameAndIdTest() {
        DataModel data = step("Получаем массив пользователя", () ->
                given(request)
                        .get("/users/2")
                        .then()
                        .spec(responseSpecWithStatusCode200)
                        .extract().as(DataModel.class));

        step("Проверяем Id, First Name, Last Name", () -> {
            assertThat(2).isEqualTo(data.getUser().getId());
            assertThat("Janet").isEqualTo(data.getUser().getFirstName());
            assertThat("Weaver").isEqualTo(data.getUser().getLastName());
        });
    }

    @Test
    @Owner("Евгений Шевчук")
    @DisplayName("Проверка данных пользователя в общем списке пользователей")
    void listUsersTest() {
        GetListUsersModel responseUsers = step("Получаем массив пользователей", () ->
                given(request)
                        .get("/users?page=2")
                        .then()
                        .spec(responseSpecWithStatusCode200)
                        .extract().as(GetListUsersModel.class));

        step("Проверяем данные в массиве", () -> {
            List<ListUsersDataResponseModel> data = responseUsers.getData();
            assertThat("Byron").isEqualTo(data.get(3).getFirstName());
            assertThat("Fields").isEqualTo(data.get(3).getLastName());
            assertThat(9).isEqualTo(responseUsers.getData().get(2).getId());
            assertThat("To keep ReqRes free, contributions towards server costs are appreciated!")
                    .isEqualTo(responseUsers.getSupport().getText());
            assertThat(6).isEqualTo(responseUsers.getPerPage());
        });

    }

    @Test
    @Owner("Евгений Шевчук")
    @DisplayName("Создание нового пользователя")
    void createUserTest() {
        BodyUserModel body = new BodyUserModel();
        body.setName("Jenya");
        body.setJob("QA");

        CreateResponseModel responseNewUser = step("Создаем пользователя с заданными Name и Job", () ->
                given(request)
                        .body(body)
                        .when()
                        .post("/users")
                        .then()
                        .spec(responseSpecWithStatusCode201)
                        .extract().as(CreateResponseModel.class));

        step("Проверяем созданного пользователя с заданными параметрами", () -> {
            assertThat("Jenya").isEqualTo(responseNewUser.getName());
            assertThat("QA").isEqualTo(responseNewUser.getJob());
            assertThat(responseNewUser.getId()).isNotNull();
            assertThat(responseNewUser.getCreatedAt()).isNotNull();
        });
    }

    @Test
    @Owner("Евгений Шевчук")
    @DisplayName("Обновление данных пользователя")
    void putUserTest() {
        BodyUserModel updateBody = new BodyUserModel();
        updateBody.setName("SuperJenya");
        updateBody.setJob("MEGAULTRAQA");
        PutResponseUserModel responseUpdateUser = step("Обновляем Name и Job пользователю", () ->
                given(request)
                        .body(updateBody)
                        .put("/users/2/")
                        .then()
                        .spec(responseSpecWithStatusCode200)
                        .extract().as(PutResponseUserModel.class));

        step("Проверяем изменения", () -> {
            assertThat("SuperJenya").isEqualTo(responseUpdateUser.getName());
            assertThat("MEGAULTRAQA").isEqualTo(responseUpdateUser.getJob());
            assertThat(responseUpdateUser.getUpdatedAt()).isNotNull();
        });
    }

    @Test
    @Owner("Евгений Шевчук")
    @DisplayName("Удаление пользователя")
    void deleteUserTest() {
        step("Проверяем статус удаления пользователя", () -> {
                given(request)
                        .delete("/users/2")
                        .then()
                        .log().body()
                        .spec(responseSpecWithStatusCode204);
        });
    }
}