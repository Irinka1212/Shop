package com.company.tests;

import com.company.Client;
import com.company.InsufficientFundsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClientTests {

    private Client clientWithMoney;
    private Client clientWithoutMoney;

    @BeforeEach
    public void setup() {
        clientWithMoney = new Client(50.0);
        clientWithoutMoney = new Client(0.0);
    }

    @Test
    public void getMoneyTest() {
        assertEquals(50.0, clientWithMoney.getMoney(), 0.001);
        assertEquals(0.0, clientWithoutMoney.getMoney(), 0.001);
    }

    @Test
    public void canPayTest() {
        assertTrue(clientWithMoney.canPay(30.0));
        assertTrue(clientWithMoney.canPay(50.0));
        assertFalse(clientWithMoney.canPay(60.0));
        assertFalse(clientWithoutMoney.canPay(1.0));
    }

    @Test
    public void paySuccessTest() throws InsufficientFundsException {
        clientWithMoney.pay(20.0);
        assertEquals(30.0, clientWithMoney.getMoney(), 0.001);

        clientWithMoney.pay(30.0);
        assertEquals(0.0, clientWithMoney.getMoney(), 0.001);
    }

    @Test
    public void payInsufficientFundsTest() {
        InsufficientFundsException exception = assertThrows(
                InsufficientFundsException.class,
                () -> clientWithMoney.pay(60.0)
        );
        assertEquals(10.0, exception.getMissingAmount(), 0.001);

        exception = assertThrows(
                InsufficientFundsException.class,
                () -> clientWithoutMoney.pay(5.0)
        );
        assertEquals(5.0, exception.getMissingAmount(), 0.001);
    }
}