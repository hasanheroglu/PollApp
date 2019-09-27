package com.hasan.PollApp.model.repo;

import com.hasan.PollApp.model.dao.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
    CompanyEntity findByName(String companyName);
}
