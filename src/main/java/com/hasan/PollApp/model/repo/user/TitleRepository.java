package com.hasan.PollApp.model.repo.user;

import com.hasan.PollApp.model.dao.user.TitleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleRepository extends JpaRepository<TitleEntity, Long> {
}
