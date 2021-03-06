package com.AdvancedProgramming.Menus;

import com.AdvancedProgramming.MiniNet;
import com.AdvancedProgramming.States;
import com.AdvancedProgramming.Users.RelationType;
import com.AdvancedProgramming.Users.User;
import com.AdvancedProgramming.Users.UserFactory;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * AddUserMenu is the menu used to add a new user.
 *
 * @version 1.0.0 22nd March 2018
 * @author Tejas Cherukara
 */
public class AddUserMenu implements Menu {

    public AddUserMenu() {}

    /**
     * Add User menu options
     */
    @Override
    public void getMenu() {
        System.out.println("\nAdd User");
        System.out.println(MiniNet.DIVIDER);
    }

    /**
     * Parse add user inputs.
     * @param input
     */
    @Override
    public void doAction(Scanner input) {

        String name;
        do{
            System.out.print("Enter name (must be unique): ");
            name = input.nextLine();
            checkSpecialInput(name);
        } while (Objects.equals(name, "") || Objects.equals(name, " ") || !userService.uniqueName(name));

        String age;
        do{
            System.out.print("Enter age (must be number): ");
            age = input.nextLine();
            checkSpecialInput(age);
        } while (!MiniNet.isInputInt(age) || Integer.parseInt(age) < 0);

        Integer ageInt = Integer.parseInt(age);

        System.out.print("Enter profile pic (Optional): ");
        String profilePicture = input.nextLine();

        System.out.print("Enter status (Optional): ");
        String status = input.nextLine();

        if (ageInt >= UserFactory.YOUNG_ADULT) {
            userService.addUser(UserFactory.getUser(name,ageInt,profilePicture,status));
        } else {
            getYoungAdult(input, name, ageInt, profilePicture, status);
        }
        MiniNet.switchState(States.MAIN_MENU);
    }

    /**
     * Get parent 1 and parent 2 for Young adult users.
     * @param input Scanner to get parent 1 and 2 name
     * @param name Name of the new Young Adult / infant.
     * @param ageInt Age of the new Young adult / infant.
     * @param profilePicture profile picture of the new Young adult / infant.
     * @param status status of the new Young adult / infant.
     */
    private void getYoungAdult(Scanner input, String name, Integer ageInt, String profilePicture, String status) {
        // If age is less than 16, they need to choose 2 parents.
        List<User> adults = userService.getUsers().stream().filter(UserFactory.isAdult).collect(Collectors.toList());
        if (adults.size() >= 2) {
            IntStream.range(0, adults.size()).mapToObj(i -> (i + 1) + ". " + userService.getUsers().get(i).getName()).forEach(System.out::println);

            Optional<User> guardianOne, guardianTwo;
            String guardianOneName, guardianTwoName;

            do {
                System.out.print("Enter name of guardian 1: ");
                guardianOneName = input.nextLine();
                guardianOne = userService.getUserWithName(guardianOneName);
                if (isSpecialInput(guardianOneName)) {
                    break;
                }
            } while (!guardianOne.isPresent());

            if (!guardianOne.isPresent()) {
                checkSpecialInput(guardianOneName);
            }

            do {//Check if the parent 1 already has a coparent assciated with them.
                if (guardianOne.isPresent() && guardianOne.get().getFriends().stream()
                        .anyMatch(o -> o.getRelation() == RelationType.COPARENT)) {
                    guardianTwo = checkForCoParent(guardianOne.get());
                    guardianTwoName = guardianTwo.get().getName();

                } else {
                    System.out.print("Enter name of guardian 2 (Cannot be same user as guardian 1): ");
                    guardianTwoName = input.nextLine();
                    guardianTwo = userService.getUserWithName(guardianTwoName);
                    if (isSpecialInput(guardianTwoName)) {break;}
                }
            } while (!guardianTwo.isPresent() || guardianTwo.get() == guardianOne.get());

            if (guardianTwo.isPresent()) {
                userService.addUser(
                        UserFactory.getUser(name, ageInt, profilePicture, status, guardianOne.get(), guardianTwo.get())
                );
            } else {
                System.out.println("\n\n Error creating user.\n\n");
                checkSpecialInput(guardianTwoName);
            }
        } else {
            System.out.println("\nNeed at least 2 adults to add a young adult.\n");
        }
    }

    /**
     * Check if a person already has a coparent relationship.
     * @param guardianOne user to check the coparent relationship for.
     * @return Optional user to see if a coparent exists.
     */
    private Optional<User> checkForCoParent(User guardianOne) {
        Optional<User> guardianTwo;
        guardianTwo = Optional.ofNullable(guardianOne
                .getFriends().stream()
                .filter(o -> o.getRelation() == RelationType.COPARENT)
                .findFirst().get().getUser());
        System.out.println("\n\n"+guardianOne.getName()+ "'s partner is "+guardianTwo.get().getName());
        System.out.println(guardianTwo.get().getName()+" is automatically selected as second guardian.");
        return guardianTwo;
    }

}