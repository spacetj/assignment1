package com.AdvancedProgramming.Users;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * UserFactory utilizes the factory method to create the appropriate User given the age.
 *
 * @version 1.0.0 10th March 2018
 * @author Tejas Cherukara
 */
public class YoungAdult extends User {

    private static Predicate<Relationship> isParent = relationship -> relationship.getRelation() == RelationType.PARENT;

    protected YoungAdult(String name, Integer age, String profilePicture, String status, User parent1, User parent2) {
        super(name, age, profilePicture, status);
        addDependantsTo(parent1, parent2);
    }

    private void addDependantsTo(User parent1, User parent2){
        this.addRelation(new Relationship(RelationType.PARENT, parent1));
        this.addRelation(new Relationship(RelationType.PARENT, parent2));
        parent1.addRelation(new Relationship(RelationType.COPARENT, parent2));
    }

    @Override
    public void addRelation(Relationship newFriend) {

        if (isParent.test(newFriend) || (isAgeGapLessThan5(newFriend.getUser()) &&
                isYoungAdultFromDiffFamily(newFriend))) {
            super.addRelation(newFriend);
        }
    }

    private Boolean isAgeGapLessThan5(User user){
        return Math.abs(this.getAge() - user.getAge()) == 4;
    }

    private Boolean isYoungAdultFromDiffFamily(Relationship relation) {

        if(!UserFactory.isYoungAdult.test(relation.getUser())){
            return false;
        }

        List<User> parents = relationships.stream()
                .filter(o -> o.getRelation() == RelationType.PARENT)
                .map(Relationship::getUser).collect(Collectors.toList());

        // Check that the new friend doesn't have a relation to current users parents.
        return !(relation.getUser().getUserRelation(parents.get(0)).isPresent() ||
                relation.getUser().getUserRelation(parents.get(1)).isPresent());
    }
}
