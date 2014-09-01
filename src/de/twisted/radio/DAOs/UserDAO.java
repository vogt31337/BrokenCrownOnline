/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.twisted.radio.DAOs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
/**
 *
 * @author mike
 */
public class UserDAO extends PersistableObject implements Serializable, Comparable<UserDAO> {
    public List<Region> ownedRegions = new ArrayList<>();
    public List<UserDAO> minions = new ArrayList<>();
    public List<Resource> resources = new ArrayList<>();
    public String pwStored;
    public String userName;
    public Image emblem = null;

    public List<Region> getOwnedRegions() {
        return ownedRegions;
    }

    public void setOwnedRegions(List<Region> ownedRegions) {
        this.ownedRegions = ownedRegions;
    }

    public List<UserDAO> getMinions() {
        return minions;
    }

    public void setMinions(List<UserDAO> minions) {
        this.minions = minions;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public Image getEmblem() {
        return emblem;
    }

    public void setEmblem(Image emblem) {
        this.emblem = emblem;
    }
    
    public UserDAO(String userName, String pwStored) {
        this.userName = userName;
        this.pwStored = pwStored;
    }

    public UserDAO() {}

    @Override
    public int compareTo(UserDAO o) {
        return this.userName.compareTo(o.getUserName());
    }

    @Override
    public String toString() {
        return userName;
    }
    
    public String getUserName() {
        return userName;
    }
        
}
