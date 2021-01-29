/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.batchapi.model;

/**
 *
 * @author dperez
 */
public class BatchJobStatusDto {
    
    private int status;
    private String description;

    public BatchJobStatusDto() {
    }

    public BatchJobStatusDto(int status, String description) {
        this.status = status;
        this.description = description;
    }    

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
