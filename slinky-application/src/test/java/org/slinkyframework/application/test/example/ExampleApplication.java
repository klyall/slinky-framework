package org.slinkyframework.application.test.example;

import org.slinkyframework.application.Application;
import org.slinkyframework.application.test.example.domain.Account;

public interface ExampleApplication extends Application {

    Account retrieveAccountDetails(String accountNumber);

    void deleteAccount(String accountNumber);
}
