package com.hasan.PollApp.model.repo.user;

import com.hasan.PollApp.model.dao.user.AccessibilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessibilityRepository extends JpaRepository<AccessibilityEntity, Long> {
}
