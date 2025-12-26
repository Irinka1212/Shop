package com.company;

public class Client {
    private double money;

    public Client(double money) { this.money = money; }

    public double getMoney() { return money; }
    public boolean canPay(double amount) { return money >= amount; }

    public void pay(double amount) throws InsufficientFundsException {
        if (!canPay(amount)) throw new InsufficientFundsException((amount - money));
        money -= amount;
    }
}