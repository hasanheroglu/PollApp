package com.softeng.votit.model.dto.user;

import java.util.LinkedList;
import java.util.List;

public class SecurityDto {
    private String email;
    private String token;
    private List<String> roles;

    public SecurityDto() {
        this.roles = new LinkedList<String>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
