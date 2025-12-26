package com.company.tests;

import com.company.ReceiptFileNotFoundException;
import com.company.Util;
import org.junit.jupiter.api.*;
import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class UtilTests {

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    public void setUpStreams() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testReadReceiptExistingFile() throws Exception {
        String testContent = "Receipt #1\nCashier: Test\nTOTAL: 10.0\n";
        File tempFile = File.createTempFile("receipt_test", ".txt");
        tempFile.deleteOnExit();

        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(testContent);
        }

        Util.readReceipt(tempFile.getAbsolutePath());
        String output = outContent.toString();

        assertTrue(output.contains("Receipt #1"));
        assertTrue(output.contains("Cashier: Test"));
        assertTrue(output.contains("TOTAL: 10.0"));
    }

    @Test
    public void testReadReceiptNonExistingFile() {
        assertThrows(ReceiptFileNotFoundException.class,
                () -> Util.readReceipt("non_existing_file.txt")
        );
    }
}