package org.ece.repository;

import org.ece.dto.Manager;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ManagerOperations extends CrudRepository<Manager, String> {
    Optional<Manager> findManagerByUserName(String userName);
}
