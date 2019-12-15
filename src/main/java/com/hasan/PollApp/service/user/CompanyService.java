package com.hasan.PollApp.service.user;

import com.hasan.PollApp.model.dto.user.CompanyDto;
import com.hasan.PollApp.model.dto.user.CompanyUpdateDto;
import com.hasan.PollApp.model.dto.user.TitleDto;
import com.hasan.PollApp.util.Operation;

public interface CompanyService {
    Operation list();
    Operation get(String companyName);
    Operation add(CompanyDto companyDto);
    Operation remove(String companyName);
    Operation update(String companyName, CompanyUpdateDto companyUpdateDto);
    Operation listUsers(String companyName);
    Operation listUsersByTitle(String companyName, String title);
    Operation listTitles(String companyName);
    Operation addTitle(String companyName, TitleDto titleDto);
    Operation removeTitle(String companyName, Long titleId);
}
