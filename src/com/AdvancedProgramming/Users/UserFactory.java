package com.AdvancedProgramming.Users;

import java.util.Scanner;
import java.util.function.Predicate;

/**
 * UserFactory utilizes the factory method to create the appropriate User given the age.
 *
 * @version 1.0.0 10th March 2018
 * @author Tejas Cherukara
 */
public class UserFactory{

    public static Integer INFANT_AGE = 2;
    public static Integer YOUNG_ADULT = 16;
    public static Predicate<User> isYoungAdult = user -> user.getAge() < YOUNG_ADULT;
    public static Predicate<User> isInfant = user -> user.getAge() <= INFANT_AGE;

    public static User getUser(String name, Integer age, String profilePicture, String status, User parent1, User parent2){

        if(age <= INFANT_AGE){
            return new Infant(name, age, profilePicture, status, parent1, parent2);
        } else if (age < YOUNG_ADULT){
            return new YoungAdult(name, age, profilePicture, status, parent1, parent2);
        } else {
            return new Adult(name, age, profilePicture, status);
        }

    }

}
