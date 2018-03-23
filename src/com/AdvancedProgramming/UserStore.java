package com.AdvancedProgramming;

import com.AdvancedProgramming.Users.User;

import java.util.List;
import java.util.Optional;

/**
 * UserStore allows changing the implementation of getting user data without affecting the MiniNet code.
 *
 * @version 1.0.0 22nd March 2018
 * @author Tejas Cherukara
 */

public interface UserStore {
    List<User> getUsers();
    void addUser(User u);
    void deleteUser(User u);
    Optional<User> getSelectedUser();
    void setSelectedUser(User selectedUser);
    void displayUsers();
    Optional<User> getUserWithName(String name);
}
