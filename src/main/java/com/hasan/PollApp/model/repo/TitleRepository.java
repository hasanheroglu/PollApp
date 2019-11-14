package com.hasan.PollApp.model.repo;

import com.hasan.PollApp.model.dao.TitleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleRepository extends JpaRepository<TitleEntity, Long> {
    TitleEntity findByTitle(String title);
}
