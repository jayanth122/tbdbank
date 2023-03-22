package org.ece.repository;

import org.ece.dto.CustomerPayee;
import org.springframework.data.repository.CrudRepository;

public interface CustomerPayeeOperations extends CrudRepository<CustomerPayee, String> {
}
