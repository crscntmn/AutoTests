package auto.tests.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class RegistrationPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    //Локаторы
    private By registerButton = By.cssSelector(".ico-register");
    private By gender = By.id("gender-male");
    private By firstNameField = By.id("FirstName");
    private By lastNameField = By.id("LastName");
    private By emailField = By.id("Email");
    private By passwordField = By.id("Password");
    private By confirmPasswordField = By.id("ConfirmPassword");
    private By endRegisterButton = By.id("register-button");
    private By continueButton = By.xpath("//input[@class='button-1 register-continue-button']");
    private By successMessage = By.className("result");
    private By errorMessage = By.className("field-validation-error");

    public void openRegistration() {
        wait.until(ExpectedConditions.elementToBeClickable(registerButton)).click();
    }

    public void registerUser(String firstName, String lastName, String email, String password) {
        wait.until(ExpectedConditions.elementToBeClickable(gender)).click();
        driver.findElement(firstNameField).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(confirmPasswordField).sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(endRegisterButton)).click();
    }

    public void clickContinue() {
        wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();
    }

    public boolean isRegistrationSuccessful() {
        String text = wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage)).getText();
        return text.contains("Your registration completed");
    }

    public int getErrorCount() {
        List<WebElement> errors = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(errorMessage));
        return errors.size();
    }
}
