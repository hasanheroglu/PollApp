package com.hasan.PollApp.model.repo.user;

import com.hasan.PollApp.model.dao.user.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByRole(String role);
}
