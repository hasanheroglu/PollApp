package com.softeng.votit.model.repo.user;

import com.softeng.votit.model.dao.user.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
    CompanyEntity findByName(String companyName);
}
