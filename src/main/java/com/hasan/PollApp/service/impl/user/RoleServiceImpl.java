package com.hasan.PollApp.service.impl.user;

import com.hasan.PollApp.model.dao.user.RoleEntity;
import com.hasan.PollApp.model.repo.user.RoleRepository;
import com.hasan.PollApp.service.user.RoleService;
import com.hasan.PollApp.util.Operation;
import com.hasan.PollApp.util.OperationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Operation getAll() {
        Iterable<RoleEntity> roles = roleRepository.findAll();

        if(roles.equals(null)){ return new Operation(OperationStatus.ROLE_NOT_FOUND); }

        return new Operation(OperationStatus.ROLE_FOUND, roles);
    }
}
