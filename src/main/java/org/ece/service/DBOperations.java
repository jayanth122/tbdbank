package org.ece.service;

import org.ece.dto.Manager;
import org.ece.dto.User;
import org.ece.repository.ManagerOperations;
import org.ece.repository.UserOperations;
import org.springframework.stereotype.Service;

@Service
public class DBOperations {

    private UserOperations userOperations;
    private ManagerOperations managerOperations;


    public DBOperations(final ManagerOperations managerOperations, final UserOperations userOperations) {
        this.managerOperations = managerOperations;
        this.userOperations = userOperations;
    }

    private User setManagerDetailsInUser(final User user) {
        Manager manager = managerOperations.findManagerByUserName(user.getUserName()).get();
        user.setFirstName(manager.getFirstName());
        user.setLastName(manager.getLastName());
        user.setUserId(manager.getManagerId());
        return user;
    }

    public User getUserDetails(final User user) {
        switch (user.getAccountType()) {
            case MANAGER:
                return setManagerDetailsInUser(user);
            default:
                return null;
        }
    }
}
