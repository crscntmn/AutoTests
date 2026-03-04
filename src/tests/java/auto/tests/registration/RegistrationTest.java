package auto.tests.registration;

import auto.tests.pages.RegistrationPage;
import auto.tests.testdata.TestData;
import base.BaseTest;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;


public class RegistrationTest extends BaseTest {

    String EMAIL;
    RegistrationPage registrationPage;

    @BeforeEach
    void generateEmail() {
        EMAIL = TestData.generateEmail();
        registrationPage = new RegistrationPage(driver);
    }

    @Test
    @DisplayName("1.1 Успешная регистрация")
    void SuccessRegistration() {
        //1. Открытие страницы
        registrationPage.open();
        //2. Регистрация пользователя
        registrationPage.registerUser(TestData.NAME, TestData.LASTNAME, EMAIL, TestData.PASSWORD);
        Assertions.assertTrue(registrationPage.isRegistrationSuccessful());
        //3. Клик на "Продолжить"
        registrationPage.clickContinue();
    }

    @Test
    @DisplayName("1.2 Регистрация с пустыми полями")
    void RegistrationWithEmptyValue() {
        //1. Открытие страницы
        registrationPage.open();
        //2. Клик по регистрации
        driver.findElement(By.id("register-button")).click();
        Assertions.assertTrue(registrationPage.getErrorCount() >= 5);

    }
}

