/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.batchapi.controller;

import com.mycompany.batchapi.model.ErrorDto;
import com.mycompany.batchapi.model.PokemonDto;
import com.mycompany.batchapi.service.PokemonService;
import com.mycompany.batchapi.util.BackendResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author dperez
 */
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/pokemons")
public class PokemonController {

    @Inject
    private Logger logger;

    @Inject
    private PokemonService pokemonService;

    public PokemonController() {
    }

    @GET
    public Response getPokemons() {
        try {
            BackendResponse backendResponse = this.pokemonService.getPokemons();

            return Response.status(backendResponse.getHttpStatus())
                    .entity(backendResponse.getResult())
                    .build();

        } catch (Exception ex) {
            this.logger.log(Level.SEVERE, "An unexpected error occurred when trying to obtain the pokemons.", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorDto("An unexpected error occurred when trying to obtain the pokemons."))
                    .build();
        }
    }

    @POST
    public Response savePokemon(PokemonDto pokemonDto) {
        try {
            BackendResponse backendResponse = this.pokemonService.savePokemon(pokemonDto);

            return Response.status(backendResponse.getHttpStatus())
                    .entity(backendResponse.getResult())
                    .build();

        } catch (Exception ex) {
            this.logger.log(Level.SEVERE, "An unexpected error occurred when trying to save the pokemon.", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorDto("An unexpected error occurred when trying to save the pokemon."))
                    .build();
        }
    }

    @GET
    @Path("/createPokemonsInBatch/{totalPokemons}")
    public Response createPokemonsInBatch(@PathParam("totalPokemons") Integer totalPokemons) {
        try {
            BackendResponse backendResponse = this.pokemonService.createPokemonsInBatch(totalPokemons);

            return Response.status(backendResponse.getHttpStatus())
                    .entity(backendResponse.getResult())
                    .build();

        } catch (Exception ex) {
            this.logger.log(Level.SEVERE, "An unexpected error occurred when trying to generate the batch to generate the pokemon.", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorDto("An unexpected error occurred when trying to generate the batch to generate the pokemon."))
                    .build();
        }
    }
    
    @GET
    @Path("getCreatePokemonsBatchStatus/{jobExecutionId}")
    public Response getCreatePokemonsBatchStatus(@PathParam("jobExecutionId") Long jobExecutionId){
            try {
            BackendResponse backendResponse = this.pokemonService.getCreatePokemonsBatchStatus(jobExecutionId);

            return Response.status(backendResponse.getHttpStatus())
                    .entity(backendResponse.getResult())
                    .build();

        } catch (Exception ex) {
            this.logger.log(Level.SEVERE, "An unexpected error occurred when trying to generate the batch to generate the pokemon.", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorDto("An unexpected error occurred when trying to generate the batch to generate the pokemon."))
                    .build();
        }
    }

}
