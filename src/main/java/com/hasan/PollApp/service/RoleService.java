package com.hasan.PollApp.service;

import com.hasan.PollApp.model.dao.RoleEntity;

public interface RoleService {
    Iterable<RoleEntity> getAll();
}
