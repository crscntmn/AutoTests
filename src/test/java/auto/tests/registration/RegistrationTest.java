package auto.tests.registration;

import auto.tests.components.HeaderComponent;
import auto.tests.pages.RegistrationPage;
import auto.tests.testdata.TestData;
import base.BaseTest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Tag;


public class RegistrationTest extends BaseTest {
    String EMAIL;
    String WRONG_EMAIL;
    String EMPTY_EMAIL;
    String EMAIL_WITH_SPACE;
    String EMAIL_WITH_CAPS;
    RegistrationPage registrationPage;
    HeaderComponent header;

    @BeforeEach
    void setUp() {
        EMAIL = TestData.generateEmail();
        WRONG_EMAIL = TestData.generateWrongEmail();
        EMPTY_EMAIL = TestData.generateEmptyEmail();
        EMAIL_WITH_SPACE = TestData.generateEmailWithSpace();
        EMAIL_WITH_CAPS = TestData.generateEmailWithCaps();
        registrationPage = new RegistrationPage(driver);
        header = new HeaderComponent(driver);
    }

    @Tag("smoke")
    @Test
    @DisplayName("1.1 Успешная регистрация")
    void SuccessRegistration() {
        //1. Клик по кнопке регистрации
        header.openRegistration();
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
        header.openRegistration();
        //2. Клик по регистрации
        registrationPage.endRegistration();
        //Ожидаемое поведение: ошибка регистрации
        Assertions.assertTrue(registrationPage.getErrorCount() >= 5);
    }

    @Tag("regression")
    @Test
    @DisplayName("1.3 Регистрация с несовпадающими паролями")
    void RegistrationWithMismatchPasswords() {
        //1. Клик по кнопке регистрации
        header.openRegistration();
        //2. Регистрация пользователя
        registrationPage.registerUser(TestData.NAME, TestData.LASTNAME, EMAIL, TestData.PASSWORD, TestData.WRONG_PASSWORD);
        //Ожидаемое поведение: ошибка регистрации
        Assertions.assertTrue(registrationPage.isPasswordDoNotMatch());
    }

    @Tag("regression")
    @Test
    @DisplayName("1.4 Регистрация с некорректным email")
    void RegistrationWithIncorrectEmail() {
        //1. Клик по кнопке регистрации
        header.openRegistration();
        //2. Регистрация пользователя
        registrationPage.registerUser(TestData.NAME, TestData.LASTNAME, WRONG_EMAIL, TestData.PASSWORD, TestData.PASSWORD);
        //Ожидаемое поведение: ошибка регистрации
        Assertions.assertTrue(registrationPage.isWrongEmail());
    }

    @Test
    @DisplayName("1.5 Регистрация без имени")
    void RegistrationWithoutName() {
        //1. Клик по кнопке регистрации
        header.openRegistration();
        //2. Регистрация пользователя
        registrationPage.registerUser(TestData.EMPTY_NAME, TestData.LASTNAME, EMAIL, TestData.PASSWORD, TestData.PASSWORD);
        //Ожидаемое поведение: ошибка регистрации
        Assertions.assertTrue(registrationPage.isEmptyName());
    }

    @Test
    @DisplayName("1.6 Регистрация без фамилии")
    void RegistrationWithoutLastName() {
        //1. Клик по кнопке регистрации
        header.openRegistration();
        //2. Регистрация пользователя
        registrationPage.registerUser(TestData.NAME, TestData.EMPTY_LASTNAME, EMAIL, TestData.PASSWORD, TestData.PASSWORD);
        //Ожидаемое поведение: ошибка регистрации
        Assertions.assertTrue(registrationPage.isEmptyLastName());
    }

    @Test
    @DisplayName("1.7 Регистрация без email")
    void RegistrationWithoutEmail() {
        //1. Клик по кнопке регистрации
        header.openRegistration();
        //2. Регистрация пользователя
        registrationPage.registerUser(TestData.NAME, TestData.LASTNAME, EMPTY_EMAIL, TestData.PASSWORD, TestData.PASSWORD);
        //Ожидаемое поведение: ошибка регистрации
        Assertions.assertTrue(registrationPage.isEmptyEmail());
    }

    @Tag("regression")
    @Test
    @DisplayName("1.8 Регистрация с паролем в 6 символов (граничное значение)")
    void RegistrationWithSixSymbols() {
        //1. Клик по кнопке регистрации
        header.openRegistration();
        //2. Регистрация пользователя
        registrationPage.registerUser(TestData.NAME, TestData.LASTNAME, EMAIL, TestData.SHORT_PASSWORD, TestData.SHORT_PASSWORD);
        //Ожидаемое поведение: успешная регистрация
        Assertions.assertTrue(registrationPage.isRegistrationSuccessful());
    }

    @Test
    @DisplayName("1.9 Регистрация с паролем в 5 символов")
    void RegistrationWithFiveSymbols() {
        //1. Клик по кнопке регистрации
        header.openRegistration();
        //2. Регистрация пользователя
        registrationPage.registerUser(TestData.NAME, TestData.LASTNAME, EMAIL, TestData.INVALIDATE_PASSWORD, TestData.INVALIDATE_PASSWORD);
        //Ожидаемое поведение: ошибка регистрации
        Assertions.assertTrue(registrationPage.isPasswordInvalidate());
    }

    @Test
    @DisplayName("1.10 Регистрация с пробелами в email")
    void RegistrationWithSpaceInEmail() {
        //1. Клик по кнопке регистрации
        header.openRegistration();
        //2. Регистрация пользователя
        registrationPage.registerUser(TestData.NAME, TestData.LASTNAME, EMAIL_WITH_SPACE, TestData.PASSWORD, TestData.PASSWORD);
        //Ожидаемое поведение: ошибка регистрации
        Assertions.assertTrue(registrationPage.isWrongEmail());
    }

    @Tag("regression")
    @Test
    @DisplayName("1.11 Регистрация с email в верхнем регистре")
    void RegistrationWithCapsInEmail() {
        //1. Клик по кнопке регистрации
        header.openRegistration();
        //2. Регистрация пользователя
        registrationPage.registerUser(TestData.NAME, TestData.LASTNAME, EMAIL_WITH_CAPS, TestData.PASSWORD, TestData.PASSWORD);
        //Ожидаемое поведение: ошибка регистрации
        Assertions.assertTrue(registrationPage.isRegistrationSuccessful());
    }
}

