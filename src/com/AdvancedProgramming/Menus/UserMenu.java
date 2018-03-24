package com.AdvancedProgramming.Menus;

import com.AdvancedProgramming.MiniNet;
import com.AdvancedProgramming.States;
import com.AdvancedProgramming.Users.RelationType;
import com.AdvancedProgramming.Users.Relationship;
import com.AdvancedProgramming.Users.User;
import com.AdvancedProgramming.Users.UserFactory;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * UserMenu has the menu options once a user is selected.
 *
 * @version 1.0.0 22nd March 2018
 * @author Tejas Cherukara
 */
public class UserMenu extends MenuTemplate implements Menu {

    public UserMenu() {}

    /**
     * The options available when a user is selected.
     */
    @Override
    public void getMenu() {
        if (userService.getSelectedUser().isPresent()) {
            User user = userService.getSelectedUser().get();

            System.out.println(MiniNet.DIVIDER);
            System.out.println("Selected user: "+user.getName());
            System.out.println("Age: "+user.getAge());
            if (user.getProfilePicture() != null) {
                System.out.println("Profile Picture: "+ user.getProfilePicture());
            }
            if (user.getStatus() != null) {
                System.out.println("Status: "+user.getStatus());
            }
            System.out.println();
            System.out.println("Options");
            System.out.println(MiniNet.DIVIDER);
            System.out.println("0. Back");
            System.out.println("1. Show relations");
            System.out.println("2. Add friend");
            System.out.println("3. Delete friend");
            System.out.println("4. Update profile picture");
            System.out.println("5. Update status");
            if(UserFactory.isYoungAdult.test(userService.getSelectedUser().get())){
                System.out.println("6. Show parents");
            } else {
                System.out.println("6. Show dependants");
            }
            System.out.println("7. Delete account");
        } else {
            System.out.println("\nSelect a user first\n");
            MiniNet.switchState(States.MAIN_MENU);
        }
    }

    /**
     * The options that's available for the user.
     * @param input
     */
    @Override
    public void doAction(Scanner input) {
        System.out.print("Select number from menu:");
        String action = input.nextLine();
        if(MiniNet.isInputInt(action) && userService.getSelectedUser().isPresent()){
            int actionInt = Integer.parseInt(action);
            switch(actionInt){
                case 0:
                    MiniNet.switchState(States.MAIN_MENU);
                    break;
                case 1:
                    userService.getSelectedUser().get().showFriends();
                    break;
                case 2:
                    addfriend(input);
                    break;
                case 3:
                    deleteFriend(input);
                    break;
                case 4:
                    updateProfilePicture(input);
                    break;
                case 5:
                    updateStatus(input);
                    break;
                case 6:
                    showSpecialRelations();
                    break;
                case 7:
                    deleteUser();
                    break;
                default:
                    super.doAction(actionInt);
                    break;
            }
        } else {
            System.out.println("Please input one of the options above.\n\n");
        }
    }

    /**
     * Get the name of the user to add as a friend
     * @param input Scanner to get the name of the user to add.
     */
    private void addfriend(Scanner input) {
        userService.displayUsers();
        Optional<User> newFriend;
        String name;

        do {
            System.out.println("\n\n Name of the user to add: ");
            name = input.nextLine();
            newFriend = userService.getUserWithName(name);

            if(isSpecialInput(name)){
                break;
            }

        } while (!newFriend.isPresent());

        if (newFriend.isPresent()) {
            userService.getSelectedUser().get().addRelation(new Relationship(RelationType.FRIEND, newFriend.get()));
        } else {
            checkSpecialInput(name);
        }
    }

    /**
     * Update the profile picture of a user
     * @param input Scanner to get the new profile picture.
     */
    private void updateProfilePicture(Scanner input){
        System.out.print("Enter new profile picture: ");
        String profPic = input.nextLine();
        userService.getSelectedUser().get().setProfilePicture(profPic);
        System.out.println("\nProfile picture updated.");
    }

    /**
     * Update the status of a user.
     * @param input Scanner to get the new status
     */
    private void updateStatus(Scanner input){
        System.out.print("Enter new status: ");
        String status = input.nextLine();
        userService.getSelectedUser().get().setStatus(status);
        System.out.println("\nStatus updated.");
    }

    /**
     * Get the name of the user to delete.
     * @param input Scanner to get the name from System in.
     */
    private void deleteFriend(Scanner input){
        userService.getSelectedUser().get().showFriends();
        Optional<User> delFriend;
        String name;

        if (!userService.getSelectedUser().get().getFriends().isEmpty()) {
            do {
                System.out.println("\n\n Name of the user to delete: ");
                name = input.nextLine();
                delFriend = userService.getUserWithName(name);

                if(isSpecialInput(name)){
                    break;
                }

            } while (!delFriend.isPresent());

            if(delFriend.isPresent() && userService.getSelectedUser().get().getFriends().contains(delFriend.get())){
                userService.getSelectedUser().get().deleteRelation(delFriend.get());
            } else {
                checkSpecialInput(name);
            }
        } else {
            System.out.println("\n\nUser has no relation to delete.\n");
        }
    }

    /**
     * For parents, shows the related dependants, for young adults, it shows the parents.
     */
    private void showSpecialRelations(){
        List<Relationship> relationsToShow;
        System.out.println("\n");
        // If parent, show dependants, else show parents.
        if (userService.getSelectedUser().isPresent() && UserFactory.isYoungAdult.test(userService.getSelectedUser().get())) {
            relationsToShow = userService.getSelectedUser().get()
                    .getFriends().stream()
                    .filter(o -> o.getRelation() == RelationType.GUARDIAN).collect(Collectors.toList());
        } else {
            relationsToShow = userService.getSelectedUser().get()
                    .getFriends().stream()
                    .filter(o -> o.getRelation() == RelationType.DEPENDANT).collect(Collectors.toList());
        }

        if(!relationsToShow.isEmpty()){
            relationsToShow.forEach(o -> System.out.println("Name: "+o.getUser().getName()));
        } else {
            System.out.println("None to display");
        }

        System.out.println("\n");
    }

    /**
     * Delete user from the user store.
     */
    private void deleteUser() {
        // Delete the relations that link to user
        userService.getSelectedUser().get().getFriends().forEach(o -> {
            o.getUser().eraseRelationWithUser(userService.getSelectedUser().get());
        });

        // Delete the user's dependants
        List<Relationship> dependants = userService.getSelectedUser().get().getFriends().stream()
                .filter(o -> o.getRelation() == RelationType.DEPENDANT)
                .collect(Collectors.toList());

        IntStream.range(0, dependants.size()).forEach(i -> {
            userService.deleteUser(dependants.get(i).getUser());
        });

        userService.deleteUser(userService.getSelectedUser().get());

        //Change state
        MiniNet.switchState(States.MAIN_MENU);
        userService.setSelectedUser(null);
    }
}