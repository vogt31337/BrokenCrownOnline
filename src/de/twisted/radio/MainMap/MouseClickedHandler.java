/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.twisted.radio.MainMap;

import de.twisted.radio.DAOs.Region;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author mvogt
 */
public class MouseClickedHandler implements EventHandler<MouseEvent>{

    private final MainWindow mainWindow;    

    MouseClickedHandler(MainWindow aThis) {
        this.mainWindow = aThis;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        mainWindow.setMouseX(mouseEvent.getX());
        mainWindow.setMouseY(mouseEvent.getY());
        Region selectedRegion = mainWindow.getMainMap().selectNearestRegion(new Point2D(mouseEvent.getX(), mouseEvent.getY()));
        if (selectedRegion != null) {
            System.out.println(selectedRegion.toString());
        }
    }
}