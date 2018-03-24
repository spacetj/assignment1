package com.AdvancedProgramming.Menus;

import com.AdvancedProgramming.MiniNet;
import com.AdvancedProgramming.States;
import com.AdvancedProgramming.UserService;
import com.AdvancedProgramming.Users.User;

import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

/**
 * MainMenu is the initial user interface that is displayed when MiniNet is run.
 *
 * @version 1.0.0 22nd March 2018
 * @author Tejas Cherukara
 */
public class MainMenu extends MenuTemplate implements Menu {

    public MainMenu() {}

    /**
     * Main menu options.
     */
    @Override
    public void getMenu() {
        System.out.println("\nMain Menu:");
        System.out.println(MiniNet.DIVIDER);
        System.out.println("1. List all users");
        System.out.println("2. Add new user");
        System.out.println("3. Are these two friends?");
        System.out.println("4. Select a user");
    }

    /**
     * Parses user actions for main menu.
     * @param input
     */
    @Override
    public void doAction(Scanner input) {
        System.out.print("Select number from menu: ");
        String action = input.nextLine();
        if(MiniNet.isInputInt(action)){
            int actionInt = Integer.parseInt(action);
            switch(actionInt){
                case 1:
                    showUsers();
                    break;
                case 2:
                    MiniNet.switchState(States.ADD_USER);
                    break;
                case 3:
                    areTheseFriends(input);
                    break;
                case 4:
                    selectUser(input);
                    break;
                default:
                    super.doAction(actionInt);
                    break;
            }
        } else {
            System.out.println("Please input one of the options above.");
        }
    }

    /**
     * Show all the users in the system.
     */
    private void showUsers() {
        if (userService.getUsers().size() > 0) {
            userService.getUsers().stream().filter(Objects::nonNull).forEach(user -> {
                System.out.println("\nName: "+user.getName());
                System.out.println("Age: "+user.getAge());

                if (!Objects.equals(user.getProfilePicture(), "")) {
                    System.out.println("Profile Picture: "+user.getProfilePicture());
                }

                if (!Objects.equals(user.getStatus(), "")) {
                    System.out.println("Status: "+user.getStatus()+"\n");
                }

            });
        } else {
            System.out.println("\nNo users currently registered.\n");
        }
    }

    /**
     * Selects a user and changes the menu.
     * @param input
     */
    private void selectUser(Scanner input) {
        userService.displayUsers();

        Optional<User> user;
        String name;

        do {
            System.out.println("Name of the user (from list above):");
            name = input.nextLine();
            user = userService.getUserWithName(name);

            if (isSpecialInput(name)) {
                break;
            }

        } while (!user.isPresent());

        if (user.isPresent()) {
            userService.setSelectedUser(user.get());
            MiniNet.switchState(States.SELECT_USER);
        } else {
            checkSpecialInput(name);
        }
    }

    /**
     * Checks if 2 users have a relation.
     * @param input
     */
    private void areTheseFriends(Scanner input){
        userService.displayUsers();
        Optional<User> user1, user2;
        String userOne, userTwo;

        do{
            System.out.println("Enter the name of user:");
            userOne = input.nextLine();
            user1 = userService.getUserWithName(userOne);
            if(isSpecialInput(userOne)){
                break;
            }
        } while(!user1.isPresent());

        if(!user1.isPresent()){
            checkSpecialInput(userOne);
        }

        do{
            System.out.println("Enter the name of second user:");
            userTwo = input.nextLine();
            user2 = userService.getUserWithName(userTwo);
            if(isSpecialInput(userTwo)){
                break;
            }
        } while(!user2.isPresent());

        if(!user1.isPresent()){
            checkSpecialInput(userOne);
        } else {
            if (user1.get().getUserRelation(user2.get()).isPresent()) {
                System.out.println(user2.get().getName()+" and "+user1.get().getName()+" has a relation.");
            } else {
                System.out.println("\n\nThey are not friends.\n");
            }
        }

    }
}
