package com.hasan.PollApp.service;

import com.hasan.PollApp.model.dao.CompanyEntity;
import com.hasan.PollApp.model.dao.PollEntity;
import com.hasan.PollApp.model.dao.TitleEntity;
import com.hasan.PollApp.model.dao.UserEntity;
import com.hasan.PollApp.model.dto.CompanyDto;
import com.hasan.PollApp.model.dto.TitleDto;
import com.hasan.PollApp.model.dto.UserDto;

import java.util.List;
import java.util.Set;

public interface CompanyService {
    Iterable<CompanyEntity> list();
    void add(CompanyDto companyDto);
    void remove(String companyName);
    CompanyEntity get(String companyName);
    List<PollEntity> listPolls(String companyName);
    void addPoll(String companyName, PollEntity poll);
    PollEntity getPoll(String companyName, int pollIndex);
    List<UserEntity> listUsers(String companyName);
    void addUser(String companyName, UserDto userDto);
    void removeUser(String companyName, Long userId);
    Set<TitleEntity> listTitles(String companyName);
    void addTitle(String companyName, TitleDto titleDto);
}
