package com.hasan.PollApp.model.repo;

import com.hasan.PollApp.model.dao.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByRole(String role);
}
