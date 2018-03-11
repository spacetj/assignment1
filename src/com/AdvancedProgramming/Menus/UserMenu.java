package com.AdvancedProgramming.Menus;

import com.AdvancedProgramming.Users.User;
import com.AdvancedProgramming.Users.UserFactory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * UserFactory utilizes the factory method to create the appropriate User given the age.
 *
 * @version 1.0.0 10th March 2018
 * @author Tejas Cherukara
 */
public class UserMenu {

    public User addUser(ArrayList<User> users) {

        Scanner input = new Scanner(System.in);
        System.out.println("Enter name: ");
        String name = input.nextLine();
        System.out.println("Enter age: ");
        Integer age = input.nextInt();
        System.out.println("Enter profile pic (Optional): ");
        String profilePicture = input.nextLine();
        System.out.println("Enter status (Optional): ");
        String status = input.nextLine();

        if (age >= UserFactory.YOUNG_ADULT) {
            input.close();
            return UserFactory.getUser(name,age,profilePicture,status, null, null);
        } else {

            // If age is less than 16, they need to choose 2 parents.
            List<User> adults = users.stream().filter(o -> o.getAge() > 16).collect(Collectors.toList());

            if(adults.size() < 2){
                System.out.println("Need at least 2 adults to add a young adult.");
                return null;
            }

            IntStream.range(1, adults.size())
                    .mapToObj(i -> i+". "+users.get(i).getName())
                    .forEach(System.out::println);

            System.out.println("Enter parent 1 number: ");
            Integer parentOneIndex = input.nextInt();
            Integer parentTwoIndex;

            do {
                System.out.println("Enter parent 2 number (Parent 1 cannot be parent 2): ");
                parentTwoIndex = input.nextInt();
            } while (Objects.equals(parentOneIndex, parentTwoIndex));

            input.close();

            return UserFactory.getUser(name,age,profilePicture,status, adults.get(parentOneIndex - 1), adults.get(parentTwoIndex - 2));

        }
    }

    public void selectUser(User user) {
        System.out.println("==================================");
        System.out.println("Selected user: "+user.getName());
        System.out.println("Age: "+user.getAge());
        if (user.getProfilePicture() != null) {
            System.out.println("Profile Picture"+ user.getProfilePicture());
        }
        if (user.getStatus() != null) {
            System.out.println("Status: "+user.getStatus());
        }
        System.out.println();
        System.out.println("Options");
        System.out.println("==================================");
        System.out.println("1. Show relations");
        System.out.println("2. Add friend");
        System.out.println("3. Delete friend");
        System.out.println("4. Update profile picture");
        System.out.println("5. Update status");
    }
}
