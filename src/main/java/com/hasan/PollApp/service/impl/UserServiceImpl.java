package com.hasan.PollApp.service.impl;

import com.hasan.PollApp.model.dao.TitleEntity;
import com.hasan.PollApp.model.dao.UserEntity;
import com.hasan.PollApp.model.dto.TitleDto;
import com.hasan.PollApp.model.dto.UserDto;
import com.hasan.PollApp.model.repo.TitleRepository;
import com.hasan.PollApp.model.repo.UserRepository;
import com.hasan.PollApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TitleRepository titleRepository;

    @Override
    public UserEntity get(Long id) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);

        if(!optionalUser.isPresent()){ return null; }

        UserEntity user = optionalUser.get();

        return user;
    }

    @Override
    public UserEntity add(UserDto userDto) {
        UserEntity user = new UserEntity(userDto);
        userRepository.save(user);
        return user;
    }

    @Override
    public void addTitle(Long id, String name) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);

        if(!optionalUser.isPresent()) { return; }

        UserEntity user = optionalUser.get();
        TitleEntity title = titleRepository.findByTitle(name);
        user.getTitles().add(title);

        userRepository.save(user);
    }

    @Override
    public void removeTitle(Long id, String name) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);

        if(!optionalUser.isPresent()) { return; }

        UserEntity user = optionalUser.get();
        TitleEntity title = titleRepository.findByTitle(name);
        user.getTitles().remove(title);

        userRepository.save(user);
    }
}
