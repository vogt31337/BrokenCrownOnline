/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.twisted.radio.login;

import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 *
 * @author mvogt
 */

public class LoginFXML {  
    private FXMLExampleController controller;

    public void start(Stage stage) throws Exception {
        URL location = getClass().getResource("fxml_example.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);

        Parent root = (Parent) fxmlLoader.load();
        controller = fxmlLoader.getController();
        controller.setStage(stage);
        
        stage.setTitle("Broken Crown Online Welcome");
        stage.setScene(new Scene(root, 300, 275));
        stage.setResizable(false);
        stage.showAndWait();
    }

    public String getUser() {
        return controller.getUser();
    }
}
