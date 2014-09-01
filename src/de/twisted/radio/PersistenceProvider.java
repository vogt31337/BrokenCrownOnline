/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.twisted.radio;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;
import de.twisted.radio.DAOs.Region;
import de.twisted.radio.DAOs.UserDAO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Point2D;

/**
 *
 * @author mvogt
 */
public class PersistenceProvider {

    public UserDAO admin = null;
    private String pinImage = "resource/pin_yellow.png";
    private String userPath = this.getClass().getClassLoader().getResource("resource/").getFile();
    private static PersistenceProvider persistenceProvider = null;

    public static PersistenceProvider getPersistenceProvider() {
        if (persistenceProvider == null) {
            persistenceProvider = new PersistenceProvider();
        }
        return persistenceProvider;
    }

    public PersistenceProvider() throws NullPointerException {
    }

    public void saveUser(UserDAO user) {
        try {
            try {
                YamlWriter userWriter = new YamlWriter(new FileWriter(userPath + user.userName + ".yml"));
                userWriter.write(user);
                userWriter.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(PersistenceProvider.class.getName()).log(Level.SEVERE, null, ex);
            } catch (YamlException ye) {
                Logger.getLogger(PersistenceProvider.class.getName()).log(Level.SEVERE, "couldn't load user: " + user.userName, ye);
            } catch (IOException ie) {
                Logger.getLogger(PersistenceProvider.class.getName()).log(Level.SEVERE, "error writing to disk user: " + user.userName, ie);
            }
        } catch (NullPointerException npe) {
            Logger.getLogger(PersistenceProvider.class.getName()).log(Level.SEVERE, "users.yml not found.", npe);
        }
    }

    public UserDAO loadUser(final String name) {
        if ("admin".equals(name)) {
            File f = new File(userPath + "/admin.yml");
            if (!f.exists()) {
                admin = new UserDAO("admin", "");
                saveUser(admin);
                return admin;
            }
        }

        UserDAO user = null;
        try {
            YamlReader userReader = new YamlReader(new FileReader(userPath + name + ".yml"));
            user = userReader.read(UserDAO.class);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PersistenceProvider.class.getName()).log(Level.SEVERE, null, ex);
        } catch (YamlException ye) {
            Logger.getLogger(PersistenceProvider.class.getName()).log(Level.SEVERE, "couldn't load user: " + name, ye);
        }

        if (user.userName.equals("admin")) {
            admin = user;
        }
        
        return user;
    }

    /*
     * This function will create a new Region
     * and add it to the region list, the admin user
     * and it will return the new region.
     * @param position the coordinates of the center
     */
    public Region addNewRegion(Point2D position) {
        Region region = new Region(position);
        region.setImage(pinImage);
        admin.ownedRegions.add(region);
        return region;
    }

    public List<Region> getRegions() {
        return admin.ownedRegions;
    }
}
