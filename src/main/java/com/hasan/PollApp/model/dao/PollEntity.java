package com.hasan.PollApp.model.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hasan.PollApp.model.dto.PollDto;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
public class PollEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String type;
    @Column
    private Integer maxSelectionCount;
    @Column
    private Integer entryCount;
    @OneToMany(
            mappedBy = "poll",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<OptionEntity> options;
    @Column
    private Long ownerId;
    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonIgnore
    private CompanyEntity company;
    @ManyToMany
    @JoinTable(
            name = "polls_and_users",
            joinColumns = @JoinColumn(name = "poll_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    private Set<UserEntity> users;

    public PollEntity() {
    }

    public PollEntity(PollDto pollDto){
        this.title = pollDto.getTitle();
        this.type = pollDto.getType();
        this.options = new LinkedList<OptionEntity>();
        this.users = new HashSet<UserEntity>();
        this.entryCount = 0;
        this.maxSelectionCount = pollDto.getMaxSelectionCount();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getMaxSelectionCount() {
        return maxSelectionCount;
    }

    public void setMaxSelectionCount(Integer maxSelectionCount) {
        this.maxSelectionCount = maxSelectionCount;
    }

    public Integer getEntryCount() {
        return entryCount;
    }

    public void setEntryCount(Integer entryCount) {
        this.entryCount = entryCount;
    }

    public List<OptionEntity> getOptions() {
        return options;
    }

    public void setOptions(List<OptionEntity> options) {
        this.options = options;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }
}
