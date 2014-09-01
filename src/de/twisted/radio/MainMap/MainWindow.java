/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.twisted.radio.MainMap;

import de.twisted.radio.DAOs.UserDAO;
import de.twisted.radio.MainApplication;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneBuilder;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author mvogt
 */
public class MainWindow {
    
    private double mouseX = 0.0;
    private double mouseY = 0.0;
    private CentralMap mm;
//    private MainApplication myApplication = null;

    public MainWindow(Stage primaryStage, UserDAO user/*, MainApplication myApp*/) {
//        this.myApplication = myApp;
        primaryStage.setResizable(true);

        final BorderPane border = new BorderPane();
        
        Scene scene = SceneBuilder.create()
                                .width(1024)
                                .height(768)
                                .root(border)
                                .build();
        
        primaryStage.setScene(scene);

        border.prefHeightProperty().bind(scene.heightProperty());
        border.prefWidthProperty().bind(scene.widthProperty());        

        /************************
         *  Border Pane Center  *
         ************************/
        StackPane stp = new StackPane();
        
        Group mainMapGroup = new Group();
        Group outerGroup = new Group(mainMapGroup);
        final ScrollPane scrollPane = new ScrollPane();
        
        scrollPane.setPannable(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
        scrollPane.addEventFilter(ScrollEvent.ANY, new ZoomHandler(mainMapGroup));
//        scrollPane.setOnMouseMoved(new OnMouseMovedHandler(this));
        scrollPane.setOnMouseClicked(new MouseClickedHandler(this));
        
        scrollPane.setContent(outerGroup);
        
        stp.getChildren().add(scrollPane);
        border.setCenter(stp);
        
        String mainMap = this.getClass().getClassLoader().getResource("resource/karte-Makrabar-vers2.jpg").getFile();
        try {
            mm = new CentralMap(mainMapGroup, new File(mainMap));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainApplication.class.getName()).log(Level.SEVERE, "Main map not found in the resource folder!", ex);
        }        
        
        /*********************
         *  Border Pane Top  *
         *********************/
        
        MainMenuBar menuBar = new MainMenuBar(user, mm);
        border.setTop(menuBar.getMenuBar());
        /*
        HBox lowerPanel = new HBox();
        Image i = null;
        try {
            i = new Image(new BufferedInputStream(new FileInputStream(new File(this.getClass().getClassLoader().getResource("resource/wappen_makrabar.png").getFile()))));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        ImageView mipmap = new ImageView(i);
        mipmap.scaleXProperty().set(0.5);
        mipmap.scaleYProperty().set(0.5);
        
        lowerPanel.getChildren().add(mipmap);
//        lowerPanel.setAlignment(Pos.BOTTOM_CENTER);
        lowerPanel.setMaxSize(mipmap.getFitWidth(), mipmap.getFitHeight());
        
        stp.getChildren().add(lowerPanel); */
    }
    
    public double getMouseX() {
        return mouseX;
    }

    public void setMouseX(double mouseX) {
        this.mouseX = mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }

    public void setMouseY(double mouseY) {
        this.mouseY = mouseY;
    }
    
    public CentralMap getMainMap() {
        return mm;
    }
}
