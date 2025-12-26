package com.company.tests;

import com.company.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

public class CashRegisterTests {

    private Cashier cashierAnna;
    private Shop shop;
    private Product milk;
    private Product banana;

    @BeforeEach
    public void setup() throws Exception {
        cashierAnna = new Cashier(1, "Anna", 1200);
        shop = new Shop("TestShop");
        shop.addCashier(cashierAnna);

        Calendar milkExp = Calendar.getInstance();
        milkExp.add(Calendar.DAY_OF_MONTH, 3);
        milk = new Product(1, "Milk", 1.2, ProductCategory.FOOD, milkExp);
        shop.deliverProduct(milk, 10);

        Calendar bananaExp = Calendar.getInstance();
        bananaExp.add(Calendar.DAY_OF_MONTH, 7);
        banana = new Product(2, "Banana", 0.8, ProductCategory.FOOD, bananaExp);
        shop.deliverProduct(banana, 20);
    }

    @Test
    public void createReceiptSuccessTest() throws Exception {
        CashRegister register = new CashRegister(cashierAnna);
        Cart cart = new Cart(shop);
        cart.addProduct(milk, 2);
        cart.addProduct(banana, 5);

        Receipt receipt = register.createReceipt(cart, shop);

        assertNotNull(receipt);
        assertEquals(cashierAnna, receipt.getCashier());
        assertTrue(receipt.getTotal() > 0);
    }
}