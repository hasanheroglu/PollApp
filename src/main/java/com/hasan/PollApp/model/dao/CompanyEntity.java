package com.hasan.PollApp.model.dao;

import com.hasan.PollApp.model.dto.CompanyDto;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String description;
    @OneToMany(
            mappedBy = "company",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<UserEntity> users;
    @OneToMany(
            mappedBy = "company",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<TitleEntity> titles;
    @OneToMany(
            mappedBy = "company",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<PollEntity> polls;

    public CompanyEntity() {
    }

    public CompanyEntity(CompanyDto companyDto){
        this.name = companyDto.getName();
        this.description = companyDto.getDescription();
        this.users = new LinkedList<UserEntity>();
        this.titles = new HashSet<TitleEntity>();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }

    public Set<TitleEntity> getTitles() {
        return titles;
    }

    public void setTitles(Set<TitleEntity> titles) {
        this.titles = titles;
    }

    public List<PollEntity> getPolls() {
        return polls;
    }

    public void setPolls(List<PollEntity> polls) {
        this.polls = polls;
    }
}
