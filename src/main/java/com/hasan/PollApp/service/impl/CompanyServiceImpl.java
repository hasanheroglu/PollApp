package com.hasan.PollApp.service.impl;

import com.hasan.PollApp.model.dao.*;
import com.hasan.PollApp.model.dto.CompanyDto;
import com.hasan.PollApp.model.dto.PollDto;
import com.hasan.PollApp.model.dto.TitleDto;
import com.hasan.PollApp.model.dto.UserDto;
import com.hasan.PollApp.model.repo.*;
import com.hasan.PollApp.service.CompanyService;
import com.hasan.PollApp.service.PollService;
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
    public Iterable<CompanyEntity> list() {
        return companyRepository.findAll();
    }

    @Override
    public void add(CompanyDto companyDto) {
        CompanyEntity company = new CompanyEntity(companyDto);
        companyRepository.save(company);
    }

    @Override
    public void remove(String companyName) {
        CompanyEntity company = companyRepository.findByName(companyName);

        //if(company == null) { return something bad!}

        companyRepository.delete(company);
    }

    @Override
    public CompanyEntity get(String companyName) {
        CompanyEntity company = companyRepository.findByName(companyName);

        //if(company == null) { return something bad!}

        return company;
    }

    @Override
    public List<PollEntity> listPolls(String companyName) {
        CompanyEntity company = companyRepository.findByName(companyName);

        //if(company == null) { return something bad!}

        return company.getPolls();
    }

    @Override
    public void addPoll(String companyName, PollEntity poll) {
        CompanyEntity company = companyRepository.findByName(companyName);

        //if(company == null) { return something bad!}

        company.getPolls().add(poll);
        companyRepository.save(company);
    }

    @Override
    public PollEntity getPoll(String companyName, int pollIndex) {
        CompanyEntity company = companyRepository.findByName(companyName);

        //if(company == null) { return something bad!}

        return company.getPolls().get(pollIndex-1);
    }

    @Override
    public List<UserEntity> listUsers(String companyName) {
        CompanyEntity company = companyRepository.findByName(companyName);

        //if(company == null) { return something bad!}

        return company.getUsers();
    }

    @Override
    public List<UserEntity> listUsersByTitle(String companyName, String title) {
        CompanyEntity company = companyRepository.findByName(companyName);

        //if(company == null) { return something bad!}

        List<UserEntity> usersWithTitle = new LinkedList<UserEntity>();
        for (UserEntity user: company.getUsers()) {
            for(TitleEntity titleEntity: user.getTitles()){
                if(titleEntity.getTitle().equals(title)){
                    usersWithTitle.add(user);
                }
            }
        }

        return usersWithTitle;
    }

    @Override
    public void addUser(String companyName, UserDto userDto) {
        CompanyEntity company = companyRepository.findByName(companyName);
        UserEntity user = new UserEntity(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        //if(company == null) { return something bad!}

        AccessibilityEntity email = new AccessibilityEntity("email", user.getEmail());
        AccessibilityEntity phoneNumber = new AccessibilityEntity("phoneNumber", user.getPhoneNumber());

        accessibilityRepository.save(email);
        accessibilityRepository.save(phoneNumber);

        user.getAccessibilityOptions().add(email);
        user.getAccessibilityOptions().add(phoneNumber);

        //call user service instead???
        user.setCompany(company);
        user.setCompanyName(company.getName());
        userRepository.save(user);

        email.setUser(user);
        phoneNumber.setUser(user);

        accessibilityRepository.save(email);
        accessibilityRepository.save(phoneNumber);
    }

    @Override
    public void removeUser(String companyName, Long userId) {
        CompanyEntity company = companyRepository.findByName(companyName);
        Optional<UserEntity> optionalUser = userRepository.findById(userId);

        if(!optionalUser.isPresent()){ return; }

        UserEntity user = optionalUser.get();

        company.getUsers().remove(user);
        companyRepository.save(company);
    }

    @Override
    public Set<TitleEntity> listTitles(String companyName) {
        CompanyEntity company = companyRepository.findByName(companyName);

        //if(company == null) { return something bad!}

        return company.getTitles();
    }

    @Override
    public void addTitle(String companyName, TitleDto titleDto) {
        CompanyEntity company = companyRepository.findByName(companyName);
        TitleEntity title = new TitleEntity(titleDto);

        //if(company == null) { return something bad!}

        //call title service instead???
        title.setCompany(company);
        titleRepository.save(title);
    }
}
