package org.ece.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.ece.dto.Interac;
import org.ece.dto.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TransactionOperations extends CrudRepository<Transaction, String> {

    List<Transaction> findTransactionsByCustomerId(String customerId);
    List<Transaction> findAllByTransactionDateBetweenAndCustomerId(
            LocalDate transactionDateStart,
            LocalDate transactionDateEnd,
            String CustomerId);
    @Query("select t from Transaction t where t.transactionDate <= :transactionDate")
    List<Transaction> findAllWithDateBefore(
            @Param("date") LocalDate transactionDate);

    @Query("SELECT t FROM Transaction t WHERE t.transactionDate BETWEEN ?1 AND ?2")
    List<Transaction> findAllWithDateBeforeAndDateAfter(@Param("date") LocalDate transactionFromDate,
                                                        @Param("date") LocalDate transactionToDate);

    @Query("SELECT a FROM Transaction a WHERE a.transactionDate BETWEEN :start AND :end")
    List<Transaction> findByLevelBetween(@Param("start") LocalDate start,
                                         @Param("end") LocalDate end);
    //a.customerId = :customerId AND        @Param("customerId") String customerId,
}
