package com.company.tests;

import com.company.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

public class ShopTests {

    private final String SHOP_NAME = "TestShop";
    private final String ANNA = "Anna";
    private final String BOB = "Bob";
    private final String MILK = "Milk";
    private final String BANANA = "Banana";
    private final String SOAP = "Soap";
    private final String ORANGE = "Orange";
    private Shop shop;
    private Cashier cashierAnna;
    private Cashier cashierBob;
    private Product milk;
    private Product banana;
    private Product soap;

    @BeforeEach
    public void setup() throws Exception {
        shop = new Shop(SHOP_NAME);

        cashierAnna = new Cashier(1, ANNA, 1200);
        cashierBob = new Cashier(2, BOB, 1300);
        shop.addCashier(cashierAnna);
        shop.addCashier(cashierBob);

        Calendar milkExp = Calendar.getInstance();
        milkExp.add(Calendar.DAY_OF_MONTH, 3);
        milk = new Product(1, MILK, 1.2, ProductCategory.FOOD, milkExp);
        shop.deliverProduct(milk, 10);

        Calendar bananaExp = Calendar.getInstance();
        bananaExp.add(Calendar.DAY_OF_MONTH, 7);
        banana = new Product(2, BANANA, 0.8, ProductCategory.FOOD, bananaExp);
        shop.deliverProduct(banana, 20);

        Calendar soapExp = Calendar.getInstance();
        soapExp.add(Calendar.DAY_OF_MONTH, 365);
        soap = new Product(3, SOAP, 2.0, ProductCategory.NON_FOOD, soapExp);
        shop.deliverProduct(soap, 5);
    }

    @Test
    public void getNameTest() {
        assertEquals(SHOP_NAME, shop.getName());
    }

    @Test
    public void getProductQuantityTest() {
        assertEquals(10, shop.getProductQuantity(milk));
        assertEquals(20, shop.getProductQuantity(banana));
        assertEquals(5, shop.getProductQuantity(soap));
    }

    @Test
    public void deliverProductSuccessTest() throws Exception {
        Product orange = new Product(4, ORANGE, 1.5, ProductCategory.FOOD, Calendar.getInstance());
        shop.deliverProduct(orange, 8);
        assertEquals(8, shop.getProductQuantity(orange));
    }

    @Test
    public void deliverProductInvalidQuantityTest() {
        Product orange = new Product(4, ORANGE, 1.5, ProductCategory.FOOD, Calendar.getInstance());
        assertThrows(InvalidQuantityException.class, () -> shop.deliverProduct(orange, 0));
        assertThrows(InvalidQuantityException.class, () -> shop.deliverProduct(orange, -5));
    }

    @Test
    public void calculatePriceFoodTest() {
        double price = shop.calculatePrice(milk);
        assertTrue(price > milk.getDeliveryPrice());
    }

    @Test
    public void calculatePriceNonFoodTest() {
        double price = shop.calculatePrice(soap);
        assertTrue(price > soap.getDeliveryPrice());
    }

    @Test
    public void calculatePriceWithDiscountTest() throws Exception {
        Calendar exp = Calendar.getInstance();
        exp.add(Calendar.DAY_OF_MONTH, 2);
        Product orange = new Product(5, ORANGE, 1.0, ProductCategory.FOOD, exp);
        shop.deliverProduct(orange, 10);
        double discountedPrice = shop.calculatePrice(orange);
        assertTrue(discountedPrice >= orange.getDeliveryPrice() * 1.01);
    }

    @Test
    public void processSaleSingleProductSuccessTest() throws Exception {
        Cart cart = new Cart(shop);
        cart.addProduct(milk, 2);
        Client client = new Client(10);
        CashRegister register = new CashRegister(cashierAnna);
        shop.addRegister(register);

        shop.processSale(register, client, cart);

        assertEquals(8, shop.getProductQuantity(milk));
        assertEquals(1, shop.getReceiptCount());
        assertTrue(shop.getTotalTurnover() > 0);
    }

    @Test
    public void processSaleMultipleProductsSuccessTest() throws Exception {
        Cart cart = new Cart(shop);
        cart.addProduct(milk, 1);
        cart.addProduct(banana, 5);
        Client client = new Client(20);
        CashRegister register = new CashRegister(cashierBob);
        shop.addRegister(register);

        shop.processSale(register, client, cart);

        assertEquals(9, shop.getProductQuantity(milk));
        assertEquals(15, shop.getProductQuantity(banana));
        assertEquals(1, shop.getReceiptCount());
        assertTrue(shop.getTotalTurnover() > 0);
    }

    @Test
    public void processSaleProductBecomesExpiredTest() throws Exception {
        Calendar exp = Calendar.getInstance();
        exp.add(Calendar.DAY_OF_MONTH, 1);
        Product nearExpiryMilk = new Product(7, "NearExpiryMilk", 1.0, ProductCategory.FOOD, exp);
        shop.deliverProduct(nearExpiryMilk, 5);

        Cart cart = new Cart(shop);
        cart.addProduct(nearExpiryMilk, 1);
        Client client = new Client(50);
        CashRegister register = new CashRegister(cashierAnna);
        shop.addRegister(register);

        exp.add(Calendar.DAY_OF_MONTH, -2);

        ExpiredProductException exception = assertThrows(
                ExpiredProductException.class,
                () -> shop.processSale(register, client, cart)
        );
        assertTrue(exception.getMessage().contains("Product expired: NearExpiryMilk"));
    }

    @Test
    public void processSaleInsufficientFundsTest() throws Exception {
        Cart cart = new Cart(shop);
        cart.addProduct(milk, 2);
        Client client = new Client(1);
        CashRegister register = new CashRegister(cashierAnna);
        shop.addRegister(register);

        assertThrows(InsufficientFundsException.class, () -> shop.processSale(register, client, cart));
    }

    @Test
    public void totalTurnoverAndProfitTest() throws Exception {
        Cart cart1 = new Cart(shop);
        cart1.addProduct(milk, 2);
        Cart cart2 = new Cart(shop);
        cart2.addProduct(banana, 3);
        Client client = new Client(50);
        CashRegister register = new CashRegister(cashierAnna);
        shop.addRegister(register);

        shop.processSale(register, client, cart1);
        shop.processSale(register, client, cart2);

        assertTrue(shop.getTotalTurnover() > 0);
        assertEquals(30, shop.getTotalDeliveryCost(), 30);
        assertEquals(shop.getTotalProfit(), shop.getTotalTurnover() - shop.getTotalDeliveryCost());
    }
}