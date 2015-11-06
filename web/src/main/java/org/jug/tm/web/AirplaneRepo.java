/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jug.tm.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Singleton;
import org.jug.tm.api.Airplane;
import org.jug.tm.api.Wing;

/**
 *
 * @author pflueras
 */
@Singleton
public class AirplaneRepo {
    private ArrayList<Airplane> airplanes = new ArrayList<>();
    
    @PostConstruct
    public void initialize() {
        Airplane airplane;
        airplanes = new ArrayList<>();
        
        airplane = new Airplane("Airbus380", "Hangar", 135.3f, 0, new Date(), new Date());
        airplane.addWing(new Wing(75, 25.5f));
        airplane.addWing(new Wing(45, 25.5f));
        airplanes.add(airplane);
        
        airplane = new Airplane("Airbus390", "Hangar", 136.8f, 0, new Date(), new Date());
        airplane.addWing(new Wing(65, 26f));
        airplane.addWing(new Wing(58, 26f));
        airplanes.add(airplane);
    }
    
    /**
     * 
     * @return 
     */
    public List<Airplane> getAirplanes() {
        return airplanes;
    }
    
    /**
     * Find airplane by name.
     * @param name
     * @return null if no airplane has been found.
     */
    public Airplane findByName(String name) {
        for (Airplane airplane: airplanes) {
            if (name.equals(airplane.getName())) {
                return airplane;
            }
        }
        
        return null;
    }
    
    /**
     * 
     * @param airplane
     * @return null if the airplane is already present in the repo.
     */
    public Airplane addAirplane(Airplane airplane) {
        String name = airplane.getName();
        
        if (name == null || name.isEmpty()) {
            return null;
        }
        
        if (findByName(name) == null) {
            airplane.setCreatedAt(new Date());
            airplane.setUpdatedAt(new Date());
            airplanes.add(airplane);
            
            return airplane;
        } else {
            return null;
        }
    }
    
    /**
     * 
     * @param airplane null if the airplane name belongs to another plane.
     * @return 
     */
    public Airplane updateAirplane(Airplane airplane) {
        String name = airplane.getName();
        
        if (name == null || name.isEmpty()) {
            return null;
        }
        
        Airplane localAirplane = findByName(name);
        if (localAirplane == null) {
            return null;
        } else {
            airplane.copyTo(localAirplane);
            localAirplane.setUpdatedAt(new Date());
            
            return localAirplane;
        }
    }
    
    /**
     * Remove the specified airplane from repo.
     * @param airplaneName 
     */
    public void delete(String airplaneName) {
        Airplane airplane = findByName(airplaneName);
        
        if (airplane != null) {
            airplanes.remove(airplane);
        }
    }
    
    /**
     * Reset the list of airplanes from the repository.
     */
    public void reset() {
        initialize();
    }
    
    /**
     * Generate an Exception for demo purposes.
     * @throws CustomException 
     */
    public void generateError() throws CustomException {
        throw new CustomException("Custom exception has been reached!");
    }
}
