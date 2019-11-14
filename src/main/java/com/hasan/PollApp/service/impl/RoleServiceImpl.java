package com.hasan.PollApp.service.impl;

import com.hasan.PollApp.model.repo.TitleRepository;
import com.hasan.PollApp.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private TitleRepository roleRepository;
}
