package com.AdvancedProgramming.Users;

import javax.management.relation.Relation;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * MiniNet is the main class which controls the java social network.
 *
 * Run this class in order to start the command line application.
 *
 * @version 1.0.0 10th March 2018
 * @author Tejas Cherukara
 */
public abstract class User implements UserActions{

    List<Relationship> relationships = new ArrayList<>();
    private String name;
    private Integer age;
    private String profilePicture;
    private String status;
    public static Predicate<User> isAgeLessThan2 = user -> user.getAge() <= 2;
    public static Predicate<Relationship> isDependant = relationship -> relationship.getRelation() == RelationType.DEPENDANT;

    public User(String name, Integer age, String profilePicture, String status) {
        this.name = name;
        this.age = age;
        this.profilePicture = profilePicture;
        this.status = status;
    }

    public Optional<Relationship> getUserRelation(User user){
        return relationships.stream().filter(o -> o.getUser() == user).findAny();
    }

    @Override
    public void addRelation(Relationship newFriend) {

        if(!relationships.contains(newFriend) &&
                (!isAgeLessThan2.test(newFriend.getUser()) || isDependant.test(newFriend))){
            Optional<Relationship> existingRelation = getUserRelation(newFriend.getUser());

            // If user already has a relation with new friend, then update the exisiting relation status.
            if(existingRelation.isPresent()){
                existingRelation.get().setRelation(newFriend.getRelation());

                //Update the new friends relation status to current user as well.
                if(newFriend.getUser().getUserRelation(this).isPresent()){
                    newFriend.getUser().getUserRelation(this).get().setRelation(newFriend.getRelation());
                }

            } else {
                // Else create a new relation for other users.
                relationships.add(newFriend);
                newFriend.getUser().addRelation(new Relationship(newFriend.getRelation(), this));
            }
        }
    }

    @Override
    public void deleteRelation(Relationship friend) {

        if(relationships.contains(friend)){
            relationships.remove(friend);
        } else {
            System.out.println("No such relation exists.");
        }
    }

    @Override
    public void showFriends() {
        System.out.println("Total number of relationships: "+ relationships.size());

        relationships.forEach(relation -> {
            System.out.println("Relation Type: "+relation.getRelation());
            System.out.println("Name: "+relation.getUser().getName());
            System.out.println("Age: "+relation.getUser().getAge());

            if (relation.getUser().getProfilePicture() != null) {
                System.out.println(relation.getUser().getProfilePicture());
            }

            if (relation.getUser().getStatus() != null) {
                System.out.println(relation.getUser().getStatus());
            }

            System.out.println("============================");
            System.out.println();
        });
    }

    public List<Relationship> getFriends() {
        return relationships;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

