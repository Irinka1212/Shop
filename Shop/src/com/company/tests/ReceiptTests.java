package com.company.tests;

import com.company.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

public class ReceiptTests {

    private Cashier cashierAnna;
    private Product milk;
    private Product soap;

    @BeforeEach
    public void setup() {
        cashierAnna = new Cashier(1, "Anna", 1200);

        Calendar milkExp = Calendar.getInstance();
        milkExp.add(Calendar.DAY_OF_MONTH, 5);
        milk = new Product(1, "Milk", 1.2, ProductCategory.FOOD, milkExp);

        Calendar soapExp = Calendar.getInstance();
        soapExp.add(Calendar.DAY_OF_MONTH, 365);
        soap = new Product(2, "Soap", 2.0, ProductCategory.NON_FOOD, soapExp);
    }

    @Test
    public void addReceiptSuccessTest() throws Exception {
        Receipt receipt = new Receipt(cashierAnna);
        receipt.addReceipt(milk, 2, 2.0);
        assertEquals(2 * 2.0, receipt.getTotal(), 0.001);
    }

    @Test
    public void addReceiptMultipleProductsTest() throws Exception {
        Receipt receipt = new Receipt(cashierAnna);
        receipt.addReceipt(milk, 2, 2.0);
        receipt.addReceipt(soap, 1, 3.0);
        assertEquals(2 * 2.0 + 3.0, receipt.getTotal(), 0.001);
    }

    @Test
    public void addReceiptInvalidQuantityTest() {
        Receipt receipt = new Receipt(cashierAnna);
        assertThrows(InvalidQuantityException.class, () -> receipt.addReceipt(milk, 0, 2.0));
        assertThrows(InvalidQuantityException.class, () -> receipt.addReceipt(soap, -1, 3.0));
    }

    @Test
    public void serializeAndDeserializeReceiptTest() throws Exception {
        Receipt receipt = new Receipt(cashierAnna);
        receipt.addReceipt(milk, 2, 2.0);
        receipt.serializeReceipt();

        String filename = "Receipt_" + receipt.getNumber() + ".ser";
        File file = new File(filename);
        assertTrue(file.exists());

        Receipt deserialized = Receipt.deserializeReceipt(filename);
        assertNotNull(deserialized);
        assertEquals(receipt.getNumber(), deserialized.getNumber());
        assertEquals(receipt.getTotal(), deserialized.getTotal(), 0.001);

        file.delete();
    }

    @Test
    public void saveReceiptToFileTest() throws Exception {
        Receipt receipt = new Receipt(cashierAnna);
        receipt.addReceipt(milk, 2, 2.0);
        receipt.saveReceiptToFile();

        String filename = "Receipt_" + receipt.getNumber() + ".txt";
        File file = new File(filename);
        assertTrue(file.exists());
        file.delete();
    }

    @Test
    public void printReceiptTest() throws Exception {
        Receipt receipt = new Receipt(cashierAnna);
        receipt.addReceipt(milk, 2, 2.0);
        assertDoesNotThrow(receipt::printReceipt);
    }

    @Test
    public void addReceiptExistingProductIncrementsQuantity() throws Exception {
        Receipt receipt = new Receipt(cashierAnna);
        receipt.addReceipt(milk, 2, 2.0);
        receipt.addReceipt(milk, 3, 2.0);
        assertEquals(5 * 2.0, receipt.getTotal(), 0.001);
    }
}