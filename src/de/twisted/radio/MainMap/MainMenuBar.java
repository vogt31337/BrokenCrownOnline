/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.twisted.radio.MainMap;

import de.twisted.radio.DAOs.UserDAO;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuBuilder;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuItemBuilder;

/**
 *
 * @author mvogt
 */
public class MainMenuBar {

    private final MenuBar menuBar = new MenuBar();
    protected final CentralMap centralMap;

    public MainMenuBar(UserDAO user, CentralMap mm) {
        this.centralMap = mm;
//        final MenuBar menuBar = new MenuBar();
        // --- Menu File
        Menu menuFile = new Menu("File");

        // --- Menu Edit
        Menu menuEdit = new Menu("Edit");

        // --- Menu View
        Menu menuView = new Menu("View");

        menuBar.getMenus().addAll(menuFile, menuEdit, menuView);

        if ("admin".equals(user.getUserName())) {
            // --- Menu Region            
            final MenuItem menuNewRegion = MenuItemBuilder.create().text("New").build();

            menuNewRegion.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    if (centralMap.isRegionSelectionActive()) {
                        centralMap.stopRegionSelection();
                    } else {
                        centralMap.startRegionSelection();
                    }
                }
            });
            MenuItem menuRegion = MenuBuilder.create().text("Region").items(menuNewRegion).build();
            
            // --- Menu Unit
            MenuItem menuUnit = MenuBuilder.create().text("Unit").build();

            // --- Menu Resource
            MenuItem menuResource = MenuBuilder.create().text("Resource").build();

            // --- Menu User
            MenuItem menuUser = MenuBuilder.create().text("User").build();

            Menu menuAdmin = MenuBuilder.create().text("Admin").items(menuRegion, menuUnit, menuResource).build();

            menuBar.getMenus().add(menuAdmin);
        }
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }
}
