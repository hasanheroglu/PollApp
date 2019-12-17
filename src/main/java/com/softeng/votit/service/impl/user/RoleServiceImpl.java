package com.softeng.votit.service.impl.user;

import com.softeng.votit.model.dao.user.RoleEntity;
import com.softeng.votit.model.repo.user.RoleRepository;
import com.softeng.votit.service.user.RoleService;
import com.softeng.votit.util.Operation;
import com.softeng.votit.util.OperationStatus;
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
