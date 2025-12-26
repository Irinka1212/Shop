package com.company.tests;

import com.company.Product;
import com.company.ProductCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTests {

    private final String MILK = "Milk";
    private final String BANANA = "Banana";
    private final String SOAP = "Soap";
    private Product milk;
    private Product soap;
    private Product expiredMilk;

    @BeforeEach
    public void setup() {
        Calendar milkExp = Calendar.getInstance();
        milkExp.add(Calendar.DAY_OF_MONTH, 5);
        milk = new Product(1, MILK, 1.2, ProductCategory.FOOD, milkExp);

        Calendar soapExp = Calendar.getInstance();
        soapExp.add(Calendar.DAY_OF_MONTH, 365);
        soap = new Product(2, SOAP, 2.0, ProductCategory.NON_FOOD, soapExp);

        Calendar expired = Calendar.getInstance();
        expired.add(Calendar.DAY_OF_MONTH, -1);
        expiredMilk = new Product(3, "ExpiredMilk", 1.0, ProductCategory.FOOD, expired);
    }

    @Test
    public void getIdTest() {
        assertEquals(1, milk.getId());
        assertEquals(2, soap.getId());
    }

    @Test
    public void getNameTest() {
        assertEquals(MILK, milk.getName());
        assertEquals(SOAP, soap.getName());
    }

    @Test
    public void getDeliveryPriceTest() {
        assertEquals(1.2, milk.getDeliveryPrice(), 0.001);
        assertEquals(2.0, soap.getDeliveryPrice(), 0.001);
    }

    @Test
    public void getCategoryTest() {
        assertEquals(ProductCategory.FOOD, milk.getCategory());
        assertEquals(ProductCategory.NON_FOOD, soap.getCategory());
    }

    @Test
    public void getExpirationDateTest() {
        assertNotNull(milk.getExpirationDate());
        assertNotNull(soap.getExpirationDate());
    }

    @Test
    public void getDaysUntilExpirationTest() {
        long daysMilk = milk.getDaysUntilExpiration();
        assertTrue(daysMilk > 0 && daysMilk <= 5);

        long daysExpired = expiredMilk.getDaysUntilExpiration();
        assertTrue(daysExpired < 0);
    }

    @Test
    public void isExpiredTest() {
        assertFalse(milk.isExpired());
        assertFalse(soap.isExpired());
        assertTrue(expiredMilk.isExpired());
    }
}