package org.slinkyframework.client.test.example;

import org.slinkyframework.client.SlinkyClient;
import org.slinkyframework.client.test.example.domain.Account;

public interface ExampleClient extends SlinkyClient {

    Account retrieveAccountDetails(String accountNumber);

    void deleteAccount(String accountNumber);
}
