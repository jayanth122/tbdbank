package org.ece.repository;

import org.ece.dto.Customer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CustomerOperations extends CrudRepository<Customer, String> {
    Optional<Customer> findByUserName(String username);
    Optional<Customer> findBySinNumber(Long sinNumber);
    Optional<Customer> findByDebitCardNumber(Long debitCardNumber);
}
