/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.twisted.radio.MainMap;

import de.twisted.radio.DAOs.Region;
import de.twisted.radio.DAOs.Unit;
import de.twisted.radio.PersistenceProvider;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author mvogt
 */
public class CentralMap {
    private boolean REGIONSELECTMODE = false;
    private List<Region> regions = null;
    private Group root = null;
    
    private ImageView iv = new ImageView();
    private double zoom = 1.0;
    public Point2D dragAnchor;
    private double dragX = 0.0f;
    private double dragY = 0.0f;
//    private final Rectangle2D standardView;
    private final Pane gamePane = new Pane();
    
    CentralMap(Group group, File map) throws FileNotFoundException {
        BufferedInputStream is = new BufferedInputStream(new FileInputStream(map));
        Image image = new Image(is);

        this.root = group;
        
        iv.setImage(image);
        iv.setPreserveRatio(true);
        iv.setSmooth(true);
        
        StackPane layout = new StackPane();
        layout.getChildren().add(iv);
        layout.getChildren().add(gamePane);
        root.getChildren().add(layout);
        updateRegions(null);

        Unit testKnight = new Unit("Knight", PersistenceProvider.getPersistenceProvider().admin, 1000);
        final ImageView ivKnight = testKnight.getImage(377.0, 173.0);
        
        ivKnight.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ivKnight.relocate(event.getSceneX(), event.getSceneY());
                event.consume();
            }
        });

        ivKnight.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Region reg = selectNearestRegion(new Point2D(event.getSceneX(), event.getSceneY()));
                ivKnight.relocate(reg.positionX, reg.positionY);
            }
        });
        
        gamePane.getChildren().add(ivKnight);
    }
    
    public ImageView getIvBackground() {
        return iv;
    }

    private void updateRegions(Region newRegion) {
        if (newRegion == null) {
            regions = PersistenceProvider.getPersistenceProvider().getRegions();
        } else {
            regions.add(newRegion);
        }
        
        for(Region r: regions) {
            try {
//                layout.getChildren().add(r.getImage());
                gamePane.getChildren().add(r.getImage());
            } catch (FileNotFoundException ex) {
                Logger.getLogger(CentralMap.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    EventHandler backupMouseClickedHandler;
    public void startRegionSelection() {
        gamePane.setCursor(Cursor.CROSSHAIR);
        REGIONSELECTMODE = true;
        backupMouseClickedHandler = gamePane.getOnMouseClicked();
        gamePane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                Point2D clickedPoint = new Point2D(me.getX(), me.getY());
                System.out.println(clickedPoint);
                Region newRegion = PersistenceProvider.getPersistenceProvider().addNewRegion(clickedPoint);
                // now we can start a window and configure the newRegion
                updateRegions(newRegion);
                stopRegionSelection();
            }
        });
    }

    void stopRegionSelection() {
        gamePane.setCursor(Cursor.DEFAULT);
        gamePane.setOnMouseClicked(backupMouseClickedHandler);
        REGIONSELECTMODE = false;
    }

    boolean isRegionSelectionActive() {
        return REGIONSELECTMODE;
    }

    public Region selectNearestRegion(final Point2D point2D) {
        Region retObj = null;
        Double selectedDistance = Double.MAX_VALUE;
        Double curDistance;
        
        if (regions.isEmpty()) {
            return null;
        }
        
        for (Region r: regions) {
            curDistance = r.getPosition().distance(point2D);
            if (Double.compare(curDistance, selectedDistance) < 0) {
                retObj = r;
                selectedDistance = curDistance;
            }
        }
        
        return retObj;
    }
}
