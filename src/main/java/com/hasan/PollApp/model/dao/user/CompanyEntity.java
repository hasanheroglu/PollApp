package com.hasan.PollApp.model.dao.user;

import com.hasan.PollApp.model.dao.poll.PollEntity;
import com.hasan.PollApp.model.dto.user.CompanyDto;

import javax.persistence.*;
import java.util.*;

@Entity
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column
    private String description;
    @Column
    private Date establishmentDate;
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
        this.users = new LinkedList<UserEntity>();
        this.titles = new HashSet<TitleEntity>();
        this.polls = new LinkedList<PollEntity>();
    }

    public CompanyEntity(CompanyDto companyDto){
        this.name = companyDto.getName();
        this.description = companyDto.getDescription();
        this.establishmentDate = companyDto.getEstablishmentDate();
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

    public Date getEstablishmentDate() {
        return establishmentDate;
    }

    public void setEstablishmentDate(Date establishmentDate) {
        this.establishmentDate = establishmentDate;
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
