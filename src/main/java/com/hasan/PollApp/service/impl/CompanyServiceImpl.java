package com.hasan.PollApp.service.impl;

import com.hasan.PollApp.model.dao.CompanyEntity;
import com.hasan.PollApp.model.dao.PollEntity;
import com.hasan.PollApp.model.dto.CompanyDto;
import com.hasan.PollApp.model.dto.PollDto;
import com.hasan.PollApp.model.repo.CompanyRepository;
import com.hasan.PollApp.model.repo.PollRepository;
import com.hasan.PollApp.service.CompanyService;
import com.hasan.PollApp.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public Iterable<CompanyEntity> list() {
        return companyRepository.findAll();
    }

    @Override
    public void add(CompanyDto companyDto) {
        CompanyEntity company = new CompanyEntity(companyDto);
        companyRepository.save(company);
    }

    @Override
    public List<PollEntity> listPolls(String companyName) {
        CompanyEntity company = companyRepository.findByName(companyName);

        //if(company == null) { return something bad!}

        return company.getPolls();
    }

    @Override
    public void addPoll(String companyName, PollEntity poll) {
        CompanyEntity company = companyRepository.findByName(companyName);

        //if(company == null) { return something bad!}

        company.getPolls().add(poll);
        companyRepository.save(company);
    }

    @Override
    public PollEntity getPoll(String companyName, int pollIndex) {
        CompanyEntity company = companyRepository.findByName(companyName);
        return company.getPolls().get(pollIndex-1);
    }
}
