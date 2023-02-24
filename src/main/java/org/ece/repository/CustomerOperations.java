package org.ece.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.ece.dto.Customer;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface CustomerOperations extends CrudRepository<Customer, String> {
    Optional<Customer> findByUserName(String username);
    Optional<Customer> findBySinNumber(Long sinNumber);
    Optional<Customer> findByCustomerId(String customerId);
    @Query("SELECT c.accountBalance FROM Customer c WHERE c.customerId like :customerId")
    Long findAccountBalanceByCustomerId(@Param("customerId") String customerId);
    Optional<Customer> findCustomerByDebitCardNumber(Long debitCardNumber);
}
