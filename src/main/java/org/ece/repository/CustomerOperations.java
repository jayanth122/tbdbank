package org.ece.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.ece.dto.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CustomerOperations extends CrudRepository<Customer, String> {
    Optional<Customer> findByUserName(String username);
    Optional<Customer> findBySinNumber(Long sinNumber);

    @Query("SELECT d FROM Customer d WHERE d.customerId like :customerId")
    List<Customer> findCustomerDetailsByCustomerId(@Param("customerId") String customerId);


}
