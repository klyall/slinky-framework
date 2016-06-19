package org.slinkyframework.client.test.example;

import org.slinkyframework.repository.SlinkyRepository;
import org.slinkyframework.client.test.example.domain.Account;

public interface ExampleRepository extends SlinkyRepository {

    Account retrieveAccountDetails(String accountNumber);

    void deleteAccount(String accountNumber);
}
