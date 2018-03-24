package com.AdvancedProgramming.Users;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * Adult is instantiated when a user is over 15 years old.
 *
 * @author Tejas Cherukara
 * @version 1.0.0 22nd March 2018
 */
public class Adult extends User {

    private Predicate<Relationship> isCoParent = relationship -> relationship.getRelation() == RelationType.COPARENT;

    protected Adult(String name, Integer age, String profilePicture, String status) {
        super(name, age, profilePicture, status);
    }

    @Override
    public void addRelation(Relationship newFriend) {
        if (!relationships.contains(newFriend) && !newFriend.getUser().getName().equalsIgnoreCase(this.getName())) {
            if (UserFactory.isYoungAdult.test(newFriend.getUser()) || UserFactory.isInfant.test(newFriend.getUser())) {
                if(isDependant.test(newFriend)){
                    relationships.add(newFriend);
                } else {
                    System.out.println("Cannot add young adult or Infant as friend.");
                }
            } else if (UserFactory.isAdult.test(newFriend.getUser()) && isCoParent.test(newFriend)) {
                Optional<Relationship> currentRelo = this.getUserRelation(newFriend.getUser());

                if (currentRelo.isPresent()) {
                    this.getUserRelation(newFriend.getUser()).get().setRelation(RelationType.COPARENT);
                    newFriend.getUser().addRelation(new Relationship(RelationType.COPARENT, this));
                } else if (!currentRelo.isPresent()) {
                    relationships.add(newFriend);
                    newFriend.getUser().addRelation(new Relationship(RelationType.COPARENT, this));
                }

            } else if (UserFactory.isAdult.test(newFriend.getUser()) && isFriend.test(newFriend)) {
                if (this.getUserRelation(newFriend.getUser()).isPresent()) {
                    System.out.println("User is already associated to current user as " +
                            this.getUserRelation(newFriend.getUser()).get().getRelation());
                } else {
                    relationships.add(newFriend);
                    System.out.println("\n\n"+newFriend.getUser().getName()+" added as a new friend to "+this.getName()+"\n\n");
                    newFriend.getUser().addRelation(new Relationship(RelationType.FRIEND, this));
                }
            }
        }
    }

    /**
     * Overrides the delete relation because dependant and coparent relationships cannot be deleted from an adult.
     *
     * @param friend to be delete.
     */
    @Override
    public void deleteRelation(User friend) {
        Optional<Relationship> userRelation = this.getUserRelation(friend);
        if (userRelation.isPresent() && !isDependant.test(userRelation.get()) && !isCoParent.test(userRelation.get())) {
            relationships.remove(this.getUserRelation(friend).get());
            friend.deleteRelation(this);
            System.out.println(this.getName()+" delete "+friend.getName()+" as a friend.");
        } else {
            System.out.println("User must exist / Dependant and coparent relations cannot be deleted");
        }
    }

    @Override
    public void eraseRelationWithUser(User user) {
        Optional<Relationship> userRelo = relationships.stream().filter(o -> o.getUser().equals(user)).findFirst();
        userRelo.ifPresent(relationship -> relationships.remove(relationship));
    }
}
