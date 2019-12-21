package com.softeng.votit.service.impl.user;

import com.softeng.votit.model.dao.poll.PollEntity;
import com.softeng.votit.model.dao.poll.UserVoteEntity;
import com.softeng.votit.model.dao.user.*;
import com.softeng.votit.model.dto.user.UserDto;
import com.softeng.votit.model.repo.poll.PollRepository;
import com.softeng.votit.model.repo.poll.UserVoteRepository;
import com.softeng.votit.model.repo.user.*;
import com.softeng.votit.service.user.UserService;
import com.softeng.votit.util.Operation;
import com.softeng.votit.util.OperationStatus;
import com.softeng.votit.model.dao.user.*;
import com.softeng.votit.model.repo.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
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
    private RoleRepository roleRepository;
    @Autowired
    private PollRepository pollRepository;
    @Autowired
    private AccessibilityRepository accessibilityRepository;
    @Autowired
    private UserVoteRepository userVoteRepository;
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
    public Operation getVoterPolls(Long id) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);

        if(!optionalUser.isPresent()){ return new Operation<>(OperationStatus.USER_NOT_FOUND); }

        UserEntity user = optionalUser.get();

        if(user == null) { return new Operation<>(OperationStatus.USER_NOT_FOUND); }

        return new Operation<>(OperationStatus.POLL_FOUND, user.getPolls());
    }

    @Override
    public Operation getOwnedPolls(Long id) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);

        if(!optionalUser.isPresent()){ return new Operation<>(OperationStatus.USER_NOT_FOUND); }

        UserEntity user = optionalUser.get();

        if(user == null) { return new Operation<>(OperationStatus.USER_NOT_FOUND); }

        CompanyEntity company = companyRepository.findByName(user.getCompanyName());

        if(company == null){ return new Operation<>(OperationStatus.COMPANY_NOT_FOUND); }

        List<PollEntity> ownedPolls = new LinkedList<>();

        for(PollEntity poll: company.getPolls()){
            if(poll.getOwnerId() == id){
                ownedPolls.add(poll);
            }
        }

        return new Operation<>(OperationStatus.POLL_FOUND, ownedPolls);
    }

    @Override
    public Operation add(String companyName, UserDto userDto) {
        CompanyEntity company = companyRepository.findByName(companyName);
        UserEntity user = new UserEntity(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        if(company == null) { return new Operation<>(OperationStatus.COMPANY_NOT_FOUND); }
        if(userDto.getEmail() == null) { return new Operation<>(OperationStatus.USER_NOT_ADDED); }
        if(userRepository.findByEmail(user.getEmail()) != null){ return new Operation<>(OperationStatus.USER_NOT_ADDED); }

        userRepository.save(user);

        addAccessibilityOptions(user);
        addCompany(user, company);

        return new Operation<>(OperationStatus.USER_ADDED, user);
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
    public Operation remove(String companyName, Long id) {
        CompanyEntity company = companyRepository.findByName(companyName);
        Optional<UserEntity> optionalUser = userRepository.findById(id);

        if(!optionalUser.isPresent()){ return new Operation<>(OperationStatus.USER_NOT_FOUND); }
        if(company == null){ return new Operation<>(OperationStatus.COMPANY_NOT_FOUND); }

        UserEntity user = optionalUser.get();

        for(PollEntity poll: user.getPolls()){
            poll.getUsers().remove(user);
            for(UserVoteEntity userVote: poll.getVotes()){
                if(userVote.getUser().equals(user)){
                    poll.getVotes().remove(userVote);
                    userVoteRepository.delete(userVote);
                    break;
                }
            }
        }

        company.getUsers().remove(user);
        companyRepository.save(company);
        userRepository.delete(user);
        return new Operation<>(OperationStatus.USER_DELETED, user);
    }


    @Override
    public Operation addTitle(Long id, Long titleId) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);

        if(!optionalUser.isPresent()) { return new Operation<>(OperationStatus.USER_NOT_FOUND); }

        UserEntity user = optionalUser.get();
        Optional<TitleEntity> optionalTitle = titleRepository.findById(titleId);

        if(!optionalTitle.isPresent()) { return new Operation<>(OperationStatus.TITLE_NOT_FOUND); }

        TitleEntity title = optionalTitle.get();

        for(TitleEntity userTitle: user.getTitles()){
            if(userTitle.getTitle().equals(title.getTitle())){ return new Operation<>(OperationStatus.TITLE_EXIST); }
        }

        userRepository.save(user);
        return new Operation<>(OperationStatus.USER_TITLE_ADDED, user);
    }

    @Override
    public Operation removeTitle(Long id, Long titleId) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);

        if(!optionalUser.isPresent()) { return new Operation<>(OperationStatus.USER_NOT_FOUND); }

        UserEntity user = optionalUser.get();
        Optional<TitleEntity> optionalTitle = titleRepository.findById(titleId);

        if(!optionalTitle.isPresent()) { return new Operation<>(OperationStatus.TITLE_NOT_FOUND); }

        TitleEntity title = optionalTitle.get();

        if(user.getTitles().remove(title)){
            userRepository.save(user);
            return new Operation<>(OperationStatus.USER_TITLE_DELETED, user);
        }

        return new Operation<>(OperationStatus.USER_TITLE_NOT_DELETED, user);
    }

    @Override
    public Operation addRole(Long id, String role) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);

        if(!optionalUser.isPresent()) { return new Operation<>(OperationStatus.USER_NOT_FOUND); }

        UserEntity user = optionalUser.get();
        RoleEntity roleEntity = roleRepository.findByRole(role);

        if(roleEntity == null){ return new Operation(OperationStatus.ROLE_NOT_FOUND); }

        for(RoleEntity userRole: user.getRoles()){
            if(userRole.getRole().equals(role)) { return new Operation(OperationStatus.ROLE_EXIST); }
        }

        user.getRoles().add(roleEntity);
        userRepository.save(user);
        roleEntity.getUsers().add(user);
        roleRepository.save(roleEntity);


        return new Operation(OperationStatus.ROLE_ADDED, user);
    }

    @Override
    public Operation removeRole(Long id, String role) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);

        if(!optionalUser.isPresent()) { return new Operation<>(OperationStatus.USER_NOT_FOUND); }

        UserEntity user = optionalUser.get();
        RoleEntity roleEntity = roleRepository.findByRole(role);

        if(roleEntity == null){ return new Operation(OperationStatus.ROLE_NOT_FOUND); }

        if(user.getRoles().remove(roleEntity)){
            userRepository.save(user);
            return new Operation(OperationStatus.ROLE_DELETED, user);
        }

        return new Operation(OperationStatus.ROLE_NOT_DELETED);
    }
}
