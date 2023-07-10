package com.cognixia.jump.furniture_app.model;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable{
    
    public static final long serialVersionUID = 1L;

    private String jwt;

    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return this.jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
