package com.AdvancedProgramming.Users;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * YoungAdult extends from User and specifies condition apply to users under 16 years old.
 *
 * @author Tejas Cherukara
 * @version 1.0.0 22nd March 2018
 */
public class YoungAdult extends User {

    protected YoungAdult(String name, Integer age, String profilePicture, String status, User parent1, User parent2) {
        super(name, age, profilePicture, status);
        addDependantsTo(parent1, parent2);
    }

    /**
     * Ensures that the guardian / dependant / coparent relationships are added for all appropriate users.
     * @param parent1 guardian number 1
     * @param parent2 guardian number 2
     */
    private void addDependantsTo(User parent1, User parent2) {
        this.addRelation(new Relationship(RelationType.GUARDIAN, parent1));
        this.addRelation(new Relationship(RelationType.GUARDIAN, parent2));
        parent1.addRelation(new Relationship(RelationType.COPARENT, parent2));
    }

    /**
     * Overrides the User add relation function as for Young adults, new friends have to be less than
     * 3 years in age gap and has to be an young adult from a different family.
     * @param newFriend
     */
    @Override
    public void addRelation(Relationship newFriend) {

        if (isGuardian.test(newFriend) || (isAgeGapLessThan3(newFriend) &&
                isYoungAdultFromDiffFamily(newFriend))) {
            super.addRelation(newFriend);
        } else {
            System.out.println("\nCannot add friend.\n\n");
        }
    }

    /**
     * Overrides the delete relation from User class because Guardian relationships cannot be deleted.
     * @param friend User to be deleted.
     */
    @Override
    public void deleteRelation(User friend) {
        if (this.getUserRelation(friend).isPresent() && !isGuardian.test(this.getUserRelation(friend).get())) {
            super.deleteRelation(friend);
        } else if (this.getUserRelation(friend).get().getRelation() == RelationType.GUARDIAN) {
            System.out.println("Cannot delete a guardian / dependant relation");
        }
    }

    /**
     * Checks that the new friends and the current users age gap is less than 5.
     * @param user
     * @return boolean
     */
    private Boolean isAgeGapLessThan3(Relationship user) {

        if (Math.abs(this.getAge() - user.getUser().getAge()) > 3 && user.getRelation() != RelationType.GUARDIAN) {
            System.out.println("\n\nAge difference is great than 3.\n\n");
            return false;
        }

        return true;
    }

    /**
     * Checks if the new friend is an young adult from a different family
     * @param relation
     * @return boolean
     */
    private Boolean isYoungAdultFromDiffFamily(Relationship relation) {

        if (!UserFactory.isYoungAdult.test(relation.getUser()) && relation.getRelation() != RelationType.GUARDIAN) {
            System.out.println("\n\nOnly young adult can be friend with a young adult.\n");
            return false;
        }

        List<User> parents = relationships.stream()
                .filter(o -> o.getRelation() == RelationType.GUARDIAN)
                .map(Relationship::getUser).collect(Collectors.toList());

        // Check that the new friend doesn't have a relation to current users parents.
        if (relation.getUser().getUserRelation(parents.get(0)).isPresent() ||
                relation.getUser().getUserRelation(parents.get(1)).isPresent()) {
            System.out.println("\n\nYoung adult needs to be from a different family.\n");
            return false;
        }

        return true;
    }
}
