package org.ece.repository;

import org.ece.dto.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionOperations extends CrudRepository<Transaction, String> {
}
