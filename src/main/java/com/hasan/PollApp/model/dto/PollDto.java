package com.hasan.PollApp.model.dto;

import java.util.List;
import java.util.Set;

public class PollDto {
    private String title;
    private String type;
    private Integer maxSelectionCount;
    private List<String> options;
    private Long ownerId;
    private Long companyId;
    private Set<Long> voterIdList;

    public PollDto() {
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

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
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

    public Set<Long> getVoterIdList() {
        return voterIdList;
    }

    public void setVoterIdList(Set<Long> voterIdList) {
        this.voterIdList = voterIdList;
    }
}
