package com.hasan.PollApp.service.impl;

import com.hasan.PollApp.model.repo.UserRepository;
import com.hasan.PollApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
}
