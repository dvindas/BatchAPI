/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.batchapi.batch;

import com.mycompany.batchapi.model.PokemonDto;
import com.mycompany.batchapi.service.PokemonService;
import com.mycompany.batchapi.util.BackendResponse;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.batch.api.Batchlet;
import javax.batch.runtime.context.JobContext;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author dperez
 */
@Dependent
@Named("PokemonCreatorBatch")
public class PokemonCreatorBatch implements Batchlet {

    private static final Logger logger = Logger.getLogger(PokemonCreatorBatch.class.getName());
    
    @Inject
    private PokemonService pokemonService;

    @Inject
    private JobContext jobContext;

    @Override
    public String process() throws Exception {
        Integer totalPokemonsForCreate = Integer.valueOf(jobContext.getProperties().getProperty("totalPokemons"));
        
        BackendResponse backendResponse = pokemonService.getPokemons();

        if (!backendResponse.isError()) {
            int totalPokemons = ((List<PokemonDto>) backendResponse.getResult()).size();

            for (int i = totalPokemons; i < (totalPokemons + totalPokemonsForCreate); i++) {
                Thread.sleep(5000);
                logger.log(Level.INFO, "Creando Pokemon {0}", String.valueOf(i));
                this.pokemonService.savePokemon(new PokemonDto(null, "Pokemon " + String.valueOf(i), "Tipo " + String.valueOf(i)));
            }
        }

        return "COMPLETED";
    }

    @Override
    public void stop() throws Exception {
      
    }
  
}
