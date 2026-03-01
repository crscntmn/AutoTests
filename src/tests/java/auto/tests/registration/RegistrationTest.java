package auto.tests.registration;
import auto.tests.testdata.TestData;
import base.BaseTest;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;


public class RegistrationTest extends BaseTest {

    String email;

    @BeforeEach
    void generateEmail(){
        email = TestData.generateEmail();
    }

    @Test
    @DisplayName("1.1 Успешная регистрация")
    void SuccessRegistration() {
        //Клик по регистрации
        WebElement startRegister = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".ico-register")));
        startRegister.click();

        //Клик по radioButton
        WebElement gender = wait.until(ExpectedConditions.elementToBeClickable(By.id("gender-male")));
        gender.click();
        Assertions.assertTrue(gender.isSelected());

        //Ввод имени
        WebElement firstName = wait.until(ExpectedConditions.elementToBeClickable(By.id("FirstName")));
        firstName.sendKeys(TestData.NAME);
        Assertions.assertEquals(TestData.NAME, firstName.getAttribute("value"));

        //Ввод фамилии
        WebElement lastName = wait.until(ExpectedConditions.elementToBeClickable(By.id("LastName")));
        lastName.sendKeys(TestData.LASTNAME);
        Assertions.assertEquals(TestData.LASTNAME, lastName.getAttribute("value"));

        //Ввод почты
        WebElement mail = wait.until(ExpectedConditions.elementToBeClickable(By.id("Email")));
        mail.sendKeys(email);
        Assertions.assertEquals(email, mail.getAttribute("value"));

        //Ввод пароля
        WebElement password = wait.until(ExpectedConditions.elementToBeClickable(By.id("Password")));
        password.sendKeys(TestData.PASSWORD);
        Assertions.assertEquals(TestData.PASSWORD, password.getAttribute("value"));

        //Подтверждение пароля
        WebElement confirmPassword = wait.until(ExpectedConditions.elementToBeClickable(By.id("ConfirmPassword")));
        confirmPassword.sendKeys(TestData.PASSWORD);
        Assertions.assertEquals(TestData.PASSWORD, confirmPassword.getAttribute("value"));

        //Регистрация
        WebElement endRegister = wait.until(ExpectedConditions.elementToBeClickable(By.id("register-button")));
        endRegister.click();
        WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("result")));
        Assertions.assertEquals("Your registration completed", result.getText());

        //Продолжить
        WebElement buttonContinue = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@class='button-1 register-continue-button']")));
        buttonContinue.click();
    }

    @Test
    @DisplayName("1.2 Регистрация с пустыми полями")
    void RegistrationWithEmptyValue() {
        //Клик по регистрации
        WebElement startRegister = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".ico-register")));
        startRegister.click();

        //Регистрация
        WebElement endRegister = driver.findElement(By.id("register-button"));
        endRegister.click();
        List<WebElement> errors = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("field-validation-error")));
        Assertions.assertTrue(errors.size() >= 5);
    }
}

