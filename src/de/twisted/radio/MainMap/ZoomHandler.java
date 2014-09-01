/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.twisted.radio.MainMap;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.ScrollEvent;

/**
 *
 * @author mvogt
 */
public class ZoomHandler implements EventHandler<ScrollEvent> {

    private static final double MAX_SCALE = 2.5d;
    private static final double MIN_SCALE = 1.0d;
    private Node nodeToZoom;

    public ZoomHandler(Node nodeToZoom) {
        this.nodeToZoom = nodeToZoom;
    }

    @Override
    public void handle(ScrollEvent scrollEvent) {
        if (scrollEvent.isControlDown()) {
            final double scale = calculateScale(scrollEvent);
            nodeToZoom.setScaleX(scale);
            nodeToZoom.setScaleY(scale);
//                scrollEvent.consume();
        }
    }

    private double calculateScale(ScrollEvent scrollEvent) {
        double scale = nodeToZoom.getScaleX() + scrollEvent.getDeltaY() / 100;

        if (scale <= MIN_SCALE) {
            scale = MIN_SCALE;
        } else if (scale >= MAX_SCALE) {
            scale = MAX_SCALE;
        }
        return scale;
    }
}
