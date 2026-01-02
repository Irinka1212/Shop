package com.company;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Receipt implements Serializable {
    private static int counter = 0;
    private final Cashier cashier;
    private final Calendar date;
    private final int number;
    private final List<ReceiptEntry> entries = new ArrayList<>();

    public Receipt(Cashier cashier) {
        this.cashier = cashier;
        this.date = Calendar.getInstance();
        this.number = ++counter;
    }

    public Cashier getCashier() { return cashier;}
    public int getNumber() { return number;}

    public double getTotal() {
        return entries.stream().mapToDouble(ReceiptEntry::getTotalPrice).sum();
    }

    public void addReceipt(Product product, double quantity, double unitPrice) throws InvalidQuantityException {
        if (quantity <= 0) throw new InvalidQuantityException(product);

        for (ReceiptEntry entry : entries) {
            if (entry.getProduct() == product) {
                entry.addQuantity(quantity);
                return;
            }
        }

        entries.add(new ReceiptEntry(product, quantity, unitPrice));
    }

    public void saveReceiptToFile() {
        String filename = "Receipt_" + number + ".txt";
        try (PrintWriter pw = new PrintWriter(filename)) {
            writeReceipt(pw);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printReceipt() {
        writeReceipt(new PrintWriter(System.out, true));
        System.out.println("============================================\n");
    }

    public void serializeReceipt() {
        String filename = "Receipt_" + number + ".ser";
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Receipt deserializeReceipt(String filename) {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(filename))) {
            return (Receipt) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void writeReceipt(PrintWriter pw) {
        pw.println("Receipt #" + number);
        pw.println("Cashier: " + cashier.getName());
        pw.println("Date: " +
                new SimpleDateFormat("dd.MM.yyyy HH:mm").format(date.getTime()));
        pw.println();

        for (ReceiptEntry e : entries) {
            pw.printf(
                    "%s x %.2f = %.2f%n",
                    e.getProduct().getName(),
                    e.getQuantity(),
                    e.getTotalPrice()
            );
        }

        pw.println();
        pw.printf("TOTAL (euro): %.2f%n", getTotal());
        pw.flush();
    }

}
