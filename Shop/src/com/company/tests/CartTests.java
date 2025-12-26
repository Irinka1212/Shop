package com.company.tests;

import com.company.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

public class CartTests {

    private Shop shop;
    private Cart cart;
    private Product milk;
    private Product banana;
    private Cashier cashierAnna;

    @BeforeEach
    public void setup() throws Exception {
        shop = new Shop("TestShop");
        cashierAnna = new Cashier(1, "Anna", 1200);
        shop.addCashier(cashierAnna);

        Calendar milkExp = Calendar.getInstance();
        milkExp.add(Calendar.DAY_OF_MONTH, 3);
        milk = new Product(1, "Milk", 1.2, ProductCategory.FOOD, milkExp);
        shop.deliverProduct(milk, 10);

        Calendar bananaExp = Calendar.getInstance();
        bananaExp.add(Calendar.DAY_OF_MONTH, 7);
        banana = new Product(2, "Banana", 0.8, ProductCategory.FOOD, bananaExp);
        shop.deliverProduct(banana, 20);

        cart = new Cart(shop);
    }

    @Test
    public void addProductSuccessTest() throws Exception {
        cart.addProduct(milk, 2);
        cart.addProduct(banana, 3);
        assertEquals(2, cart.getProductList().get(milk));
        assertEquals(3, cart.getProductList().get(banana));
    }

    @Test
    public void addProductInvalidQuantityTest() {
        assertThrows(InvalidQuantityException.class, () -> cart.addProduct(milk, 0));
        assertThrows(InvalidQuantityException.class, () -> cart.addProduct(banana, -5));
    }

    @Test
    public void addProductExpiredTest() throws Exception {
        Calendar expired = Calendar.getInstance();
        expired.add(Calendar.DAY_OF_MONTH, -1);
        Product oldMilk = new Product(3, "OldMilk", 1.0, ProductCategory.FOOD, expired);
        shop.deliverProduct(oldMilk, 5);

        ExpiredProductException exception = assertThrows(
                ExpiredProductException.class,
                () -> cart.addProduct(oldMilk, 1)
        );
        assertTrue(exception.getMessage().contains("Product expired: OldMilk"));
    }

    @Test
    public void addProductInsufficientQuantityTest() throws Exception {
        InsufficientQuantityException exception = assertThrows(
                InsufficientQuantityException.class,
                () -> cart.addProduct(milk, 20) // more than stock
        );
        assertTrue(exception.getMessage().contains("Insufficient quantity for Milk"));
        assertEquals(10.0, exception.getMissing(), 0.001);
    }

    @Test
    public void addMultipleQuantitiesUpdatesCartTest() throws Exception {
        cart.addProduct(milk, 2);
        cart.addProduct(milk, 3);
        assertEquals(5, cart.getProductList().get(milk));
    }
}