package org.slinkyframework.client.test.example;

import org.slinkyframework.client.SlinkyClient;
import org.slinkyframework.client.test.example.domain.Account;

public interface ExampleClient extends GenericClient, SlinkyClient {

    Account retrieveAccountDetails(String accountNumber);

    void updateAccountDetails(Account account);

    void deleteAccount(String accountNumber);

    void firstMethod();
    void secondMethod();
}
