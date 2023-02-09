package org.ece.service;

import org.ece.dto.Customer;
import org.ece.dto.Manager;
import org.ece.dto.User;
import org.ece.repository.CustomerOperations;
import org.ece.repository.ManagerOperations;
import org.ece.repository.UserOperations;
import org.springframework.stereotype.Service;

@Service
public class DBOperations {

    private UserOperations userOperations;
    private ManagerOperations managerOperations;
    private final CustomerOperations customerOperations;


    public DBOperations(final ManagerOperations managerOperations, final UserOperations userOperations,
                        final CustomerOperations customerOperations) {
        this.managerOperations = managerOperations;
        this.userOperations = userOperations;
        this.customerOperations = customerOperations;
    }

    private User setManagerDetailsInUser(final User user) {
        Manager manager = managerOperations.findManagerByUserName(user.getUserName()).get();
        user.setFirstName(manager.getFirstName());
        user.setLastName(manager.getLastName());
        user.setUserId(manager.getManagerId());
        return user;
    }

    private User setCustomerDetailsInUser(final User user) {
        Customer customer = customerOperations.findByUserName(user.getUserName()).get();
        user.setFirstName(customer.getFirstName());
        user.setLastName(customer.getLastName());
        user.setUserId(customer.getCustomerId());
        return user;
    }

    public User getUserDetails(final User user) {
        switch (user.getAccountType()) {
            case MANAGER:
                return setManagerDetailsInUser(user);
            case CUSTOMER:
                return setCustomerDetailsInUser(user);
            default:
                return null;
        }
    }
}
