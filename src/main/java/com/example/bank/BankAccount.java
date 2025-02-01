package com.example.bank;

// Абстрактный класс BankAccount
public abstract class BankAccount {
    private final String accountNumber;
    private double balance;
    private final String accountHolder;

    public BankAccount(String accountNumber, double balance, String accountHolder) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.accountHolder = accountHolder;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    // Абстрактный метод для снятия средств
    public abstract void withdraw(double amount);

    // Конкретный метод для пополнения счета
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited " + amount + ". New balance: " + balance);
        } else {
            System.out.println("Invalid deposit amount: " + amount);
        }
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", accountHolder='" + accountHolder + '\'' +
                '}';
    }
}
