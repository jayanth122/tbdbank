package org.ece.repository;

import org.ece.dto.Manager;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface ManagerOperations extends CrudRepository<Manager, String> {

    @Query(
            value = "SELECT * FROM manager WHERE user_name = ?1",
            nativeQuery = true)
    List<Manager> getManagerByUserName(String userName);
}
