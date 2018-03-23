package com.AdvancedProgramming.Menus;

import com.AdvancedProgramming.MiniNet;
import com.AdvancedProgramming.States;
import com.AdvancedProgramming.UserService;
import com.AdvancedProgramming.UserStore;

import java.util.Arrays;
import java.util.List;

import static com.AdvancedProgramming.MiniNet.isInputInt;

/**
 * MenuTemplate has methods that apply to all menu.
 *
 * @version 1.0.0 22nd March 2018
 * @author Tejas Cherukara
 */
public abstract class MenuTemplate {

    private final static String ACTION_NOT_FOUND = "Action Not Found, please retry with an option number listed above.";
    public static final int BACK = 0;
    public static final int HELP = -2;
    public static final int EXIT = -1;
    protected UserStore userService = UserService.getInstance();

    /**
     * Common actions that are applicable to any menus.
     * @param action
     */
    protected void doAction(int action) {
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
    public boolean isSpecialInput(String value){
        List<Integer> specialInputs = Arrays.asList(MenuTemplate.BACK,MenuTemplate.EXIT,MenuTemplate.HELP);
        return isInputInt(value) && specialInputs.contains(Integer.parseInt(value));
    }

    /**
     * If it is a special input, do the action.
     * @param value
     */
    public void checkSpecialInput(String value){
        if (isSpecialInput(value)) {
            doAction(Integer.parseInt(value));
        }
    }

}
