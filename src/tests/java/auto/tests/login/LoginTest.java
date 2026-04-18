package auto.tests.login;

import auto.tests.pages.BasePage;
import auto.tests.pages.LoginPage;
import auto.tests.pages.RegistrationPage;
import auto.tests.testdata.TestData;
import base.BaseTest;
import org.junit.jupiter.api.*;


public class LoginTest extends BaseTest {
    LoginPage loginPage;
    RegistrationPage registrationPage;
    String email;

    @BeforeEach
    void setUp() {
        loginPage = new LoginPage(driver);
        email = TestData.generateEmail();
        registrationPage = new RegistrationPage(driver);
    }

    @Test
    @DisplayName("1.1 Успешная авторизация")
    void SuccessAuthorization() {
        //Регистрация пользователя и выход
        String email = registerNewUser();
        //1. Нажатие на логин
        loginPage.openAuthorization();
        //2. Процесс авторизации (Ввод email и пароль)
        loginPage.loginUser(email, TestData.PASSWORD);
        //3. Проверка, что авторизация прошла успешно
        Assertions.assertEquals(email, loginPage.isAuthorizationSuccessful());
    }

    @Test
    @DisplayName("1.2 Авторизация с пустыми полями")
    void AuthorizationWithEmptyValue() {
        //1. Нажатие на логин
        loginPage.openAuthorization();
        //2. Нажатие кнопки Log in
        loginPage.clickEndLoginButton();
        Assertions.assertTrue(loginPage.isAuthorizationUnsuccessful());
    }

    @Test
    @DisplayName("1.3 Авторизация с неправильным паролем")
    void AuthorizationWithWrongPassword() {
        //Регистрация пользователя и выход
        String email = registerNewUser();
        //1. Нажатие на логин
        loginPage.openAuthorization();
        //2. Процесс авторизации (Ввод email и пароль)
        loginPage.loginUser(email, TestData.WRONG_PASSWORD);
        //3. Проверка, что авторизация прошла успешно
        Assertions.assertTrue(loginPage.isAuthorizationUnsuccessful());
    }
}
