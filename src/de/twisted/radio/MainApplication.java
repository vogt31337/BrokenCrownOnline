package de.twisted.radio;
/**
 * Copyright (c) 2008, 2012 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 */
import de.twisted.radio.MainMap.MainWindow;
import de.twisted.radio.login.LoginWindow;
import de.twisted.radio.DAOs.UserDAO;
import de.twisted.radio.login.LoginFXML;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * A sample that demonstrates various mouse and scroll events and their usage.
 * Click the circles and drag them across the screen. Scroll the whole screen.
 * All events are logged to the console.
 *
 * @see javafx.scene.Cursor
 * @see javafx.scene.input.MouseEvent
 * @see javafx.event.EventHandler
 */
public class MainApplication extends Application {
    
    private MainWindow mainWindow = null;
    private LoginWindow loginWindow = null;

    @Override 
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Broken Crown Online");

        Stage loginStage = new Stage();
        LoginFXML loginFancy = new LoginFXML();
        loginFancy.start(loginStage);
        
        if (loginFancy.getUser() != null) {
            UserDAO loggedUser = PersistenceProvider.getPersistenceProvider().loadUser(loginFancy.getUser());
            mainWindow = new MainWindow(primaryStage, loggedUser/*, this*/);
            primaryStage.show();
        }
        System.out.println("Exiting...");
    }
    public static void main(String[] args) {
        launch(args); 
    }
    
    @Override
    public void stop() throws IOException {
//        PersistenceProvider.getPersistenceProvider().stop();
    }    
}
