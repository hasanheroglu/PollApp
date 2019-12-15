package com.hasan.PollApp.service.impl.user;

import com.hasan.PollApp.model.dao.user.CompanyEntity;
import com.hasan.PollApp.model.dao.user.TitleEntity;
import com.hasan.PollApp.model.dao.user.UserEntity;
import com.hasan.PollApp.model.dto.user.CompanyDto;
import com.hasan.PollApp.model.dto.user.CompanyUpdateDto;
import com.hasan.PollApp.model.dto.user.TitleDto;
import com.hasan.PollApp.model.repo.user.AccessibilityRepository;
import com.hasan.PollApp.model.repo.user.CompanyRepository;
import com.hasan.PollApp.model.repo.user.TitleRepository;
import com.hasan.PollApp.model.repo.user.UserRepository;
import com.hasan.PollApp.service.user.CompanyService;
import com.hasan.PollApp.util.Operation;
import com.hasan.PollApp.util.OperationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TitleRepository titleRepository;
    @Autowired
    private AccessibilityRepository accessibilityRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Operation list() {
        Iterable<CompanyEntity> companies = companyRepository.findAll();

        if(companies == null){ return new Operation(OperationStatus.COMPANY_NOT_FOUND); }

        return new Operation(OperationStatus.COMPANY_FOUND, companies);
    }

    @Override
    public Operation get(String companyName) {
        CompanyEntity company = companyRepository.findByName(companyName);

        if(company == null) { return new Operation(OperationStatus.COMPANY_NOT_FOUND); }

        return new Operation(OperationStatus.COMPANY_FOUND, company);
    }

    @Override
    public Operation add(CompanyDto companyDto) {
        CompanyEntity company = new CompanyEntity(companyDto);

        if(companyRepository.findByName(company.getName()) != null){ return new Operation(OperationStatus.COMPANY_EXISTS); }

        companyRepository.save(company);
        return new Operation(OperationStatus.COMPANY_SAVED, company);
    }

    @Override
    public Operation update(String companyName, CompanyUpdateDto companyUpdateDto) {
        CompanyEntity company = companyRepository.findByName(companyName);

        if(company == null){ return new Operation(OperationStatus.COMPANY_NOT_FOUND); }

        company.setDescription(companyUpdateDto.getDescription());
        company.setEstablishmentDate(companyUpdateDto.getEstablishmentDate());
        company = companyRepository.save(company);

        return new Operation<>(OperationStatus.COMPANY_UPDATED, company);
    }

    @Override
    public Operation remove(String companyName) {
        CompanyEntity company = companyRepository.findByName(companyName);

        if(company == null) { return new Operation(OperationStatus.COMPANY_NOT_FOUND); }

        companyRepository.delete(company);
        return new Operation<>(OperationStatus.COMPANY_DELETED, company);
    }

    @Override
    public Operation listUsers(String companyName) {
        CompanyEntity company = companyRepository.findByName(companyName);

        if(company == null) { return new Operation(OperationStatus.COMPANY_NOT_FOUND); }

        List<UserEntity> users = company.getUsers();

        return new Operation<>(OperationStatus.COMPANY_USERS_FOUND, users);
    }

    @Override
    public Operation listUsersByTitle(String companyName, String title) {
        boolean titleFound = false;
        CompanyEntity company = companyRepository.findByName(companyName);

        if(company == null) { return new Operation(OperationStatus.COMPANY_NOT_FOUND); }

        for(TitleEntity companyTitle: company.getTitles()){
            if(companyTitle.getTitle().equals(title)){
                titleFound = true;
            }
        }

        if(!titleFound){ return new Operation(OperationStatus.TITLE_NOT_FOUND); }

        List<UserEntity> usersWithTitle = new LinkedList<UserEntity>();
        for (UserEntity user: company.getUsers()) {
            for(TitleEntity titleEntity: user.getTitles()){
                if(titleEntity.getTitle().equals(title)){
                    usersWithTitle.add(user);
                }
            }
        }

        return new Operation<>(OperationStatus.COMPANY_USERS_FOUND, usersWithTitle);
    }

    @Override
    public Operation listTitles(String companyName) {
        CompanyEntity company = companyRepository.findByName(companyName);

        if(company == null) { return new Operation(OperationStatus.COMPANY_NOT_FOUND); }

        Set<TitleEntity> titles = company.getTitles();

        return new Operation<>(OperationStatus.TITLE_FOUND, titles);
    }

    @Override
    public Operation addTitle(String companyName, TitleDto titleDto) {
        CompanyEntity company = companyRepository.findByName(companyName);
        TitleEntity title = new TitleEntity(titleDto);

        if(company == null) { return new Operation(OperationStatus.COMPANY_NOT_FOUND); }

        for(TitleEntity companyTitle: company.getTitles()){
            if(companyTitle.getTitle().equals(title.getTitle())){ return new Operation(OperationStatus.TITLE_EXIST); }
        }

        title.setCompany(company);
        titleRepository.save(title);

        return new Operation<>(OperationStatus.TITLE_ADDED, title);

    }

    @Override
    public Operation removeTitle(String companyName, Long titleId) {
        CompanyEntity company = companyRepository.findByName(companyName);

        if(company == null) { return new Operation(OperationStatus.COMPANY_NOT_FOUND); }

        Optional<TitleEntity> optionalTitle = titleRepository.findById(titleId);

        if(!optionalTitle.isPresent()) { return new Operation<>(OperationStatus.TITLE_NOT_FOUND); }

        TitleEntity title = optionalTitle.get();

        if(company.getTitles().remove(title)){
            companyRepository.save(company);
            return new Operation<>(OperationStatus.TITLE_DELETED, company);
        }

        return new Operation(OperationStatus.TITLE_NOT_DELETED);
    }
}
