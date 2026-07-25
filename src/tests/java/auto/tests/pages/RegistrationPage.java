package auto.tests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class RegistrationPage extends BasePage {

    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    //Локаторы
    private final By registerButton = By.cssSelector(".ico-register");
    private final By gender = By.id("gender-male");
    private final By firstNameField = By.id("FirstName");
    private final By lastNameField = By.id("LastName");
    private final By emailField = By.id("Email");
    private final By passwordField = By.id("Password");
    private final By confirmPasswordField = By.id("ConfirmPassword");
    private final By endRegisterButton = By.id("register-button");
    private final By continueButton = By.xpath("//input[@class='button-1 register-continue-button']");
    private final By successMessage = By.className("result");
    private final By errorMessage = By.className("field-validation-error");

    public void openRegistration() {
        wait.until(ExpectedConditions.elementToBeClickable(registerButton)).click();
    }

    public void endRegistration() {
        wait.until(ExpectedConditions.elementToBeClickable(endRegisterButton)).click();
    }

    public void registerUser(String firstName, String lastName, String email, String password, String confirmPassword) {
        wait.until(ExpectedConditions.elementToBeClickable(gender)).click();
        driver.findElement(firstNameField).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(confirmPasswordField).sendKeys(confirmPassword);
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

    public boolean isPasswordDoNotMatch() {
        String text = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
        return text.contains("The password and confirmation password do not match.");
    }

    public boolean isWrongEmail() {
        String text = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
        return text.contains("Wrong email");
    }

    public boolean isEmptyName() {
        String text = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
        return text.contains("First name is required.");
    }

    public boolean isEmptyLastName() {
        String text = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
        return text.contains("Last name is required.");
    }

    public boolean isEmptyEmail() {
        String text = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
        return text.contains("Email is required.");
    }

    public boolean isPasswordInvalidate() {
        String text = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
        return text.contains("The password should have at least 6 characters.");
    }
}
