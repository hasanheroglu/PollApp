package com.hasan.PollApp.CompanyServiceImplTests;

import com.hasan.PollApp.model.dao.user.CompanyEntity;
import com.hasan.PollApp.model.dao.user.TitleEntity;
import com.hasan.PollApp.model.dto.user.TitleDto;
import com.hasan.PollApp.model.repo.user.CompanyRepository;
import com.hasan.PollApp.model.repo.user.TitleRepository;
import com.hasan.PollApp.service.impl.user.CompanyServiceImpl;
import com.hasan.PollApp.service.user.CompanyService;
import com.hasan.PollApp.util.Operation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyServiceImplTitleTest {

    @TestConfiguration
    static class UserServiceImplAddTestContextConfiguration {

        @Bean
        public CompanyService companyService() {
            return new CompanyServiceImpl();
        }
    }

    @Autowired
    private CompanyService companyService;

    @MockBean
    private CompanyRepository companyRepository;

    @MockBean
    private TitleRepository titleRepository;

    @Test
    public void whenCompanyExists_thenTitleShouldBeFound(){
        CompanyEntity company = new CompanyEntity();
        company.setName("Votit");

        Mockito.when(companyRepository.findByName(company.getName()))
                .thenReturn(company);

        String companyName = "Votit";
        Operation operation = companyService.listTitles(companyName);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(true);
    }

    @Test
    public void whenCompanyDoesNotExist_thenTitleShouldNotBeFound(){
        CompanyEntity company = new CompanyEntity();
        company.setName("Apple");

        Mockito.when(companyRepository.findByName(company.getName()))
                .thenReturn(company);

        String companyName = "Votit";
        Operation operation = companyService.listTitles(companyName);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(false);
    }

    @Test
    public void whenCompanyExistsAndTitleDoesNotExist_thenTitleShouldBeAdded(){
        CompanyEntity company = new CompanyEntity();
        company.setName("Votit");
        TitleEntity title = new TitleEntity();
        title.setTitle("Manager");
        company.getTitles().add(title);

        Mockito.when(companyRepository.findByName(company.getName()))
                .thenReturn(company);

        String companyName = "Votit";
        TitleDto titleDto = new TitleDto();
        titleDto.setTitle("Developer");

        Operation operation = companyService.addTitle(companyName, titleDto);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(true);
    }

    @Test
    public void whenCompanyDoesNotExist_thenTitleShouldNotBeAdded(){
        CompanyEntity company = new CompanyEntity();
        company.setName("Votit");
        TitleEntity title = new TitleEntity();
        title.setTitle("Manager");
        company.getTitles().add(title);

        Mockito.when(companyRepository.findByName(company.getName()))
                .thenReturn(company);

        String companyName = "Apple";
        TitleDto titleDto = new TitleDto();
        titleDto.setTitle("Developer");

        Operation operation = companyService.addTitle(companyName, titleDto);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(false);
    }

    @Test
    public void whenTitleExists_thenTitleShouldNotBeAdded(){
        CompanyEntity company = new CompanyEntity();
        company.setName("Votit");
        TitleEntity title = new TitleEntity();
        title.setTitle("Manager");
        company.getTitles().add(title);

        Mockito.when(companyRepository.findByName(company.getName()))
                .thenReturn(company);

        String companyName = "Votit";
        TitleDto titleDto = new TitleDto();
        titleDto.setTitle("Manager");

        Operation operation = companyService.addTitle(companyName, titleDto);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(false);
    }

    @Test
    public void whenCompanyExistsAndTitleExists_thenTitleShouldBeRemoved(){

    }

    @Test
    public void whenCompanyDoesNotExist_thenTitleShouldNotBeRemoved(){

    }

    @Test
    public void whenTitleDoesNotExist_thenTitleShouldNotBeRemoved(){

    }
}
