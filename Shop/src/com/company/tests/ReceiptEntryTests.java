package com.company.tests;

import com.company.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

public class ReceiptEntryTests {

    private Product milk;
    private Product soap;

    @BeforeEach
    public void setup() {
        Calendar milkExp = Calendar.getInstance();
        milkExp.add(Calendar.DAY_OF_MONTH, 5);
        milk = new Product(1, "Milk", 1.2, ProductCategory.FOOD, milkExp);

        Calendar soapExp = Calendar.getInstance();
        soapExp.add(Calendar.DAY_OF_MONTH, 365);
        soap = new Product(2, "Soap", 2.0, ProductCategory.NON_FOOD, soapExp);
    }

    @Test
    public void constructorSuccessTest() throws Exception {
        ReceiptEntry entry = new ReceiptEntry(milk, 2, 1.5);
        assertEquals(milk, entry.getProduct());
        assertEquals(2, entry.getQuantity(), 0.001);
        assertEquals(1.5, entry.getUnitPrice(), 0.001);
        assertEquals(3.0, entry.getTotalPrice(), 0.001);
    }

    @Test
    public void constructorInvalidQuantityTest() {
        assertThrows(InvalidQuantityException.class, () -> new ReceiptEntry(milk, 0, 1.5));
        assertThrows(InvalidQuantityException.class, () -> new ReceiptEntry(soap, -3, 2.0));
    }

    @Test
    public void addQuantitySuccessTest() throws Exception {
        ReceiptEntry entry = new ReceiptEntry(milk, 2, 1.5);
        entry.addQuantity(3);
        assertEquals(5, entry.getQuantity(), 0.001);
        assertEquals(7.5, entry.getTotalPrice(), 0.001);
    }

    @Test
    public void addQuantityInvalidTest() throws Exception {
        ReceiptEntry entry = new ReceiptEntry(soap, 1, 2.0);
        assertThrows(InvalidQuantityException.class, () -> entry.addQuantity(0));
        assertThrows(InvalidQuantityException.class, () -> entry.addQuantity(-2));
    }
}