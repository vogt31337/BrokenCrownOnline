/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.twisted.radio.login;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
/**
 *
 * @author mvogt
 */
public class FXMLExampleController {
    private Properties properties = new Properties();
    private String user = null;
    private String userPW = null;
    private Stage stage = null;
    
    @FXML private Text actiontarget;
    
    @FXML private PasswordField passwordField;
    
    @FXML private TextField userNameField;
    
    @FXML protected void handleSubmitButtonAction(ActionEvent event) {
        //lookup username in properties and fetch stored password                
        String pwStored = properties.getProperty(userNameField.getText(), "");

        try {
            //check password
            //close window and start next one if pw is ok
            if (Password.check(passwordField.getText(), pwStored)) {
                actiontarget.setText("PW is correct!");
                actiontarget.setFill(Color.rgb(21, 117, 84));
                user = userNameField.getText();
                userPW = pwStored;
                stage.close();
            }
        } catch (Exception ex) {
            Logger.getLogger(LoginWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        //else fire red text
        actiontarget.setText("PW is incorrect!");
        actiontarget.setFill(Color.rgb(210, 39, 30));
    }

    @FXML protected void handleRegisterButtonAction(ActionEvent event) {
        
    }
    
    public void setActionTarget(String text) {
        actiontarget.setText(text);
    }
    
    public FXMLExampleController() {
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
    }

    public String getUser() {
        return user;
    }

    void setStage(Stage stage) {
        this.stage = stage;
    }
}
