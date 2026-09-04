package auto.tests.components;

import auto.tests.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HeaderComponent extends BasePage {

    public HeaderComponent(WebDriver driver) {
        super(driver);
    }

    private final By registerButton = By.cssSelector(".ico-register");
    private final By loginButton = By.cssSelector(".ico-login");
    private final By cartButton = By.cssSelector(".cart-label");
    private final By logoutButton = By.cssSelector(".ico-logout");
    private final By cartQty = By.cssSelector(".cart-qty");
    private final By successfulAddToCart = By.cssSelector(".bar-notification.success");
//    private final By searchField = By.id("small-searchterms");
//    private final By searchButton = By.cssSelector(".button-1.search-box-button");

    public void openRegistration() {
        click(registerButton);
    }

    public void openLogin() {
        click(loginButton);
    }

    public void openCart() {
        click(cartButton);
    }

    public void logout() {
        click(logoutButton);
    }

    public void addProductToCart(String productName) {
        By addToCart = By.xpath("//a[text()='" + productName + "']/ancestor::div[@class='item-box']//input[@value='Add to cart']");
        click(addToCart);
        waitVisible(cartQty);
    }

    public boolean isLoginButtonDisplayed() {
        return isDisplayed(loginButton);
    }

    public int getCountItemsInHeader() {
        String text = getText(cartQty);
        return Integer.parseInt(text.replaceAll("[^0-9]", ""));
    }
}
