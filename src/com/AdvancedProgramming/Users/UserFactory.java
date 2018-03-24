package com.AdvancedProgramming.Users;

import java.util.function.Predicate;

/**
 * UserFactory utilizes the factory method to create the appropriate User given the age and other specifications.
 *
 * @version 1.0.0 22nd March 2018
 * @author Tejas Cherukara
 */
public class UserFactory{

    public static Integer INFANT_AGE = 2;
    public static Integer YOUNG_ADULT = 16;
    public static Predicate<User> isYoungAdult = user -> user.getAge() < YOUNG_ADULT && user.getAge() > INFANT_AGE;
    public static Predicate<User> isAdult = user -> user.getAge() >= YOUNG_ADULT;
    public static Predicate<User> isInfant = user -> user.getAge() <= INFANT_AGE;

    public static User getUser(String name, Integer age, String profilePicture, String status){
        return getUser(name,age,profilePicture,status,null,null);
    }

    /**
     * UserFactory default create user method which takes in the information required and results in the
     * appropriate subclass of User.
     *
     * @param name name of the user to be created.
     * @param age  age of the user to be created.
     * @param profilePicture profilePicture of the user to be created.
     * @param status status of the user to be created.
     * @param parent1 Mandatory for Young adults and infants, guardian 1 of new user
     * @param parent2 Mandatory for Young adults and infants, guardian 2 of new user
     * @return
     */
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
