package com.ecommerce.auth_service.dto.error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorVM {

    @JsonProperty("status")
    private int status;

    @JsonProperty("error")
    private String error;

    @JsonProperty("message")
    private Object message;

    @JsonProperty("path")
    private String path;

    @JsonProperty("data")
    private String data;

    public ErrorVM() {
    }

    public ErrorVM(int status, String error, Object message, String path, String data) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.data = data;
    }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }

    public Object getMessage() { return message; }
    public void setMessage(Object message) { this.message = message; }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }
}