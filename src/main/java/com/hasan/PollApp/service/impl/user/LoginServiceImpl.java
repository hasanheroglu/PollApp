package com.hasan.PollApp.service.impl.user;

import com.hasan.PollApp.model.dao.user.UserEntity;
import com.hasan.PollApp.model.dto.user.LoginDto;
import com.hasan.PollApp.model.repo.user.UserRepository;
import com.hasan.PollApp.service.user.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
