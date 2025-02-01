package com.example.bank;

// Класс-наследник CreditAccount
public class CreditAccount extends BankAccount implements TransactionFee {
    private final double creditLimit;

    public CreditAccount(String accountNumber, double balance, String accountHolder, double creditLimit) {
        super(accountNumber, balance, accountHolder);
        this.creditLimit = creditLimit;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    @Override
    public void withdraw(double amount) {
        double amountWithFee = applyFee(amount);
        if (getBalance() - amountWithFee >= creditLimit) {
            setBalance(getBalance() - amountWithFee);
            System.out.println("Withdrawn " + amount + " with fee " + (amountWithFee - amount) + ". New balance: " + getBalance());
        } else {
            System.out.println("Withdrawal of " + amount + " exceeds credit limit: " + creditLimit +". Balance:" + getBalance());
        }

    }
    @Override
    public double applyFee(double amount){
        return amount * 1.01;
    }
}
