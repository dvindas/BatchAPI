/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.batchapi.service;

import com.mycompany.batchapi.model.BatchJobDto;
import com.mycompany.batchapi.model.BatchJobStatusDto;
import com.mycompany.batchapi.model.PokemonDto;
import com.mycompany.batchapi.util.BackendResponse;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.JobExecution;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

/**
 *
 * author dperez
 */
@ApplicationScoped
public class PokemonService {

    @Inject
    private Logger logger;

    private List<PokemonDto> pokemonsDto;

    public PokemonService() {
    }

    @PostConstruct
    public void init() {
        this.pokemonsDto = new LinkedList();
    }

    public BackendResponse getPokemons() {
        try {
            return BackendResponse.ok(this.pokemonsDto, Response.Status.OK);
        } catch (Exception ex) {
            this.logger.log(Level.SEVERE, "An unexpected error occurred when obtaining pokemon.", ex);
            return BackendResponse.error(Response.Status.INTERNAL_SERVER_ERROR,
                    "An unexpected error occurred when obtaining pokemon.", ex.getMessage());
        }
    }

    public BackendResponse savePokemon(PokemonDto pokemonDto) {
        try {
            if (pokemonDto.getId() == null || pokemonDto.getId().isEmpty()) {
                pokemonDto.setId(UUID.randomUUID().toString());
                this.pokemonsDto.add(pokemonDto);
                return BackendResponse.ok(pokemonDto, Response.Status.CREATED);
            } else {
                Optional<PokemonDto> optPokemon = pokemonsDto.stream()
                        .filter(p -> p.getId().equals(pokemonDto.getId()))
                        .findAny();

                if (optPokemon.isPresent()) {
                    this.pokemonsDto.set(this.pokemonsDto.indexOf(optPokemon.get()), pokemonDto);
                    return BackendResponse.ok(pokemonDto, Response.Status.OK);
                } else {
                    return BackendResponse.error("The Pokemon you are trying to update does not exist.", Response.Status.NOT_FOUND);
                }
            }
        } catch (Exception ex) {
            this.logger.log(Level.SEVERE, "An unexpected error occurred while saving the Pokemon.", ex);
            return BackendResponse.error("An unexpected error occurred while saving the Pokemon.", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    public BackendResponse createPokemonsInBatch(Integer totalPokemons) {
        try {
            Properties properties = new Properties();
            properties.setProperty("totalPokemons", totalPokemons.toString());

            BatchJobDto batchJobDto = new BatchJobDto();
            batchJobDto.setName("PokemonCreatorBatch");

            JobOperator operator = BatchRuntime.getJobOperator();
            batchJobDto.setId(operator.start("PokemonCreatorBatch", properties));

            return BackendResponse.ok(batchJobDto, Response.Status.OK);

        } catch (Exception ex) {
            this.logger.log(Level.SEVERE, "An unexpected error occurred while creating the batch to generate pokemons.", ex);
            return BackendResponse.error("An unexpected error occurred while creating the batch to generate pokemons.", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    public BackendResponse getCreatePokemonsBatchStatus(Long jobExecutionId) {
        try {
            JobExecution jobExecution = BatchRuntime.getJobOperator().getJobExecution(jobExecutionId);
            BatchJobStatusDto batchJobStatusDto = new BatchJobStatusDto();

            switch (jobExecution.getBatchStatus()) {
                case STARTING:
                case STARTED:
                    batchJobStatusDto.setStatus(1);
                    batchJobStatusDto.setDescription("The job has started or is starting.");
                    break;
                case STOPPING:
                case STOPPED:
                    batchJobStatusDto.setStatus(2);
                    batchJobStatusDto.setDescription("The job has stopped or is stopping.");
                    break;
                case FAILED:
                case ABANDONED:
                    batchJobStatusDto.setStatus(3);
                    batchJobStatusDto.setDescription("The job has been abandoned or failed.");
                    break;
                case COMPLETED:
                    batchJobStatusDto.setStatus(4);
                    batchJobStatusDto.setDescription("The job has completed.");
                    break;
                default:
                    batchJobStatusDto.setStatus(5);
                    batchJobStatusDto.setDescription("The job has an unknown status.");
                    break;
            }

            return BackendResponse.ok(batchJobStatusDto, Response.Status.OK);

        } catch (Exception ex) {
            this.logger.log(Level.SEVERE, "An unexpected error occurred while querying the batch status to generate pokemons.", ex);
            return BackendResponse.error("An unexpected error occurred while querying the batch status to generate pokemons.", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

}
