/*
 * Author: Sydney Norman
 * Project: Minesweeper
 * Date: October 1st, 2017
 */

import javax.swing.*;

public class MenuBar extends JMenuBar {

    // Menu Option Constants
    private final static String EXIT = "Exit";
    private final static String HELP = "Help";
    private final static String NEW = "New";
    private final static String BEGINNER = "Beginner";
    private final static String INTERMEDIATE = "Intermediate";
    private final static String EXPERT = "Expert";
    private final static String CUSTOM = "Custom";

    private JMenu gameMenu, helpMenu, setupMenu;
    private JMenuItem helpMenuItem, exitMenuItem, newMenuItem;
    private JMenuItem beginnerRadioMenuItem, intermediateRadioMenuItem, expertRadioMenuItem, customRadioMenuItem;

    MenuBar(Bombs.MenuHandler MH, Bombs.SetupMenuHandler SMH) {

        // Build the Game Menu
        gameMenu = new JMenu("Game");

        // Add New Menu Item
        newMenuItem = new JMenuItem(NEW);
        newMenuItem.addActionListener(MH);
        gameMenu.add(newMenuItem);

        // Add Setup Submenu
        setupMenu = new JMenu("Setup");

        // Add Setup Submenu Group
        ButtonGroup group = new ButtonGroup();
        beginnerRadioMenuItem = new JRadioButtonMenuItem(BEGINNER);
        intermediateRadioMenuItem = new JRadioButtonMenuItem(INTERMEDIATE);
        expertRadioMenuItem = new JRadioButtonMenuItem(EXPERT);
        customRadioMenuItem = new JRadioButtonMenuItem(CUSTOM);

        beginnerRadioMenuItem.addActionListener(SMH);
        intermediateRadioMenuItem.addActionListener(SMH);
        expertRadioMenuItem.addActionListener(SMH);
        customRadioMenuItem.addActionListener(SMH);

        intermediateRadioMenuItem.setSelected(true);

        group.add(beginnerRadioMenuItem);
        group.add(intermediateRadioMenuItem);
        group.add(expertRadioMenuItem);
        group.add(customRadioMenuItem);

        setupMenu.add(beginnerRadioMenuItem);
        setupMenu.add(intermediateRadioMenuItem);
        setupMenu.add(expertRadioMenuItem);
        setupMenu.add(customRadioMenuItem);
        gameMenu.add(setupMenu);

        gameMenu.addSeparator();

        // Add exit to submenu
        exitMenuItem = new JMenuItem(EXIT);
        exitMenuItem.addActionListener(MH);
        gameMenu.add(exitMenuItem);

        // Build the Help Menu
        helpMenu = new JMenu(HELP);

        // Add Help Menu Item
        helpMenuItem = new JMenuItem(HELP);
        helpMenuItem.addActionListener(MH);
        helpMenu.add(helpMenuItem);

        add(gameMenu);
        add(helpMenu);
    }


}