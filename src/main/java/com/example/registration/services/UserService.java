package com.example.registration.services;

import org.springframework.stereotype.Service;
import com.example.registration.dao.UserDAO;

@Service
public class UserService {

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public boolean authenticate(String username, String password) {
        return userDAO.authenticate(username, password);
    }

    public String getRole(String username, String password) {
        return userDAO.getRole(username, password);
    }
}
