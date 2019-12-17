package com.softeng.votit.model.repo.user;

import com.softeng.votit.model.dao.user.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByRole(String role);
}
