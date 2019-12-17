package com.softeng.votit.CompanyServiceImplTests;

import com.softeng.votit.model.dao.user.CompanyEntity;
import com.softeng.votit.model.dao.user.TitleEntity;
import com.softeng.votit.model.dao.user.UserEntity;
import com.softeng.votit.model.repo.user.CompanyRepository;
import com.softeng.votit.service.impl.user.CompanyServiceImpl;
import com.softeng.votit.service.user.CompanyService;
import com.softeng.votit.util.Operation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyServiceImplListUsersTest {

    @TestConfiguration
    static class CompanyServiceImplListUsersTestContextConfiguration {

        @Bean
        public CompanyService companyService() {
            return new CompanyServiceImpl();
        }
    }

    @Autowired
    private CompanyService companyService;

    @MockBean
    private CompanyRepository companyRepository;

    @Before
    public void setUp(){
        CompanyEntity company = new CompanyEntity();
        company.setName("Votit");

        UserEntity user_1 = new UserEntity();
        user_1.setEmail("hasanheroglu@gmail.com");
        UserEntity user_2 = new UserEntity();
        user_2.setEmail("ilmiyepasaoglu@gmail.com");

        TitleEntity title_1 = new TitleEntity();
        title_1.setTitle("Manager");
        TitleEntity title_2 = new TitleEntity();
        title_2.setTitle("Developer");
        company.getUsers().add(user_1);
        company.getUsers().add(user_2);
        company.getTitles().add(title_1);
        company.getTitles().add(title_2);
        user_1.getTitles().add(title_1);

        Mockito.when(companyRepository.findByName(company.getName()))
                .thenReturn(company);
    }

    @Test
    public void whenValidCompanyName_thenUsersShouldBeFound(){
        String companyName = "Votit";

        Operation operation = companyService.listUsers(companyName);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(true);
    }

    @Test
    public void whenInvalidCompanyName_thenUsersShouldNotBeFound(){
        String companyName = "Apple";

        Operation operation = companyService.listUsers(companyName);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(false);
    }

    @Test
    public void whenValidCompanyNameAndTitle_thenUsersWithTitleShouldBeFound(){
        String companyName = "Votit";
        String title = "Manager";

        Operation operation = companyService.listUsersByTitle(companyName, title);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(true);
    }

    @Test
    public void whenValidCompanyNameAndInvalidTitle_thenUsersWithTitleShouldBeFound(){
        String companyName = "Votit";
        String title = "Developer";

        Operation operation = companyService.listUsersByTitle(companyName, title);

        assertThat(operation.getOperationObject())
                .isEqualTo(new LinkedList<>());
    }

    @Test
    public void whenInvalidCompanyName_thenUsersWithTitleShouldNotBeFound(){
        String companyName = "Apple";
        String title = "Manager";

        Operation operation = companyService.listUsersByTitle(companyName, title);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(false);
    }

    @Test
    public void whenInvalidTitle_thenUsersWithTitleShouldNotBeFound(){
        String companyName = "Votit";
        String title = "Finance Manager";

        Operation operation = companyService.listUsersByTitle(companyName, title);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(false);
    }
}
