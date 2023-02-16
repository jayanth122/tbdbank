package org.ece.repository;

import org.ece.dto.Interac;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface InteracOperations extends CrudRepository<Interac, String> {
    Optional<Interac> findInteracByCustomerId(String customerId);
    Optional<Interac> findInteracByEmail(String email);
}
