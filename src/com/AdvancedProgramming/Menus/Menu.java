package com.AdvancedProgramming.Menus;

import java.util.Scanner;

/**
 * Menu is an interface which has the 2 actions required for any class to be a menu.
 *
 * @version 1.0.0 22nd March 2018
 * @author Tejas Cherukara
 */
public interface Menu {
    void getMenu();
    void doAction(Scanner input);
}
