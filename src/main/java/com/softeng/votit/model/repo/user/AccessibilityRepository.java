package com.softeng.votit.model.repo.user;

import com.softeng.votit.model.dao.user.AccessibilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessibilityRepository extends JpaRepository<AccessibilityEntity, Long> {
}
