/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.twisted.radio.login;

import de.twisted.radio.MainApplication;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author mvogt
 */
public class LoginWindow {
//    private Preferences preferences = Preferences.userNodeForPackage(LoginWindow.class);
    private Properties properties = new Properties();
//    private Password passwordCheck = new Password();
    private String user = null;
    private String userPW = null;
    
    private MainApplication myApplication = null;
    private Group root = new Group();
    private final double WIDTH = 130;
    private final double HEIGHT = 100;
    
    LoginWindow(final Stage primaryStage, final MainApplication myApp) throws Exception {
        String pwProp = this.getClass().getClassLoader().getResource("resource/password.properties").getFile();
        File pwFile = new File(pwProp);
        try {
            properties.loadFromXML(new BufferedInputStream(new FileInputStream(pwFile)));
        } catch (Exception ex) { // create admin account...
            Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
            try {
                pwFile.createNewFile();
                String pw = Password.getSaltedHash("admin");
                properties.setProperty("admin", pw);
                properties.storeToXML(new BufferedOutputStream(new FileOutputStream(pwFile)), null);
            } catch (Exception ex1) {
                Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        
        // init stuff
        this.myApplication = myApp;
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        
        // create view and window layout
        BorderPane borderPane = new BorderPane();
        
        // create username and password fields
        final TextField userName = new TextField("Username");
        final PasswordField password = new PasswordField();
        password.setPromptText("Password");
        userName.setMaxSize(100, 30);
        password.setMaxSize(100, 30);
        
        // create user response message
        final Label message = new Label();
        
        // add stuff these fields do when you click on them
        userName.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                userName.clear();
            }
        });

        // add stuff these fields do when you click on them
        password.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                password.clear();
            }
        });        
        
        Button login = new Button("Login");
        Button newUser = new Button("New");
        HBox buttons = new HBox(10);
        buttons.getChildren().addAll(login, newUser);
        
        // login button action, most important!
        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                //lookup username in properties and fetch stored password                
                String pwStored = properties.getProperty(userName.getText(), "");
                
                try {
                    //check password
                    //close window and start next one if pw is ok
                    if (Password.check(password.getText(), pwStored)) {
                        message.setText("PW is correct!");
                        message.setTextFill(Color.rgb(21, 117, 84));
//                        user = new UserDAO(userName.getText(), pwStored);
                        user = userName.getText();
                        userPW = pwStored;
                        primaryStage.close();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                //else fire red text
                message.setText("PW is incorrect!");
                message.setTextFill(Color.rgb(210, 39, 30));          
            }
        });

        AnchorPane ap = new AnchorPane();
        ap.getChildren().addAll(userName, password, buttons, message);
        
        ap.setTopAnchor(userName, 10.0);
        ap.setLeftAnchor(userName, 20.0);
        ap.setTopAnchor(password, 35.0);
        ap.setLeftAnchor(password, 20.0);
        ap.setTopAnchor(buttons, 60.0);
        ap.setLeftAnchor(buttons, 18.0);        
        ap.setTopAnchor(message, 85.0);
        ap.setLeftAnchor(message, 20.0);
        
        borderPane.setCenter(ap);
        
        root.getChildren().add(borderPane);
    }
    
    public double getSampleWidth() { return WIDTH; }
 
    public double getSampleHeight() { return HEIGHT; }

    public String getUser() {
        return user;
    }

    public String getUserPW() {
        return userPW;
    }
 
}
