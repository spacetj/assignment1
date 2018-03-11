package com.AdvancedProgramming.Users;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * UserFactory utilizes the factory method to create the appropriate User given the age.
 *
 * @version 1.0.0 10th March 2018
 * @author Tejas Cherukara
 */
public class Adult extends User {

    protected Adult(String name, Integer age, String profilePicture, String status) {
        super(name, age, profilePicture, status);
    }

}
