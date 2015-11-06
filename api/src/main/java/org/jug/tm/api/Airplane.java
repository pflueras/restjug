/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jug.tm.api;

import java.util.ArrayList;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * If name is primary key then update is very cumbersome! Not a good idea.
 * @author pflueras
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Airplane {
    private String href;
    private String takeOffHref;
    
    private String name;
    private String status;
    private float weight;
    
    private int onboardPassanges;
    
    private Date createdAt;
    private Date updatedAt;
    
    private ArrayList<Wing> wings = new ArrayList<>();
    
    public Airplane() {
        // Java Bean.
    }

    public Airplane(String name, String status, float weight, int onboardPassanges,
            Date createdAt, Date updatedAt) {
        this.name = name;
        this.status = status;
        this.weight = weight;
        this.onboardPassanges = onboardPassanges;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getTakeOffHref() {
        return takeOffHref;
    }

    public void setTakeOffHref(String takeOffHref) {
        this.takeOffHref = takeOffHref;
    }
    
    public void setPrefixHref(String prefixHref) {
        this.href = prefixHref + "/" + name;
        this.takeOffHref = this.href + "/takeoff";
        
        for (Wing wing: wings) {
            wing.setPrefixHref(href + "/wings");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getOnboardPassanges() {
        return onboardPassanges;
    }

    public void setOnboardPassanges(int onboardPassanges) {
        this.onboardPassanges = onboardPassanges;
    }
    
    public void copyTo(Airplane another) {
        another.status = status;
        another.onboardPassanges = onboardPassanges;
        another.wings = wings;
    }

    public ArrayList<Wing> getWings() {
        return wings;
    }

    public void setWings(ArrayList<Wing> wings) {
        this.wings = wings;
    }
    
    /**
     * Add a new wing to the airplane.
     * @param wing 
     */
    public void addWing(Wing wing) {
        wings.add(wing);
    }
}
