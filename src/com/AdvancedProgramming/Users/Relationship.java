package com.AdvancedProgramming.Users;

/**
 * UserFactory utilizes the factory method to create the appropriate User given the age.
 *
 * @version 1.0.0 10th March 2018
 * @author Tejas Cherukara
 */
public class Relationship {

    private RelationType relation;
    private User user;

    public Relationship(RelationType relation, User user) {
        this.relation = relation;
        this.user = user;
    }

    public RelationType getRelation() {
        return relation;
    }

    public void setRelation(RelationType relation) {
        this.relation = relation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}