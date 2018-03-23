package com.AdvancedProgramming;

import com.AdvancedProgramming.Menus.*;

import java.util.Scanner;

/**
 * MiniNet is the main class which controls the java social network.
 *
 * Run this class in order to start the command line social network.
 *
 * @version 1.0.0 22nd March 2018
 * @author Tejas Cherukara
 */

public class MiniNet {

    public final static String DIVIDER = "=========================";
    private static Menu menu;
    private static Scanner input;

    public static void main(String[] args) {

        menu = new MainMenu();
        input = new Scanner(System.in);

        printHeader();

        do{
            menu.getMenu();
            menu.doAction(input);
        } while(true);

    }

    /**
     * Switches the menu option.
     * @param state One of the states enum.
     */
    public static void switchState(States state){
        switch (state){
            case ADD_USER:
                menu = new AddUserMenu();
                break;
            case MAIN_MENU:
                menu = new MainMenu();
                break;
            case SELECT_USER:
                menu = new UserMenu();
                break;
        }
    }

    /**
     * Print the top menu panel of the application.
     */
    public static void printHeader() {
        System.out.println("Welcome to MiniNet, the next generation social media. Command line interfaces is where its at.\n");
        System.out.println("Useful Shortcuts:");
        System.out.println("1. To exit the application at any time: "+ MenuTemplate.EXIT);
        System.out.println("2. To go back a screen: "+ MenuTemplate.BACK);
        System.out.println("3. To view these help shortcuts: "+ MenuTemplate.HELP);
        System.out.println(DIVIDER);
        System.out.println();
    }

    /**
     * Exits the application with a done message.
     */
    public static void exit(){
        input.close();
        System.out.println("\nExiting now...");
        System.out.println("Thank you for using MiniNet.\n");
        System.exit(0);
    }

    /**
     * Checks if the input is an integer.
     * @param value
     * @return
     */
    public static boolean isInputInt(String value) {
        try{
            Integer.parseInt(value);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }
}

