package org.slinkyframework.common.logging.test.example;

public class ExampleSavingsAccount extends ExampleAbstractAccount{

    private static final double INTEREST_RATE = 0.1;

    public ExampleSavingsAccount(String accoutNumber, double balance) {
        super(accoutNumber, balance);
    }

    @Override
    double calculateInterest() {
        return getBalance() * INTEREST_RATE;
    }
}
