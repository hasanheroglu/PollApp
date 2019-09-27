package com.hasan.PollApp.service;

import com.hasan.PollApp.model.dao.PollEntity;
import com.hasan.PollApp.model.dto.PollDto;
import org.springframework.stereotype.Service;

public interface PollService {
    PollEntity add(PollDto pollDto);
    void addTitle(Long id, String title);
    void addOption(Long id, String option);
}
