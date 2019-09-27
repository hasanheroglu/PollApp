package com.hasan.PollApp.service;

import com.hasan.PollApp.model.dao.CompanyEntity;
import com.hasan.PollApp.model.dao.PollEntity;
import com.hasan.PollApp.model.dto.CompanyDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompanyService {
    Iterable<CompanyEntity> list();
    void add(CompanyDto companyDto);
    List<PollEntity> listPolls(String companyName);
    void addPoll(String companyName, PollEntity poll);
    PollEntity getPoll(String companyName, int pollIndex);
}
