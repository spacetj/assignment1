package com.AdvancedProgramming.Users;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * Adult is instantiated when a user is over 15 years old.
 *
 * @version 1.0.0 22nd March 2018
 * @author Tejas Cherukara
 */
public class Adult extends User {

    private Predicate<Relationship> isCoParent = relationship -> relationship.getRelation() == RelationType.COPARENT;

    protected Adult(String name, Integer age, String profilePicture, String status) {
        super(name, age, profilePicture, status);
    }

    /**
     * Overrides the delete relation because dependant and coparent relationships cannot be deleted from an adult.
     * @param friend to be delete.
     */
    @Override
    public void deleteRelation(User friend) {
        Optional<Relationship> userRelation = this.getUserRelation(friend);
        if (userRelation.isPresent() && !isDependant.test(userRelation.get()) && !isCoParent.test(userRelation.get())) {
            super.deleteRelation(friend);
        } else {
            System.out.println("User must exist / Dependant and coparent relations cannot be deleted");
        }
    }
}
