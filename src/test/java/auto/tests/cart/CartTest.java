package auto.tests.cart;

import auto.tests.components.HeaderComponent;
import auto.tests.pages.CartPage;
import auto.tests.pages.LoginPage;
import auto.tests.pages.RegistrationPage;
import auto.tests.testdata.TestData;
import base.BaseTest;
import org.junit.jupiter.api.*;
import java.math.BigDecimal;

public class CartTest extends BaseTest {
    CartPage cartPage;
    RegistrationPage registrationPage;
    LoginPage loginPage;
    HeaderComponent header;
    String EMAIL;
    private final String Laptop = "14.1-inch Laptop";
    private final String GiftCard = "$25 Virtual Gift Card";
    private final String CheapComputer = "Build your own cheap computer";
    private final String OwnComputer = "Build your own computer";
    private final String SimpleComputer = "Simple Computer";

    @BeforeEach
    void Setup() {
        cartPage = new CartPage(driver);
        header = new HeaderComponent(driver);
        EMAIL = TestData.generateEmail();
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
    void cheapComputerToCart() {
        //1. Добавление дешевого компьютера в корзину
        header.addProductToCart(CheapComputer);
        //Ожидаемое поведение: Открытие деталки товара с выбором комплектующих
        Assertions.assertTrue(cartPage.isProductDetailOpened());
        //2. Ввод количества товара и нажатие кнопки "Add to cart"
        cartPage.addProductFromDetail(10);
        //Ожидаемое поведение: Товар успешно добавлен в корзину
        Assertions.assertTrue(cartPage.addProductToCartSuccessful());
    }

    @Test
    @DisplayName("1.4 Добавление нескольких товаров в корзину анонимом")
    void manyPositionToCart() {
        //1. Добавление ноутбука в корзину
        header.addProductToCart(Laptop);
        //Ожидаемое поведение: ноутбук успешно добавлен в корзину
        Assertions.assertTrue(cartPage.addProductToCartSuccessful());
        //2. Добавление подарочной карты в корзину
        header.addProductToCart(GiftCard);
        //Ожидаемое поведение: открытие деталки товара подарочной карты
        Assertions.assertTrue(cartPage.isProductDetailOpened());
        //3. Заполнение обязательных полей в деталке товара подарочной карты
        cartPage.addGiftCardFromDetail(TestData.NAME, EMAIL, TestData.NAME, EMAIL);
        //4. Ввод количества 1 и нажатие кнопки "Add to cart"
        cartPage.addProductFromDetail(1);
        //Ожидаемое поведение: подарочная карта успешно добавлена в корзину
        Assertions.assertTrue(cartPage.addProductToCartSuccessful());
        //5. Открытие корзины
        header.openCart();
        //Ожидаемое поведение: ноутбук и подарочная карта в корзине
        Assertions.assertEquals(2, cartPage.getCountItemsInCart());
    }

    @Test
    @DisplayName("1.5 Повторное добавление одного товара в корзину анонимом")
    void manySamePositionToCart() {
        //1. Добавление ноутбука в корзину
        header.addProductToCart(Laptop);
        //Ожидаемое поведение: ноутбук успешно добавлен в корзину
        Assertions.assertTrue(cartPage.addProductToCartSuccessful());
        //2. Добавление ноутбука в корзину
        header.addProductToCart(Laptop);
        //Ожидаемое поведение: ноутбук успешно добавлен в корзину
        Assertions.assertTrue(cartPage.addProductToCartSuccessful());
        //3. Открытие корзины
        header.openCart();
        //Ожидаемое поведение: 2 ноутбука в корзине
        Assertions.assertEquals(2, cartPage.getProductQuantity());
    }

    @Test
    @DisplayName("1.6 Изменение количества товара в корзине")
    void changeCountInCart() {
        //1. Добавление ноутбука в корзину
        header.addProductToCart(Laptop);
        //Ожидаемое поведение: ноутбук успешно добавлен в корзину
        Assertions.assertTrue(cartPage.addProductToCartSuccessful());
        //2. Открытие корзины
        header.openCart();
        //3. Изменение количества товара с 1 на 5
        cartPage.changeCountInCart(5);
        //Ожидаемое поведение: 5 ноутбуков в корзине
        Assertions.assertEquals(5, cartPage.getProductQuantity());

    }

    @Test
    @DisplayName("1.7 Удаление товара в корзине")
    void deleteFromCart() {
        //1. Добавление ноутбука в корзину
        header.addProductToCart(Laptop);
        //Ожидаемое поведение: ноутбук успешно добавлен в корзину
        Assertions.assertTrue(cartPage.addProductToCartSuccessful());
        //2. Открытие корзины
        header.openCart();
        //3. Удаление ноутбука из корзины
        cartPage.removeFromCart();
        //Ожидаемое поведение: корзина пуста
        Assertions.assertEquals(0, cartPage.getCountItemsInCart());
    }

    @Test
    @DisplayName("1.8 Проверка количества товаров в шапке")
    void countInHeader() {
        //1. Добавление ноутбука в корзину
        header.addProductToCart(Laptop);
        //Ожидаемое поведение: счетчик в шапке изменился на "1"
        Assertions.assertEquals(1, header.getCountItemsInHeader());
    }

    @Test
    @DisplayName("1.9 Изменение количества товара в корзине на 0")
    void changeCountOnZero() {
        //1. Добавление ноутбука в корзину
        header.addProductToCart(Laptop);
        //Ожидаемое поведение: ноутбук успешно добавлен в корзину
        Assertions.assertTrue(cartPage.addProductToCartSuccessful());
        //2. Открытие корзины
        header.openCart();
        //3. Изменение количества товара с 1 на 0
        cartPage.changeCountInCart(0);
        //Ожидаемое поведение: корзина пуста
        Assertions.assertEquals(0, cartPage.getCountItemsInCart());
    }

    @Test
    @DisplayName("1.10 Проверка итоговой цены в корзине")
    void checkTotalPriceInCart() {
        //1. Добавление ноутбука в корзину
        header.addProductToCart(Laptop);
        //Ожидаемое поведение: ноутбук успешно добавлен в корзину
        Assertions.assertTrue(cartPage.addProductToCartSuccessful());
        //2. Открытие корзины
        header.openCart();
        BigDecimal unitPrice = cartPage.getProductUnitPrice(Laptop);
        int quantity = cartPage.getProductQuantity(Laptop);
        BigDecimal productSubtotal = cartPage.getProductTotalPrice(Laptop);
        BigDecimal expectedSubtotal = unitPrice.multiply(BigDecimal.valueOf(quantity));
        //Ожидаемое поведение: корректный подсчет итоговой цены
        Assertions.assertEquals(expectedSubtotal, productSubtotal);
    }

}
