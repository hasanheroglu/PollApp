package com.hasan.PollApp.security;

import com.hasan.PollApp.model.dao.CompanyEntity;
import com.hasan.PollApp.model.dao.RoleEntity;
import com.hasan.PollApp.model.dao.UserEntity;
import com.hasan.PollApp.model.repo.CompanyRepository;
import com.hasan.PollApp.model.repo.RoleRepository;
import com.hasan.PollApp.model.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;


@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        RoleEntity systemAdminRole = createRoleIfNotFound("ROLE_SYSTEM_ADMIN");
        RoleEntity companyAdminRole = createRoleIfNotFound("ROLE_COMPANY_ADMIN");
        RoleEntity pollOwnerRole = createRoleIfNotFound("ROLE_POLL_OWNER");
        RoleEntity userRole = createRoleIfNotFound("ROLE_USER");

        CompanyEntity company = new CompanyEntity();
        company.setName("Admin");

        companyRepository.save(company);

        UserEntity userEntity = new UserEntity();
        userEntity.setName("admin");
        userEntity.setSurname(" ");
        userEntity.setEmail("admin@admin.com");
        userEntity.setCompany(company);
        userEntity.getRoles().add(systemAdminRole);
        userEntity.getRoles().add(companyAdminRole);
        userEntity.getRoles().add(pollOwnerRole);
        userEntity.getRoles().add(userRole);
        userEntity.setPassword(passwordEncoder.encode("admin"));
        userEntity.setEnabled(true);
        userEntity.setCompanyName(company.getName());

        userRepository.save(userEntity);

        company.getUsers().add(userEntity);
        companyRepository.save(company);
    }

    @Transactional
    public RoleEntity createRoleIfNotFound(String name) {

        RoleEntity role = roleRepository.findByRole(name);
        if (role == null) {
            role = new RoleEntity(name);
            roleRepository.save(role);
        }

        return role;
    }
}
