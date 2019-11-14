package com.hasan.PollApp.service;

import com.hasan.PollApp.model.dao.PollEntity;
import com.hasan.PollApp.model.dto.PollDto;
import com.hasan.PollApp.model.dto.VoteDto;

import java.util.HashSet;
import java.util.List;

public interface PollService {
    Iterable<PollEntity> getAll(String companyName);
    PollEntity get(Long id);
    PollEntity add(String companyName, PollDto pollDto);
    void addTitle(Long id, String title);
    void addOption(Long id, String option);
    void vote(VoteDto voteDto);
}
