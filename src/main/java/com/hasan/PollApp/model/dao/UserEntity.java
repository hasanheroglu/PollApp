package com.hasan.PollApp.model.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hasan.PollApp.model.dto.UserDto;

import javax.persistence.*;
import java.util.*;

@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private Date birthDate;
    @Column
    private String phoneNumber;
    @Column
    private String email;
    @Column
    private String password;
    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonIgnore
    private CompanyEntity company;
    private String companyName;
    @ManyToMany
    @JoinTable(
            name = "users_and_titles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "title_id", referencedColumnName = "id")
    )
    private List<TitleEntity> titles;
    @ManyToMany(mappedBy = "users")
    @JsonIgnore
    private Set<PollEntity> polls;


    public UserEntity() {
    }

    public UserEntity(UserDto userDto){
        this.name = userDto.getName();
        this.surname = userDto.getSurname();
        this.birthDate = userDto.getBirthDate();
        this.phoneNumber = userDto.getPhoneNumber();
        this.email = userDto.getEmail();
        this.password = userDto.getPassword();
        this.titles = new LinkedList<TitleEntity>();
        this.polls = new HashSet<PollEntity>();
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

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<TitleEntity> getTitles() {
        return titles;
    }

    public void setTitles(List<TitleEntity> titles) {
        this.titles = titles;
    }

    public Set<PollEntity> getPolls() {
        return polls;
    }

    public void setPolls(Set<PollEntity> polls) {
        this.polls = polls;
    }
}
