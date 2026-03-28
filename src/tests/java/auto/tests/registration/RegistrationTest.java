package auto.tests.registration;

import auto.tests.pages.RegistrationPage;
import auto.tests.testdata.TestData;
import base.BaseTest;
import org.junit.jupiter.api.*;


public class RegistrationTest extends BaseTest {
    String EMAIL;
    String WRONG_EMAIL;
    RegistrationPage registrationPage;

    @BeforeEach
    void setUp() {
        EMAIL = TestData.generateEmail();
        WRONG_EMAIL = TestData.generateWrongEmail();
        registrationPage = new RegistrationPage(driver);
    }

    @Test
    @DisplayName("1.1 Успешная регистрация")
    void SuccessRegistration() {
        //1. Клик по кнопке регистрации
        registrationPage.openRegistration();
        //2. Регистрация пользователя
        registrationPage.registerUser(TestData.NAME, TestData.LASTNAME, EMAIL, TestData.PASSWORD, TestData.PASSWORD);
        //Ожидаемое поведение: успешная регистрация
        Assertions.assertTrue(registrationPage.isRegistrationSuccessful());
        //3. Клик на "Продолжить"
        registrationPage.clickContinue();
    }

    @Test
    @DisplayName("1.2 Регистрация с пустыми полями")
    void RegistrationWithEmptyValue() {
        //1. Клик по кнопке регистрации
        registrationPage.openRegistration();
        //2. Клик по регистрации
        registrationPage.endRegistration();
        //Ожидаемое поведение: ошибка регистрации
        Assertions.assertTrue(registrationPage.getErrorCount() >= 5);
    }

    @Test
    @DisplayName("1.3 Регистрация с несовпадающими паролями")
    void RegistrationWithMismatchPasswords() {
        //1. Клик по кнопке регистрации
        registrationPage.openRegistration();
        //2. Регистрация пользователя
        registrationPage.registerUser(TestData.NAME, TestData.LASTNAME, EMAIL, TestData.PASSWORD, TestData.WRONG_PASSWORD);
        //Ожидаемое поведение: ошибка регистрации
        Assertions.assertTrue(registrationPage.isPasswordDoNotMatch());
    }

    @Test
    @DisplayName("1.4 Регистрация с некорректным email")
    void RegistrationWithIncorrectEmail() {
        //1. Клик по кнопке регистрации
        registrationPage.openRegistration();
        //2. Регистрация пользователя
        registrationPage.registerUser(TestData.NAME, TestData.LASTNAME, WRONG_EMAIL, TestData.PASSWORD, TestData.PASSWORD);
        //Ожидаемое поведение: ошибка регистрации
        Assertions.assertTrue(registrationPage.isWrongEmail());
    }
}

