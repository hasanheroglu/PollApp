package com.softeng.votit.model.dao.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.softeng.votit.model.dto.user.TitleDto;

import javax.persistence.*;
import java.util.List;

@Entity
public class TitleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonIgnore
    private CompanyEntity company;
    @Column
    private String title;
    @ManyToMany(mappedBy = "titles")
    @JsonIgnore
    private List<UserEntity> users;

    public TitleEntity(){

    }

    public TitleEntity(TitleDto titleDto){
        this.title = titleDto.getTitle();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }
}
