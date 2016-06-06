package org.slinkyframework.client.test.example;

import org.slinkyframework.repository.Repository;
import org.slinkyframework.client.test.example.domain.Account;

public interface ExampleRepository extends Repository {

    Account retrieveAccountDetails(String accountNumber);

    void deleteAccount(String accountNumber);
}
