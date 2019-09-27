package com.hasan.PollApp.model.dao;

import com.hasan.PollApp.model.dto.PollDto;

import javax.persistence.*;
import java.util.List;

@Entity
public class PollEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private Integer entryCount;
    @Column
    private Integer optionCount;
    @Column
    @Embedded
    private List<String> options;
    @Column
    @Embedded
    private List<Integer> results;
    @Column
    private Long ownerId;
    @Column
    private Long companyId;

    public PollEntity() {
    }

    public PollEntity(PollDto pollDto){
        this.title = pollDto.getTitle();
        this.ownerId = pollDto.getOwnerId();
        this.companyId = pollDto.getCompanyId();
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

    public Integer getEntryCount() {
        return entryCount;
    }

    public void setEntryCount(Integer entryCount) {
        this.entryCount = entryCount;
    }

    public Integer getOptionCount() {
        return optionCount;
    }

    public void setOptionCount(Integer optionCount) {
        this.optionCount = optionCount;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public List<Integer> getResults() {
        return results;
    }

    public void setResults(List<Integer> results) {
        this.results = results;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
