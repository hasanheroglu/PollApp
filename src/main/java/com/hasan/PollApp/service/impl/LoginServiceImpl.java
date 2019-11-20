package com.hasan.PollApp.service.impl;

import com.hasan.PollApp.model.dao.UserEntity;
import com.hasan.PollApp.model.dto.LoginDto;
import com.hasan.PollApp.model.repo.UserRepository;
import com.hasan.PollApp.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void login(LoginDto loginDto) {
        UserEntity user = userRepository.findByEmail(loginDto.getEmail());

        if (user == null) { return; }
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) { return; }
    }
}
