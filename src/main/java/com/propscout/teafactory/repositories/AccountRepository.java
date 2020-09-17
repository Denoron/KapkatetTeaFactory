package com.propscout.teafactory.repositories;

import com.propscout.teafactory.models.entities.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Integer> {
    Optional<Account> findByTransactionAccNo(Long transactionAccNo);
}
