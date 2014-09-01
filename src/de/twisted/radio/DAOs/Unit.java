/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.twisted.radio.DAOs;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.animation.TranslateTransitionBuilder;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 *
 * @author mvogt
 */
public class Unit extends PersistableObject implements Serializable, Comparable<Unit> {
    public String name;
    public UserDAO owner;
    public int count = 0;
    public String emblem = "resource/knight.png";
    public Region currentRegion = null;
    private TranslateTransition translateTransition;

    public Unit(String name, UserDAO owner, int count) {
        this.name = name;
        this.owner = owner;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    private void doTranslation(Point2D oldPos, Point2D newPos, ImageView iv) {
        translateTransition = new TranslateTransition(Duration.seconds(1), iv);
        translateTransition.setFromX(oldPos.getX());
        translateTransition.setFromY(oldPos.getY());
        translateTransition.setToX(newPos.getX());
        translateTransition.setToY(newPos.getY());
        translateTransition.setCycleCount(Timeline.INDEFINITE);
        translateTransition.setAutoReverse(true);
        translateTransition = TranslateTransitionBuilder.create()
                .duration(Duration.seconds(4))
                .node(iv)
                .fromX(20)
                .toX(380)
                .cycleCount(Timeline.INDEFINITE)
                .autoReverse(true)
                .build();
    }

    public void changeRegion(Region newRegion) {
        ImageView iv = getImage(this.currentRegion.positionX, this.currentRegion.positionY);
        Point2D oldPos = new Point2D(iv.getX(), iv.getY());
        doTranslation(oldPos, newRegion.getPosition(), iv);
        currentRegion = newRegion;
        translateTransition.play();
    }

    public void setImage(String pathToImage) {
        emblem = pathToImage;
    }

    public ImageView getImage(double positionX, double positionY) {
        try {
            Image i = new Image(new BufferedInputStream(new FileInputStream(new File(this.getClass().getClassLoader().getResource(emblem).getFile()))));
            ImageView iView = new ImageView(i);
            iView.setX(positionX - i.getHeight() / 2);
            iView.setY(positionY - i.getWidth() / 2);
            return iView;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Unit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public int compareTo(Unit o) {
        if (this.equals(o)) {
            return 0;
        }
        int comparison = this.name.compareTo(o.getName());
        if (comparison != 0) {
            return comparison;
        } else {
            int sign = this.count - o.getCount();
            return (sign == 0) ? 0 : (sign > 0) ? 1 : -1;
        }
    }
}
