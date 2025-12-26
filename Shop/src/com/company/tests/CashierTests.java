package com.company.tests;

import com.company.Cashier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CashierTests {

    private Cashier cashierAnna;
    private Cashier cashierBob;

    @BeforeEach
    public void setup() {
        cashierAnna = new Cashier(1, "Anna", 1200);
        cashierBob = new Cashier(2, "Bob", 1300);
    }

    @Test
    public void getIdTest() {
        assertEquals(1, cashierAnna.getId());
        assertEquals(2, cashierBob.getId());
    }

    @Test
    public void getNameTest() {
        assertEquals("Anna", cashierAnna.getName());
        assertEquals("Bob", cashierBob.getName());
    }

    @Test
    public void getMonthlySalaryTest() {
        assertEquals(1200, cashierAnna.getMonthlySalary(), 0.001);
        assertEquals(1300, cashierBob.getMonthlySalary(), 0.001);
    }
}