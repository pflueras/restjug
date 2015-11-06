/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jug.tm.web;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executor;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author pflueras
 */
@ApplicationPath("rest")
public class JAXRSConfiguration extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        HashSet<Class<?>> classes = new HashSet<>();
        
        classes.add(AirplaneResource.class);
        classes.add(CustomMapper.class);
        
        return classes;
    }
}
