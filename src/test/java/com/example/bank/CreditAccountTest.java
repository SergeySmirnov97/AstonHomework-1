package com.example.bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;


public class CreditAccountTest {
    private static final NumberFormat FORMATTER = NumberFormat.getInstance(Locale.US);

    static {
        if (FORMATTER instanceof DecimalFormat) {
            ((DecimalFormat) FORMATTER).applyPattern("0.00");
        }
    }
    private CreditAccount creditAccount;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        creditAccount = new CreditAccount("67890", 1000, "Jane Doe", -5000);
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void testWithdraw_success() {
        double withdrawAmount = 2000;
        double expectedBalance = 1000 - (withdrawAmount * 1.01);
        creditAccount.withdraw(withdrawAmount);
        assertEquals(expectedBalance, creditAccount.getBalance());
        String expectedOutput = String.format("Withdrawn %s with fee %s. New balance: %s\n",
                FORMATTER.format(withdrawAmount),
                FORMATTER.format(BigDecimal.valueOf(withdrawAmount).multiply(BigDecimal.valueOf(0.01)).setScale(2, java.math.RoundingMode.HALF_UP)),
                FORMATTER.format(expectedBalance));
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    void testWithdraw_exceedsLimit() {
        double withdrawAmount = 6500;
        InsufficientFundsException exception = assertThrows(InsufficientFundsException.class, () -> creditAccount.withdraw(withdrawAmount));
        assertEquals("Withdrawal of " + FORMATTER.format(withdrawAmount) + " exceeds credit limit: " + FORMATTER.format(creditAccount.getCreditLimit()) + ". Balance:" + FORMATTER.format(creditAccount.getBalance()), exception.getMessage());
    }

    @Test
    void testWithdraw_negativeAmount() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> creditAccount.withdraw(-100));
        assertEquals("Withdraw amount should be greater than zero", exception.getMessage());
    }

    @Test
    void testDeposit_success() {
        double depositAmount = 500;
        double expectedBalance = 1000 + depositAmount;
        creditAccount.deposit(depositAmount);
        assertEquals(expectedBalance, creditAccount.getBalance());
    }

    @Test
    void testDeposit_negativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> creditAccount.deposit(-100));
    }

    @Test
    void testCreditAccountCreation_negativeLimit() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new CreditAccount("67890", 1000, "Jane Doe", -100));
        assertEquals("Credit limit cannot be negative", exception.getMessage());
    }
}