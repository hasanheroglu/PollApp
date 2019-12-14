package com.hasan.PollApp.model.repo.user;

import com.hasan.PollApp.model.dao.user.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
    CompanyEntity findByName(String companyName);
}
