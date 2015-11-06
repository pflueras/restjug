/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jug.tm.api;

import java.util.UUID;

/**
 *
 * @author pflueras
 */
public class Wing {
    private String href;
    
    private String uuid;
    
    /**
     * Specified in liters.
     */
    private int fuelQuantity;
    private float length;

    public Wing() {
        // Java Bean
        pickUUID();
    }

    public Wing(int fuelQuantity, float length) {
        this();
        
        this.fuelQuantity = fuelQuantity;
        this.length = length;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
    
    public void setPrefixHref(String prefixHref) {
        this.href = prefixHref + "/" + uuid;
    }

    public int getFuelQuantity() {
        return fuelQuantity;
    }

    public void setFuelQuantity(int fuelQuantity) {
        this.fuelQuantity = fuelQuantity;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }
    
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    
    public final void pickUUID() {
        uuid = UUID.randomUUID().toString();
    }
}
