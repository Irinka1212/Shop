package com.company;

import java.util.Calendar;

public class Main {
    public static void main(String[] args) {

        try {
            Shop shop = new Shop("Lidl");

            Cashier anna = new Cashier(1, "Anna", 1200);
            Cashier bob = new Cashier(2, "Bob", 1300);
            shop.addCashier(anna);
            shop.addCashier(bob);

            CashRegister firstRegister = new CashRegister(anna);
            CashRegister secondRegister = new CashRegister(anna);
            CashRegister thirdRegister = new CashRegister(bob);
            shop.addRegister(firstRegister);
            shop.addRegister(secondRegister);
            shop.addRegister(thirdRegister);

            Calendar milkExp = Calendar.getInstance();
            milkExp.add(Calendar.DAY_OF_MONTH, 3);
            Calendar bananaExp = Calendar.getInstance();
            bananaExp.add(Calendar.DAY_OF_MONTH, 7);

            Product milk = new Product(1, "Milk", 1.2, ProductCategory.FOOD, milkExp);
            Product banana = new Product(2, "Banana", 0.8, ProductCategory.FOOD, bananaExp);

            shop.deliverProduct(milk, 10);
            shop.deliverProduct(banana, 20);

            Client firstClient = new Client(20);
            Cart firstCart = new Cart(shop);
            firstCart.addProduct(milk, 5);
            shop.processSale(firstRegister, firstClient, firstCart);

            Client secondClient = new Client(35);
            Cart secondCart = new Cart(shop);
            secondCart.addProduct(milk, 5);
            secondCart.addProduct(banana, 10);
            shop.processSale(secondRegister, secondClient, secondCart);

            Client thirdClient = new Client(15);
            Cart thirdCart = new Cart(shop);
            thirdCart.addProduct(banana, 10);
            shop.processSale(thirdRegister, thirdClient, thirdCart);

            System.out.println("\nReceipts issued: " + shop.getReceiptCount());
            System.out.printf("Total turnover (gross revenue from sales) euro: %.2f%n", shop.getTotalTurnover());
            System.out.printf("Total delivery cost (euro): %.2f%n", shop.getTotalDeliveryCost());
            System.out.printf("Total Profit (turnover - delivery cost) euro: %.2f%n", shop.getTotalProfit());
            System.out.printf("Salary expenses (euro): %.2f%n", shop.getSalaryExpenses());

            System.out.println("\nReading first receipt from file:");
            Util.readReceipt("Receipt_1.txt");

            Receipt deserializedReceipt = Receipt.deserializeReceipt("Receipt_2.ser");
            System.out.println("\nDeserialized second receipt content:");
            deserializedReceipt.printReceipt();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
