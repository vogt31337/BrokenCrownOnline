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
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 *
 * @author mvogt
 */
public class Region extends PersistableObject implements Serializable, Comparable<Region> {
    public List<Unit> containedUnits = new LinkedList<>();
    public List<Resource> containedResourcess = new LinkedList<>();
    public List<Region> surroundingRegion = new LinkedList<>();

    public double positionX;
    public double positionY;
    public String name;
    public String emblem = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public List<Unit> getContainedUnits() {
        return containedUnits;
    }

    public void setContainedUnits(List<Unit> containedUnits) {
        this.containedUnits = containedUnits;
    }

    public List<Resource> getContainedResourcess() {
        return containedResourcess;
    }

    public void setContainedResourcess(List<Resource> containedResourcess) {
        this.containedResourcess = containedResourcess;
    }

    public List<Region> getSurroundingRegion() {
        return surroundingRegion;
    }

    public void setSurroundingRegion(List<Region> surroundingRegion) {
        this.surroundingRegion = surroundingRegion;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.positionX) + Objects.hashCode(this.positionY);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Region other = (Region) obj;
        if ((!Objects.equals(this.positionX, other.positionX)) &&
            (!Objects.equals(this.positionY, other.positionY))) {
            return false;
        }
        return true;
    }
    
    public Region(Point2D position) {
        this.positionX = position.getX();
        this.positionY = position.getY();
    }
    
    public Point2D getPosition() {
        return new Point2D(positionX, positionY);
    }
    
    public Region() {}
    
    @Override
    public int compareTo(Region o) {
        if (o.equals(this)) {
            return 0;
        }
        return (this.name.equals(o.getName())) ? 0: 1;
    }

    public void setImage(String pathToImage) {        
        emblem = pathToImage;
    }
    
    public ImageView getImage() throws FileNotFoundException {
        Image i = new Image(new BufferedInputStream(new FileInputStream(new File(this.getClass().getClassLoader().getResource(emblem).getFile()))));
        ImageView iView = new ImageView(i);
        iView.setX(positionX - i.getHeight() / 2);
        iView.setY(positionY - i.getWidth() / 2);
        
/*        Pane p = new Pane();
        p.getChildren().add(iView);
        return p;*/
        return iView;
    }
    
    @Override
    public String toString() {
        StringBuilder strB = new StringBuilder();
        strB.append(name);
        strB.append(" coord: ");
        strB.append(this.getPosition().toString());
        return strB.toString();
    }
}
