package com.hasan.PollApp.model.repo;

import com.hasan.PollApp.model.dao.PollEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollRepository extends JpaRepository<PollEntity, Long> {
}
