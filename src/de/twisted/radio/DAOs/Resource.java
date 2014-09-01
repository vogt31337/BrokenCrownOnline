/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.twisted.radio.DAOs;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mike
 */
class Resource extends PersistableObject implements Comparable<Resource>, Serializable {

    @Override
    public final int hashCode() {
        int hash = 7;
        hash = 97 * hash + (int) (this.creationDate ^ (this.creationDate >>> 32));
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.value) ^ (Double.doubleToLongBits(this.value) >>> 32));
        hash = 97 * hash + this.count;
        hash = 97 * hash + Objects.hashCode(this.name);
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
        final Resource other = (Resource) obj;
        if (this.creationDate != other.creationDate) {
            return false;
        }
        if (Double.doubleToLongBits(this.value) != Double.doubleToLongBits(other.value)) {
            return false;
        }
        if (this.count != other.count) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Resource{" + "creationDate=" + creationDate + ", value=" + value + ", count=" + count + ", name=" + name + ", hash=" + hashCode() +'}';
    }
    
    /**
     * Calculate a sha1 hash of this object based on the toString function.
     * @return hashcode
     */
    public byte[] secureHashCode() {
        byte[] retArray = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA");
            digest.update(this.toString().getBytes("ASCII"));
            retArray = digest.digest();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(Resource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retArray;
    }
    
    /**
     * Compares a given hashcode to the hashcode of this object.
     * @param hashcode
     * @return true if hashcode equals hashcode of this object
     */
    public boolean secureHashCompare(byte[] hashcode) {
        return Arrays.equals(hashcode, this.secureHashCode());
    }

    /**
     * Timestamp of creation
     */
    public long creationDate = 0;
    /**
     * Price for all resources of this type
     */
    public double value = 0.0;
    
    /**
     * number of owned resources e.g. 1500 Logs of wood
     */
    public int count = 0;
    
    /**
     * the name of the resource, maybe an Enum?
     */
    public final String name;
    
    /**
     * Represents the owner of the resource.
     */
//    public UserDAO owner;
    
    public double getPricePerItem() {
        return value / count;
    }
    
    public Resource(String name, int count, double value/*, UserDAO owner*/) {
        this.creationDate = System.currentTimeMillis();
        this.name = name;
        this.count = count;
        this.value = value;
//        this.owner = owner;
    }
    
    public Resource(final String fromString) {
        String tmpStr = fromString.substring(9, fromString.length()-1);
        String[] strings = tmpStr.split(", ");
        
        this.creationDate = Long.getLong(strings[0].split("=")[1]);
        this.value = Double.valueOf(strings[1].split("=")[1]);
        this.count = Integer.getInteger(strings[2].split("=")[1]);
        this.name = strings[3].split("=")[1];
        
        if (this.hashCode() != Integer.getInteger(strings[4].split("=")[1])) {
            throw new InternalError("Hashcode was wrong! Maybe a typo or a hacked object?");
        }        
    }
    
    @Override
    public int compareTo(Resource o) {
        if (o.equals(this)) {
            return 0;
        } else if (o.getPricePerItem() > this.getPricePerItem()) {
            return 1;
        } 
        return -1;
    }
    
}
