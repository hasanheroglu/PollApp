package com.hasan.PollApp.service.impl.user;

import com.hasan.PollApp.model.dao.user.AccessibilityEntity;
import com.hasan.PollApp.model.dao.user.CompanyEntity;
import com.hasan.PollApp.model.dao.user.TitleEntity;
import com.hasan.PollApp.model.dao.user.UserEntity;
import com.hasan.PollApp.model.dto.user.UserDto;
import com.hasan.PollApp.model.repo.user.AccessibilityRepository;
import com.hasan.PollApp.model.repo.user.CompanyRepository;
import com.hasan.PollApp.model.repo.user.TitleRepository;
import com.hasan.PollApp.model.repo.user.UserRepository;
import com.hasan.PollApp.service.user.UserService;
import com.hasan.PollApp.util.Operation;
import com.hasan.PollApp.util.OperationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TitleRepository titleRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private AccessibilityRepository accessibilityRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Operation get(Long id) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);

        if(!optionalUser.isPresent()){ return new Operation<>(OperationStatus.USER_NOT_FOUND); }

        UserEntity user = optionalUser.get();

        return new Operation<>(OperationStatus.USER_FOUND, user);
    }

    @Override
    public Operation getByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email);

        if(user == null) { return new Operation<>(OperationStatus.USER_NOT_FOUND); }

        return new Operation<>(OperationStatus.USER_FOUND, user);
    }

    @Override
    public Operation add(String companyName, UserDto userDto) {
        CompanyEntity company = companyRepository.findByName(companyName);
        UserEntity user = new UserEntity(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        if(company == null) { return new Operation<>(OperationStatus.COMPANY_NOT_FOUND); }

        userRepository.save(user);
        addAccessibilityOptions(user);
        addCompany(user, company);

        return new Operation<>(OperationStatus.USER_ADDED, user);
    }

    @Override
    public Operation remove(String companyName, Long id) {
        CompanyEntity company = companyRepository.findByName(companyName);
        Optional<UserEntity> optionalUser = userRepository.findById(id);

        if(!optionalUser.isPresent()){ return new Operation<>(OperationStatus.USER_NOT_FOUND); }

        UserEntity user = optionalUser.get();

        company.getUsers().remove(user);
        companyRepository.save(company);
        return new Operation<>(OperationStatus.USER_DELETED);
    }

    private void addAccessibilityOptions(UserEntity user){
        AccessibilityEntity email = new AccessibilityEntity("email", user.getEmail());
        AccessibilityEntity phoneNumber = new AccessibilityEntity("phoneNumber", user.getPhoneNumber());

        email.setUser(user);
        phoneNumber.setUser(user);

        accessibilityRepository.save(email);
        accessibilityRepository.save(phoneNumber);

        user.getAccessibilityOptions().add(email);
        user.getAccessibilityOptions().add(phoneNumber);

        userRepository.save(user);
    }

    private void addCompany(UserEntity user, CompanyEntity company){
        user.setCompany(company);
        user.setCompanyName(company.getName());
        userRepository.save(user);
    }

    @Override
    public Operation addTitle(Long id, String name) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);

        if(!optionalUser.isPresent()) { return new Operation<>(OperationStatus.USER_NOT_FOUND); }

        UserEntity user = optionalUser.get();
        TitleEntity title = titleRepository.findByTitle(name);

        if(title == null) { return new Operation<>(OperationStatus.TITLE_NOT_FOUND); }

        if(user.getTitles().add(title)){
            userRepository.save(user);
            return new Operation<>(OperationStatus.USER_TITLE_ADDED, user);
        }

        return new Operation<>(OperationStatus.USER_TITLE_NOT_ADDED);
    }

    @Override
    public Operation removeTitle(Long id, String name) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);

        if(!optionalUser.isPresent()) { return new Operation<>(OperationStatus.USER_NOT_FOUND); }

        UserEntity user = optionalUser.get();
        TitleEntity title = titleRepository.findByTitle(name);

        if(title == null) { return new Operation<>(OperationStatus.TITLE_NOT_FOUND); }

        if(user.getTitles().remove(title)){
            userRepository.save(user);
            return new Operation<>(OperationStatus.USER_TITLE_DELETED, user);
        }

        return new Operation<>(OperationStatus.USER_TITLE_NOT_DELETED, user);
    }
}
