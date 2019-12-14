package com.hasan.PollApp.model.dto.user;

import com.hasan.PollApp.model.dao.user.AccessibilityEntity;
import com.hasan.PollApp.model.dao.user.RoleEntity;
import com.hasan.PollApp.model.dao.user.TitleEntity;

import java.util.Date;
import java.util.List;

public class UserDto {
    private String name;
    private String surname;
    private Date birthDate;
    private String phoneNumber;
    private String email;
    private String password;
    private String passwordConfirmation;
    private List<RoleEntity> roles;
    private List<TitleEntity> titles;
    private List<AccessibilityEntity> accessibilityOptions;

    public UserDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public List<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }

    public List<TitleEntity> getTitles() {
        return titles;
    }

    public void setTitles(List<TitleEntity> titles) {
        this.titles = titles;
    }

    public List<AccessibilityEntity> getAccessibilityOptions() {
        return accessibilityOptions;
    }

    public void setAccessibilityOptions(List<AccessibilityEntity> accessibilityOptions) {
        this.accessibilityOptions = accessibilityOptions;
    }
}
