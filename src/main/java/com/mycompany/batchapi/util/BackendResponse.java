/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.batchapi.util;

import com.mycompany.batchapi.model.ErrorDto;
import javax.ws.rs.core.Response;

/**
 *
 * @author dperez
 */
public class BackendResponse {
 
    private Object result;
    private boolean error;
    private String internalMsg;
    private Response.Status httpStatus;

    private BackendResponse(Object result, boolean error, Response.Status httpStatus, String internalMsg) {
        this.result = result;
        this.error = error;
        this.httpStatus = httpStatus;
        this.internalMsg = internalMsg;
    }
    
    public static BackendResponse ok(Response.Status httpStatus) {
        return new BackendResponse(null, false, httpStatus, null);
    }

    public static BackendResponse ok(Object result, Response.Status httpStatus) {
        return new BackendResponse(result, false, httpStatus, null);
    }

    public static BackendResponse error(String msg, Response.Status httpStatus) {
        return new BackendResponse(new ErrorDto(msg), true, httpStatus, null);
    }

    public static BackendResponse error(Response.Status httpStatus, String msg, String internalMsg) {
        return new BackendResponse(new ErrorDto(msg), true, httpStatus, internalMsg);
    }

    public <T> T getResult() {
        return (T) result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public Response.Status getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(Response.Status httpStatus) {
        this.httpStatus = httpStatus;
    }

}
