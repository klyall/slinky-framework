package org.slinkyframework.client.test.example;

import org.slinkyframework.client.Client;
import org.slinkyframework.client.test.example.domain.Account;

public interface ExampleClient extends Client {

    Account retrieveAccountDetails(String accountNumber);

    void deleteAccount(String accountNumber);
}
