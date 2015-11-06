/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jug.tm.web;

import java.net.URI;
import java.util.List;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.jug.tm.api.Airplane;

/**
 *
 * @author pflueras
 */
@Path("airplanes")
public class AirplaneResource {
    
    @Inject
    private AirplaneRepo repo;
    
    @Context
    private UriInfo uriInfo;
    
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Airplane> getAirplanes() {
        List<Airplane> airplanes = repo.getAirplanes();
        
        for (Airplane airplane: airplanes) {
            String path = uriInfo.getAbsolutePath().toString();
            airplane.setPrefixHref(path);
        }
        
        return airplanes;
    }
    
    @GET()
    @Path("{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAirplane(@PathParam("name")String name) {
        Airplane found = repo.findByName(name);
        
        if (found == null) {
            Response.ResponseBuilder builder = Response.status(Response.Status.NOT_FOUND);
            
            builder.header("Content-Type", MediaType.APPLICATION_JSON);
            builder.entity("Airplane " + name + " not found!");
            
            return builder.build();
        } else {
            Response.ResponseBuilder builder = Response.status(Response.Status.OK);
            String path = uriInfo.getAbsolutePath().toString();
            
            found.setPrefixHref(path);
            builder.header("Content-Type", MediaType.APPLICATION_JSON);
            builder.entity(found);
            
            return builder.build();
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addAirplane(Airplane airplane) {
        Airplane newAirplane = repo.addAirplane(airplane);
        
        if (newAirplane == null) {
            Response.ResponseBuilder builder = Response.status(Response.Status.CONFLICT);
            
            builder.header("Content-Type", MediaType.APPLICATION_JSON);
            builder.entity("Airplane " + airplane.getName() + " is already in repo!");
            
            return builder.build();
        } else {
            Response.ResponseBuilder builder = Response.status(Response.Status.CREATED);
            
            newAirplane.setPrefixHref(uriInfo.getAbsolutePath().toString());
            
            builder.header("Content-Type", MediaType.APPLICATION_JSON);
            builder.location(URI.create(newAirplane.getHref()));
            builder.entity(newAirplane);
            
            return builder.build();
        }
    }
    
    @PUT
    @Path("{name}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAirplane(@PathParam("name")String name, Airplane airplane) {
        Airplane found = repo.findByName(name);
        
        if (found == null) {
            Response.ResponseBuilder builder = Response.status(Response.Status.NOT_FOUND);
            
            builder.header("Content-Type", MediaType.APPLICATION_JSON);
            builder.entity("Airplane " + airplane.getName() + " not found!");
            
            return builder.build();
        } else {
            Airplane updatedAirplane = repo.updateAirplane(airplane);
            Response.ResponseBuilder builder = Response.status(Response.Status.OK);
            
            updatedAirplane.setPrefixHref(uriInfo.getAbsolutePath().toString());
            builder.header("Content-Type", MediaType.APPLICATION_JSON);
            builder.entity(updatedAirplane);
            
            return builder.build();
        }
    }
    
    @DELETE
    @Path("{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response delete(@PathParam("name")String name) {
        Airplane airplane = repo.findByName(name);
        
        if (airplane == null) {
            // NOT FOUND
            Response.ResponseBuilder builder = Response.status(Response.Status.NOT_FOUND);
            
            builder.header("Content-Type", MediaType.TEXT_PLAIN);
            builder.entity("Airplane " + name + " not found!");
            
            return builder.build();
        } else {
            // SUCCESS
            repo.delete(name);
            Response.ResponseBuilder builder = Response.status(Response.Status.OK);
            
            builder.header("Content-Type", MediaType.TEXT_PLAIN);
            builder.entity("Airplane " + name + " has been deleted!");
            
            return builder.build();
        }
    }
    
    @GET
    @Path("dummy")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject dummy() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        
        builder.add("name", "DEMO Airplane");
        builder.add("status", "DEMO");
        
        return builder.build();
    }
    
    @GET
    @Path("default")
    @Produces(MediaType.APPLICATION_JSON)
    public Response restoreDefault() {
        repo.reset();
        
        Response.ResponseBuilder builder = Response.status(Response.Status.OK);
        builder.header("Content-Type", MediaType.APPLICATION_JSON);
        builder.entity("Airplane repository restored to default!");
        
        return builder.build();
    }
    
    @GET
    @Path("error")
    @Produces(MediaType.TEXT_PLAIN)
    public Response error() throws CustomException {
        repo.generateError();
        Response.ResponseBuilder builder = Response.status(Response.Status.OK);
        
        builder.header("Content-Type", MediaType.TEXT_PLAIN);
        builder.entity("This should not be reached!");
        
        return builder.build();
    }
}
