package auto.tests.login;

import auto.tests.testdata.TestData;
import base.BaseTest;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class LoginTest extends BaseTest {

    String email;
    String password = TestData.PASSWORD;

    @BeforeEach
    void generateEmail() {
        email = TestData.generateEmail();

        //Регистрация пользователя перед тестом авторизации
        RegistrationUser(email, password);

        //Разлогин после регистрации
        driver.findElement(By.cssSelector(".ico-logout")).click();
    }

    private void RegistrationUser(String email, String password) {
        driver.findElement(By.cssSelector(".ico-register")).click();
        driver.findElement(By.id("gender-male")).click();
        driver.findElement(By.id("FirstName")).sendKeys(TestData.NAME);
        driver.findElement(By.id("LastName")).sendKeys(TestData.LASTNAME);
        driver.findElement(By.id("Email")).sendKeys(email);
        driver.findElement(By.id("Password")).sendKeys(password);
        driver.findElement(By.id("ConfirmPassword")).sendKeys(password);
        driver.findElement(By.id("register-button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("result")));
        driver.findElement(By.cssSelector(".button-1.register-continue-button")).click();
    }

    @Test
    @DisplayName("1.1 Успешная авторизация")
    void SuccessAuthorization() {
        //Нажатие на логин
        WebElement login = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".ico-login")));
        login.click();

        //Ввод почты
        WebElement mail = driver.findElement(By.id("Email"));
        mail.sendKeys(email);
        Assertions.assertEquals(email, mail.getAttribute("value"));

        //Ввод пароля
        WebElement inputPassword = driver.findElement(By.id("Password"));
        inputPassword.sendKeys(TestData.PASSWORD);
        Assertions.assertEquals(TestData.PASSWORD, inputPassword.getAttribute("value"));

        //Нажатие чекбокса
        WebElement rememberMe = driver.findElement(By.id("RememberMe"));
        rememberMe.click();
        Assertions.assertTrue(rememberMe.isSelected());

        //Закончить авторизацию
        WebElement endLogin = driver.findElement(By.cssSelector(".button-1.login-button"));
        endLogin.click();
    }

    @Test
    @DisplayName("1.2 Авторизация с пустыми полями")
    void AuthorizationWithEmptyValue() {
        //Нажатие на логин
        WebElement login = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".ico-login")));
        login.click();

        //Закончить авторизацию
        WebElement endLogin = driver.findElement(By.cssSelector(".button-1.login-button"));
        endLogin.click();
        WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("validation-summary-errors")));
        Assertions.assertTrue(result.getText().contains("Login was unsuccessful"));
    }

    @Test
    @DisplayName("1.3 Авторизация с неправильным паролем")
    void AuthorizationWithWrongPassword() {
        //Нажатие на логин
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
        Assertions.assertTrue(result.getText().contains("Login was unsuccessful"));
    }
}
