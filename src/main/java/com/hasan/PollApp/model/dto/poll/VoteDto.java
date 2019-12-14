package com.hasan.PollApp.model.dto.poll;

import java.util.List;

public class VoteDto {
    private Long pollId;
    private Long voterId;
    private List<Long> optionIds;
    private List<Integer> votePoints;

    public VoteDto() {
    }

    public VoteDto(Long pollId, Long voterId, List<Long> optionIds, List<Integer> votePoints) {
        this.pollId = pollId;
        this.voterId = voterId;
        this.optionIds = optionIds;
        this.votePoints = votePoints;
    }

    public Long getPollId() {
        return pollId;
    }

    public void setPollId(Long pollId) {
        this.pollId = pollId;
    }

    public List<Long> getOptionIds() {
        return optionIds;
    }

    public void setOptionIds(List<Long> optionIds) {
        this.optionIds = optionIds;
    }

    public List<Integer> getVotePoints() {
        return votePoints;
    }

    public void setVotePoints(List<Integer> votePoints) {
        this.votePoints = votePoints;
    }

    public Long getVoterId() {
        return voterId;
    }

    public void setVoterId(Long voterId) {
        this.voterId = voterId;
    }
}
