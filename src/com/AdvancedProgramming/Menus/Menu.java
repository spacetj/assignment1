package com.AdvancedProgramming.Menus;

import com.AdvancedProgramming.MiniNet;
import com.AdvancedProgramming.States;
import com.AdvancedProgramming.UserService;
import com.AdvancedProgramming.UserStore;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static com.AdvancedProgramming.MiniNet.isInputInt;

/**
 * Menu is an interface which has the 2 actions required for any class to be a menu.
 *
 * @version 1.0.0 22nd March 2018
 * @author Tejas Cherukara
 */
public interface Menu {
    void getMenu();
    void doAction(Scanner input);

    String ACTION_NOT_FOUND = "Action Not Found, please retry with an option number listed above.";
    int BACK = 0;
    int HELP = -2;
    int EXIT = -1;
    UserStore userService = UserService.getInstance();

    /**
     * Common actions that are applicable to any menus.
     * @param action
     */
    default void defaultAction(int action) {
        switch(action){
            case EXIT:
                MiniNet.exit();
                break;
            case BACK:
                MiniNet.switchState(States.MAIN_MENU);
                break;
            case HELP:
                MiniNet.printHeader();
                break;
            default:
                System.out.println(ACTION_NOT_FOUND);
                break;
        }
    }

    /**
     * Checks if the user input is a special character.
     * @param value
     * @return
     */
    default boolean isSpecialInput(String value){
        List<Integer> specialInputs = Arrays.asList(BACK,EXIT,HELP);
        return isInputInt(value) && specialInputs.contains(Integer.parseInt(value));
    }

    /**
     * If it is a special input, do the action.
     * @param value
     */
    default void checkSpecialInput(String value){
        if (isSpecialInput(value)) {
            defaultAction(Integer.parseInt(value));
        }
    }
}
