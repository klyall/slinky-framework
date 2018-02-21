package org.slinkyframework.application.test.example;

import org.slinkyframework.application.test.example.domain.Account;

public class ExampleApplicationImpl implements ExampleApplication {

    public static final String FORCED_EXCEPTION_ACCOUNT = "99999999";

    @Override
    public Account retrieveAccountDetails(String accountNumber) {
        return new Account();
    }

    @Override
    public void deleteAccount(String accountNumber) {
        privateMethod(accountNumber);
    }

    @Override
    public void firstMethod() {
        secondMethod();
    }

    @Override
    public void secondMethod() { }

    private void privateMethod(String accountNumber) {
        if (accountNumber.equals(FORCED_EXCEPTION_ACCOUNT)) {
            throw new IllegalArgumentException("Forced exception to test out logging and exception handling");
        }

    }
}
