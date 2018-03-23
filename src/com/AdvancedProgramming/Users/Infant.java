package com.AdvancedProgramming.Users;

/**
 * Infact class is instantiated when the user is below 2 years old.
 *
 * @version 1.0.0 22nd March 2018
 * @author Tejas Cherukara
 */
public class Infant extends YoungAdult {

    protected Infant(String name, Integer age, String profilePicture, String status, User parent1, User parent2) {
        super(name, age, profilePicture, status, parent1, parent2);
    }

    /**
     * Overrides add relation function from user as only guardian can be friends with infants.
     * @param newFriend
     */
    @Override
    public void addRelation(Relationship newFriend) {
        if(isGuardian.test(newFriend)){
            super.addRelation(newFriend);
        } else {
            System.out.println("Infants cannot have friends");
        }
    }
}
