package com.company;

import java.io.*;

public abstract class Util {
    public static void readReceipt(String filename) throws ReceiptFileNotFoundException {
        try (BufferedReader br = new BufferedReader(new FileReader(new File(filename)))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            throw new ReceiptFileNotFoundException(filename);
        }
    }
}