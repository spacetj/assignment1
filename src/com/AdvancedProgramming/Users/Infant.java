package com.AdvancedProgramming.Users;

import java.util.Optional;

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
        if (!relationships.contains(newFriend) && !newFriend.getUser().getName().equalsIgnoreCase(this.getName())) {
            if(isGuardian.test(newFriend)){
                relationships.add(newFriend);
                newFriend.getUser().addRelation(new Relationship(RelationType.DEPENDANT, this));
            } else {
                System.out.println("\n\nInfants cannot have friends\n\n");
            }
        }
    }

    /**
     * Enables user to delete a relation, overriden from User abstract class according to the
     * specific subclass constraints.
     * @param friend User to be deleted.
     */
    @Override
    public void deleteRelation(User friend) {
        System.out.println("\n\nCannot delete guardian relation\n\n");
    }

    /**
     * When another user is being deleted from the social network, this method is called to ensure that
     * all exisiting connections between this user and the to be deleted user are erased.
     * @param user that is to be deleted.
     */
    @Override
    public void eraseRelationWithUser(User user){
        Optional<Relationship> userRelo = relationships.stream().filter(o -> o.getUser().equals(user)).findFirst();
        if(userRelo.isPresent() && isGuardian.test(userRelo.get())){
            System.out.println("\n\n Deleting "+this.getName()+" as "+user.getName()+" is one of its guardians");
            relationships.forEach(o -> {
                if (!o.getUser().equals(user)) {
                    o.getUser().eraseRelationWithUser(this);
                }
            });
        }
    }


}
