package com.softeng.votit.model.repo.user;

import com.softeng.votit.model.dao.user.TitleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleRepository extends JpaRepository<TitleEntity, Long> {
}
