package com.AdvancedProgramming.Users;

/**
 * MiniNet is the main class which controls the java social network.
 *
 * Run this class in order to start the command line application.
 *
 * @version 1.0.0 10th March 2018
 * @author Tejas Cherukara
 */
public interface UserActions {
    void addRelation(Relationship friend);
    void deleteRelation(Relationship name);
    void showFriends();
}
