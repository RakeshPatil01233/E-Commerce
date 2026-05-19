package com.rk.ecommerce.dto;

public class ErrorResponse {

    private String message;
    private int status;

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}