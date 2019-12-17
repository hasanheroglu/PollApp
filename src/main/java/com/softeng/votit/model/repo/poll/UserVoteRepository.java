package com.softeng.votit.model.repo.poll;

import com.softeng.votit.model.dao.poll.UserVoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserVoteRepository extends JpaRepository<UserVoteEntity, Long> {
}
