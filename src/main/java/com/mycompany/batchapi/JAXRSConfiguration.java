/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.batchapi;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author dperez
 */
@ApplicationPath("/api")
public class JAXRSConfiguration extends Application {

    public JAXRSConfiguration() {
        super();
    }
    
}
