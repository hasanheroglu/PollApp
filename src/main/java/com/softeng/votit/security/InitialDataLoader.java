package com.softeng.votit.security;

import com.softeng.votit.model.dao.user.CompanyEntity;
import com.softeng.votit.model.dao.user.RoleEntity;
import com.softeng.votit.model.dao.user.UserEntity;
import com.softeng.votit.model.repo.user.CompanyRepository;
import com.softeng.votit.model.repo.user.RoleRepository;
import com.softeng.votit.model.repo.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


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

        CompanyEntity company = companyRepository.findByName("Admin");
        UserEntity user = userRepository.findByEmail("admin@admin.com");

        if(company == null && user == null){
            company = new CompanyEntity();
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

            userEntity = userRepository.save(userEntity);

            company.getUsers().add(userEntity);
            companyRepository.save(company);
        }
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
