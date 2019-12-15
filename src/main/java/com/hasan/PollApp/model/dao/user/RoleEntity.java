package com.hasan.PollApp.model.dao.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String role;
    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private List<UserEntity> users;

    public RoleEntity() {
        this.users = new LinkedList<UserEntity>();
    }

    public RoleEntity(String name){
        this.role = name;
        this.users = new LinkedList<UserEntity>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }
}
