package org.slinkyframework.common.logging.test.example;

import org.slinkyframework.common.logging.Loggable;

public abstract class ExampleAbstractAccount {

    @Loggable
    private String accountNumber;

    @Loggable
    private double balance;

    abstract double calculateInterest();

    public ExampleAbstractAccount(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }
}
