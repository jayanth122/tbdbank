package org.ece.repository;

import org.ece.dto.User;

import org.springframework.data.repository.CrudRepository;

public interface UserOperations extends CrudRepository<User, String> {

}
