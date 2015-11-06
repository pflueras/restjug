/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jug.tm.st;

import java.util.ArrayList;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import junit.framework.Assert;
import org.jug.tm.api.Airplane;
import org.jug.tm.api.Wing;
import org.junit.Test;

/**
 *
 * @author pflueras
 */
public class AirplaneIT {
    private static final String rootURI = "http://localhost:8080/restjug/rest";
    //private static final String rootURI = "http://192.168.1.2:8080/restjug/rest";
    
    private Airplane addAirplane(Airplane airplane) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(rootURI + "/airplanes");
        
        Response response = target.request().post(Entity.entity(airplane, MediaType.APPLICATION_JSON));
        Airplane updated = response.readEntity(Airplane.class);
        
        response.close();
        
        Assert.assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        
        return updated;
    }
    
    private Airplane updateAirplane(Airplane airplane) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(airplane.getHref());
        
        Response response = target.request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(airplane, MediaType.APPLICATION_JSON));
        Airplane updated = response.readEntity(Airplane.class);
        
        response.close();
        
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        
        return updated;
    }
    
    private void resetRepo() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(rootURI + "/airplanes/default");
        
        Response response = target.request(MediaType.APPLICATION_JSON).get();
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        
        response.close();
    }
    
    private void checkInitialRepo() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(rootURI + "/airplanes/");
        
        Response response = target.request(MediaType.APPLICATION_JSON).get();
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        
        ArrayList<Airplane> airplanes = response.readEntity(new GenericType<ArrayList<Airplane>>(){});
        Assert.assertEquals(2, airplanes.size());
        
        // We may test here also the content of the airplanes.
        
        response.close();
    }
    
    private void fillInFuel() {
        int maxFuelCapacity = 500;
        
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(rootURI + "/airplanes");
        
        Response response = target.request(MediaType.APPLICATION_JSON).get();
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        
        ArrayList<Airplane> airplanes = response.readEntity(new GenericType<ArrayList<Airplane>>(){});
        
        for (Airplane airplane: airplanes) {
            for (Wing wing: airplane.getWings()) {
                wing.setFuelQuantity(maxFuelCapacity);
            }
            
            Airplane updated = updateAirplane(airplane);
            for (Wing updatedWing: updated.getWings()) {
                Assert.assertEquals(maxFuelCapacity, updatedWing.getFuelQuantity());
            }
        }
        
        response.close();
    }
    
    private void boardPassangers() {
        int maxPassangersCapacity = 150;
        
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(rootURI + "/airplanes");
        
        Response response = target.request(MediaType.APPLICATION_JSON).get();
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        
        ArrayList<Airplane> airplanes = response.readEntity(new GenericType<ArrayList<Airplane>>(){});
        
        for (Airplane airplane: airplanes) {
            airplane.setOnboardPassanges(maxPassangersCapacity);
            Airplane updated = updateAirplane(airplane);
            Assert.assertEquals(maxPassangersCapacity, updated.getOnboardPassanges());
        }
        
        response.close();
    }
    
    
    @Test
    public void testAirplanes() {
        long start = System.currentTimeMillis();
        
        resetRepo();
        checkInitialRepo();
        fillInFuel();
        boardPassangers();
        
        long end = System.currentTimeMillis();
        System.out.println("Airplanes preparation took: " + (end - start) + " miliseconds." );
    }
}
