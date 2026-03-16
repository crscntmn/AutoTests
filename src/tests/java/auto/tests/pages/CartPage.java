package auto.tests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CartPage extends BasePage {

    public CartPage(WebDriver driver) {
        super(driver);
    }

    //Локаторы
    private By checkoutButton = By.cssSelector(".button-1.checkout-button");
    private By cartButton = By.cssSelector(".cart-qty");
    private By succesfulAddToCart = By.cssSelector(".content");
    private By termsOfServiceError = By.cssSelector(".ui-button-icon-primary.ui-icon.ui-icon-closethick");
    private By termsOfServiceCheckbox = By.id("termsofservice");
    private By needAuthorization = By.cssSelector(".page-title");
    private By qty = By.id("addtocart_72_EnteredQuantity");
    private By addToCartButtonFromDetail = By.id("add-to-cart-button-72");


    public void openCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartButton)).click();
    }

    public void clickCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton)).click();
    }

    public void addProductToCart(String productName) {
        By addToCart = By.xpath("//a[text()='" + productName + "']/ancestor::div[@class='item-box']//input[@value='Add to cart']");
        wait.until(ExpectedConditions.elementToBeClickable(addToCart)).click();
    }

    public boolean addProductToCartSuccessful(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(succesfulAddToCart)).isDisplayed();
    }

    public boolean showTermsOfService() {
        return wait.until(ExpectedConditions.elementToBeClickable(termsOfServiceError)).isDisplayed();
    }

    public void clickCheckbox() {
        wait.until(ExpectedConditions.elementToBeClickable(termsOfServiceCheckbox)).click();
    }

    public boolean needAuthorization() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(needAuthorization)).isDisplayed();
    }

    public boolean openProductDetail() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(qty)).isDisplayed();
    }

    public void confirmAddCheapComputerToCart() {
        WebElement inputQty = driver.findElement(qty);
        inputQty.click();
        inputQty.sendKeys("10");
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButtonFromDetail)).click();

    }
}
