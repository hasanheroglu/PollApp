package com.hasan.PollApp.model.dao.poll;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hasan.PollApp.model.dao.user.CompanyEntity;
import com.hasan.PollApp.model.dao.user.UserEntity;
import com.hasan.PollApp.model.dto.poll.PollDto;

import javax.persistence.*;
import java.util.*;

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
    private Date startDate;
    @Column
    private Date endDate;
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
    @JsonIgnore
    private Set<UserEntity> users;
    @OneToMany(mappedBy = "poll")
    @JsonIgnore
    private List<UserVoteEntity> votes;

    public PollEntity() {
    }

    public PollEntity(PollDto pollDto){
        this.title = pollDto.getTitle();
        this.type = pollDto.getType();
        this.options = new LinkedList<OptionEntity>();
        this.users = new HashSet<UserEntity>();
        this.entryCount = 0;
        this.maxSelectionCount = pollDto.getMaxSelectionCount();
        this.startDate = pollDto.getStartDate();
        this.endDate = pollDto.getEndDate();
        this.ownerId = pollDto.getOwnerId();
        this.votes = new LinkedList<UserVoteEntity>();
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public List<UserVoteEntity> getVotes() {
        return votes;
    }

    public void setVotes(List<UserVoteEntity> votes) {
        this.votes = votes;
    }
}
