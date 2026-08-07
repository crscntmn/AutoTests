package auto.tests.cart;

import auto.tests.components.HeaderComponent;
import auto.tests.pages.CartPage;
import auto.tests.pages.LoginPage;
import auto.tests.pages.RegistrationPage;
import base.BaseTest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Tag;

public class CartTest extends BaseTest {
    CartPage cartPage;
    RegistrationPage registrationPage;
    LoginPage loginPage;
    HeaderComponent header;
    private final String Laptop = "14.1-inch Laptop";
    private final String GiftCard = "$25 Virtual Gift Card";
    private final String CheapComputer = "Build your own cheap computer";
    private final String OwnComputer = "Build your own computer";
    private final String SimpleComputer = "Simple Computer";

    @BeforeEach
    void Setup() {
        cartPage = new CartPage(driver);
        header = new HeaderComponent(driver);
    }

    @Test
    @DisplayName("1.1 Оформление заказа аномимом без условий соглашения")
    void unknownWithoutCheckbox() {
        //1. Добавление ноутбука в корзину
        header.addProductToCart(Laptop);
        //Ожидаемое поведение: товар успешно добавлен в корзину
        Assertions.assertTrue(cartPage.addProductToCartSuccessful());
        //2. Переход в корзину
        header.openCart();
        //3. Нажатие на кнопку "Checkout"
        cartPage.clickCheckout();
        //Ожидаемое поведение: отображение окна об обсутствии принятия соглашения
        Assertions.assertTrue(cartPage.showTermsOfService());
    }

    @Tag("smoke")
    @Test
    @DisplayName("1.2 Оформление заказа анонимом с условиями соглашения")
    void unknownWithCheckbox() {
        //1. Добавление товара в корзину
        header.addProductToCart(Laptop);
        //Ожидаемое поведение: товар успешно добавлен в корзину
        Assertions.assertTrue(cartPage.addProductToCartSuccessful());
        //2. Переход в корзину
        header.openCart();
        //3. Прожатие чекбокса
        cartPage.clickCheckbox();
        //4. Нажатие на кнопку "Checkout"
        cartPage.clickCheckout();
        //Ожидаемое поведение: необходима авторизация
        Assertions.assertTrue(cartPage.needAuthorization());
    }

    @Tag("regression")
    @Test
    @DisplayName("1.3 Добавление дешевого компьютера в корзину анонимом")
    void cheapComputerToCard() {
        //1. Добавление дешевого компьютера в корзину
        header.addProductToCart(CheapComputer);
        //Ожидаемое поведение: Открытие деталки товара с выбором комплектующих
        Assertions.assertTrue(cartPage.isProductDetailOpened());
        //2. Ввод количества товара и нажатие кнопки "Add to cart"
        cartPage.addProductFromDetail(10);
        //Ожидаемое поведение: Товар успешно добавлен в корзину
        Assertions.assertTrue(cartPage.addProductToCartSuccessful());
    }
}
