package com.hasan.PollApp.model.repo;

import com.hasan.PollApp.model.dao.AccessibilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessibilityRepository extends JpaRepository<AccessibilityEntity, Long> {
}
