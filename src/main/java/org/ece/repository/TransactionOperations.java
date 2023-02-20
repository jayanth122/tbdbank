package org.ece.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.ece.dto.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionOperations extends CrudRepository<Transaction, String> {

    List<Transaction> findTransactionsByCustomerId(String customerId);
    @Query("SELECT t FROM Transaction t WHERE t.transactionDate BETWEEN ?1 AND ?2")
    List<Transaction> findAllWithDateBeforeAndDateAfter(@Param("date") LocalDate transactionFromDate,
                                                        @Param("date") LocalDate transactionToDate);

    @Query("SELECT a FROM Transaction a WHERE a.customerId "
            + "LIKE :customerId AND a.transactionDate BETWEEN :start AND :end")
    List<Transaction> findByLevelBetween(@Param("customerId") String customerId,
                                         @Param("start") LocalDate start,
                                         @Param("end") LocalDate end);
    //a.customerId = :customerId AND
}
