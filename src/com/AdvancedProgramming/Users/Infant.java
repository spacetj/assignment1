package com.AdvancedProgramming.Users;

/**
 * UserFactory utilizes the factory method to create the appropriate User given the age.
 *
 * @version 1.0.0 10th March 2018
 * @author Tejas Cherukara
 */
public class Infant extends YoungAdult {

    protected Infant(String name, Integer age, String profilePicture, String status, User parent1, User parent2) {
        super(name, age, profilePicture, status, parent1, parent2);
    }

    @Override
    public void addRelation(Relationship newFriend) {
        if(newFriend.getRelation() == RelationType.PARENT){
            super.addRelation(newFriend);
        }

        System.out.println("Infants cannot have friends");
    }
}
