package auto.tests.login;

import auto.tests.components.HeaderComponent;
import auto.tests.pages.LoginPage;
import auto.tests.pages.RegistrationPage;
import auto.tests.testdata.TestData;
import base.BaseTest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Tag;


public class LoginTest extends BaseTest {
    LoginPage loginPage;
    RegistrationPage registrationPage;
    HeaderComponent header;
    String email;
    String wrong_email;
    String empty_email;

    @BeforeEach
    void setUp() {
        loginPage = new LoginPage(driver);
        email = TestData.generateEmail();
        wrong_email = TestData.generateWrongEmail();
        empty_email = TestData.generateEmptyEmail();
        registrationPage = new RegistrationPage(driver);
        header = new HeaderComponent(driver);
    }

    private void registerNewUser() {
        header.openRegistration();
        registrationPage.registerUser(TestData.NAME, TestData.LASTNAME, email, TestData.PASSWORD, TestData.PASSWORD);
        registrationPage.clickContinue();
        header.logout();
    }

    @Tag("smoke")
    @Test
    @DisplayName("1.1 Успешная авторизация")
    void SuccessAuthorization() {
        //Регистрация пользователя и выход
        registerNewUser();
        //1. Нажатие на логин
        header.openLogin();
        //2. Процесс авторизации (Ввод email и пароль)
        loginPage.loginUser(email, TestData.PASSWORD);
        //Ожидаемое поведение: успешная авторизация
        Assertions.assertEquals(email, loginPage.isAuthorizationSuccessful());
    }

    @Test
    @DisplayName("1.2 Авторизация с пустыми полями")
    void AuthorizationWithEmptyValue() {
        //1. Нажатие на логин
        header.openLogin();
        //2. Нажатие кнопки Log in
        loginPage.clickEndLoginButton();
        Assertions.assertTrue(loginPage.isAuthorizationUnsuccessful());
    }

    @Tag("regression")
    @Test
    @DisplayName("1.3 Авторизация с неправильным паролем")
    void AuthorizationWithWrongPassword() {
        //1. Нажатие на логин
        header.openLogin();
        //2. Процесс авторизации (Ввод email и пароль)
        loginPage.loginUser(email, TestData.WRONG_PASSWORD);
        //Ожидаемое поведение: ошибка авторизации
        Assertions.assertTrue(loginPage.isAuthorizationUnsuccessful());
    }

    @Tag("regression")
    @Test
    @DisplayName("1.4 Авторизация с неправильным email")
    void AuthorizationWithWrongEmail() {
        //Регистрация пользователя и выход
        registerNewUser();
        //1. Нажатие на логин
        header.openLogin();
        //2. Процесс авторизации (Ввод email и пароль)
        loginPage.loginUser(wrong_email, TestData.PASSWORD);
        //Ожидаемое поведение: Отображение ошибки невалидного email
        Assertions.assertTrue(loginPage.isInvalidEmailErrorDisplayed());
    }

    @Test
    @DisplayName("1.5 Авторизация с пустым email")
    void AuthorizationWithEmptyEmail() {
        //1. Нажатие на логин
        header.openLogin();
        //2. Процесс авторизации (Ввод email и пароль)
        loginPage.loginUser(empty_email, TestData.PASSWORD);
        //Ожидаемое поведение: ошибка авторизации
        Assertions.assertTrue(loginPage.isAuthorizationUnsuccessful());
    }

    @Test
    @DisplayName("1.6 Авторизация с пустым паролем")
    void AuthorizationWithEmptyPassword() {
        //1. Нажатие на логин
        header.openLogin();
        //2. Процесс авторизации (Ввод email и пароль)
        loginPage.loginUser(email, TestData.EMPTY_PASSWORD);
        //Ожидаемое поведение: ошибка авторизации
        Assertions.assertTrue(loginPage.isAuthorizationUnsuccessful());
    }

    @Test
    @DisplayName("1.7 Авторизация с нажатием Forgot password, если email не существует")
    void AuthorizationWithInvalidEmailInForgotPassword() {
        //1. Нажатие на логин
        header.openLogin();
        //2. Нажатие кнопки ForgotPassword
        loginPage.clickForgotPassword();
        //3. Ввод email
        loginPage.inputEmailInForgotPassword(email);
        //4. Клик по кнопке Recover
        loginPage.clickRecoverButton();
        //Ожидаемое поведение: отображение ошибки email not found
        Assertions.assertTrue(loginPage.emailIsNotFound());
    }

    @Tag("smoke")
    @Test
    @DisplayName("1.8 Авторизация с нажатием Forgot password, если email существует")
    void AuthorizationWithValidEmailInForgotPassword() {
        //1. Регистрация нового пользователя
        registerNewUser();
        //2. Нажатие на логин
        header.openLogin();
        //3. Нажатие кнопки ForgotPassword
        loginPage.clickForgotPassword();
        //4. Ввод email
        loginPage.inputEmailInForgotPassword(email);
        //5. Клик по кнопке Recover
        loginPage.clickRecoverButton();
        //Ожидаемое поведение: Инструкция отправлена на почту
        Assertions.assertTrue(loginPage.sentInstructionInEmail());
    }

    @Test
    @DisplayName("1.9 Авторизация с неверным email и пустым паролем")
    void AuthorizationWithInvalidEmailAndEmptyPassword() {
        //1. Нажатие на логин
        header.openLogin();
        //2. Ввод email
        loginPage.loginUser(wrong_email, TestData.EMPTY_PASSWORD);
        //Ожидаемое поведение: Отображение ошибки невалидного email
        Assertions.assertTrue(loginPage.isInvalidEmailErrorDisplayed());
    }

    @Test
    @DisplayName("1.10 Проверка разлогина")
    void LogoutAfterAuthorization() {
        //Регистрация пользователя и выход
        registerNewUser();
        //Ожидаемое поведение: Отображение кнопки Login в шапке
        Assertions.assertTrue(header.isLoginButtonDisplayed());
    }
}
