package org.slinkyframework.application.test.example;

import org.slinkyframework.application.SlinkyApplication;
import org.slinkyframework.application.test.example.domain.Account;

public interface ExampleApplication extends SlinkyApplication {

    Account retrieveAccountDetails(String accountNumber);

    void deleteAccount(String accountNumber);

    void firstMethod();
    void secondMethod();
}
