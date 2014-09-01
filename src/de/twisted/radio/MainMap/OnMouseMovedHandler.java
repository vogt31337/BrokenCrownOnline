/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.twisted.radio.MainMap;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author mvogt
 */
public class OnMouseMovedHandler implements EventHandler<MouseEvent>{

    private final MainWindow mainWindow;
    
    public OnMouseMovedHandler(MainWindow aThis) {
        this.mainWindow = aThis;
    }
    
    @Override
    public void handle(MouseEvent mouseEvent) {
        mainWindow.setMouseX(mouseEvent.getX());
        mainWindow.setMouseY(mouseEvent.getY());
    }
}
