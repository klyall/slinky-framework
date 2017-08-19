package org.slinkyframework.client.test.example;

import org.slinkyframework.client.test.example.domain.Account;
import org.slinkyframework.common.logging.Loggable;

public class ExampleClientImpl implements ExampleClient {

    public static final String FORCED_EXCEPTION_ACCOUNT = "99999999";

    @Override
    public Account retrieveAccountDetails(@Loggable String accountNumber) {
        return new Account();
    }

    @Override
    public void updateAccountDetails(Account account) {
    }

    @Override
    public void deleteAccount(String accountNumber) {
        privateMethod(accountNumber);
    }

    private void privateMethod(String accountNumber) {
        if (accountNumber.equals(FORCED_EXCEPTION_ACCOUNT)) {
            throw new IllegalArgumentException("Forced exception to test out logging and exception handling");
        }

    }
}
