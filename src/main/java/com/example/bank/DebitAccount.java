package com.example.bank;

// Класс-наследник DebitAccount
public class DebitAccount extends BankAccount {
    public DebitAccount(String accountNumber, double balance, String accountHolder) {
        super(accountNumber, balance, accountHolder);
    }

    @Override
    public void withdraw(double amount) {
        if (getBalance() >= amount) {
            setBalance(getBalance() - amount);
            System.out.println("Withdrawn " + amount + ". New balance: " + getBalance());
        } else {
            System.out.println("Insufficient funds for withdrawal of " + amount + " in " + getAccountNumber() +". Balance: " + getBalance());
        }
    }
}
