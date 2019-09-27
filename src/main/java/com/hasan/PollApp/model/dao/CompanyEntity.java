package com.hasan.PollApp.model.dao;

import com.hasan.PollApp.model.dto.CompanyDto;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    @OneToMany
    @JoinTable(
            name = "companies_and_users",
            joinColumns = @JoinColumn(name = "company_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    private List<UserEntity> users;
    @Column
    @OneToMany
    @JoinTable(
            name = "companies_and_roles",
            joinColumns = @JoinColumn(name = "company_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private List<RoleEntity> roles;
    @Column
    @OneToMany
    @JoinTable(
            name = "companies_and_polls",
            joinColumns = @JoinColumn(name = "company_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "poll_id", referencedColumnName = "id")
    )
    private List<PollEntity> polls;

    public CompanyEntity() {
    }

    public CompanyEntity(CompanyDto companyDto){
        this.name = companyDto.getName();
        this.users = new LinkedList<UserEntity>();
        this.roles = new LinkedList<RoleEntity>();
        this.polls = new LinkedList<PollEntity>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }

    public List<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }

    public List<PollEntity> getPolls() {
        return polls;
    }

    public void setPolls(List<PollEntity> polls) {
        this.polls = polls;
    }
}
