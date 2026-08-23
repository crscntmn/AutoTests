package auto.tests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class CheckoutPage extends BasePage {

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    //Локаторы
    private final By continueButton = By.cssSelector("#shipping-buttons-container input.new-address-next-step-button");
    private final By inputName = By.name("BillingNewAddress.FirstName");
    private final By inputLastName = By.name("BillingNewAddress.LastName");
    private final By inputEmail = By.name("BillingNewAddress.Email");
    private final By dropDownCountry = By.id("BillingNewAddress_CountryId");


    public void selectCountry(String countryName) {
        Select country = new Select(driver.findElement(dropDownCountry));
        country.selectByVisibleText(countryName);
    }
}
