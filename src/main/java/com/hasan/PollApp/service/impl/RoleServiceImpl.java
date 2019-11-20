package com.hasan.PollApp.service.impl;

import com.hasan.PollApp.model.dao.RoleEntity;
import com.hasan.PollApp.model.repo.RoleRepository;
import com.hasan.PollApp.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Iterable<RoleEntity> getAll() {
        return roleRepository.findAll();
    }
}
