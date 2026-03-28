package auto.tests.login;

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

    private void registerNewUser() {
        registrationPage.openRegistration();
        registrationPage.RegisterUser(TestData.NAME, TestData.LASTNAME, email, TestData.PASSWORD, TestData.PASSWORD);
        registrationPage.clickContinue();
        loginPage.logout();
    }

    @Test
    @DisplayName("1.1 Успешная авторизация")
    void SuccessAuthorization() {
        //Регистрация пользователя и выход
        registerNewUser();
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
/*        //Нажатие на логин
        WebElement login = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".ico-login")));
        login.click();

        //Ввод почты
        WebElement mail = driver.findElement(By.id("Email"));
        mail.sendKeys(email);
        Assertions.assertEquals(email, mail.getAttribute("value"));

        //Ввод пароля
        WebElement inputPassword = driver.findElement(By.id("Password"));
        inputPassword.sendKeys(TestData.WRONG_PASSWORD);
        Assertions.assertEquals(TestData.WRONG_PASSWORD, inputPassword.getAttribute("value"));

        //Закончить авторизацию
        WebElement endLogin = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".button-1.login-button")));
        endLogin.click();
        WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("validation-summary-errors")));
        Assertions.assertTrue(result.getText().contains("Login was unsuccessful"));*/
    }
}
