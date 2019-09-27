package com.hasan.PollApp.service.impl;

import com.hasan.PollApp.model.dao.PollEntity;
import com.hasan.PollApp.model.dto.PollDto;
import com.hasan.PollApp.model.repo.CompanyRepository;
import com.hasan.PollApp.model.repo.PollRepository;
import com.hasan.PollApp.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PollServiceImpl implements PollService {
    @Autowired
    private PollRepository pollRepository;

    @Override
    public PollEntity add(PollDto pollDto) {
        PollEntity pollEntity = new PollEntity(pollDto);
        pollRepository.save(pollEntity);
        return pollEntity;
    }

    @Override
    public void addTitle(Long id, String title) {
        Optional<PollEntity> optionalPollEntity = pollRepository.findById(id);

        if(!optionalPollEntity.isPresent()){ return; }

        PollEntity pollEntity = optionalPollEntity.get();
        pollEntity.setTitle(title);
        pollRepository.save(pollEntity);
    }

    @Override
    public void addOption(Long id, String option) {

    }
}
