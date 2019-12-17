package com.softeng.votit.model.repo.poll;

import com.softeng.votit.model.dao.poll.PollEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollRepository extends JpaRepository<PollEntity, Long> {
}
