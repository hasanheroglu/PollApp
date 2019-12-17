package com.softeng.votit.CompanyServiceImplTests;

import com.softeng.votit.model.dao.user.CompanyEntity;
import com.softeng.votit.model.repo.user.CompanyRepository;
import com.softeng.votit.service.impl.user.CompanyServiceImpl;
import com.softeng.votit.service.user.CompanyService;
import com.softeng.votit.util.Operation;
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
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyServiceImplGetTest {

    @TestConfiguration
    static class CompanyServiceImplGetTestContextConfiguration {

        @Bean
        public CompanyService companyService() {
            return new CompanyServiceImpl();
        }
    }

    @Autowired
    private CompanyService companyService;

    @MockBean
    private CompanyRepository companyRepository;

    @Test
    public void whenCompaniesExist_thenCompaniesShouldBeListed(){
        List<CompanyEntity> companies = new LinkedList<>();
        CompanyEntity company_1 = new CompanyEntity();
        company_1.setName("Votit");
        CompanyEntity company_2 = new CompanyEntity();
        company_2.setName("Apple");
        companies.add(company_1);
        companies.add(company_2);

        Mockito.when(companyRepository.findAll())
                .thenReturn(companies);

        Operation operation = companyService.list();

        assertThat(operation.getWasSuccessful())
                .isEqualTo(true);
    }

    @Test
    public void whenCompaniesDoNotExist_thenCompaniesShouldNotBeListed(){
        Mockito.when(companyRepository.findAll())
                .thenReturn(null);

        Operation operation = companyService.list();

        assertThat(operation.getWasSuccessful())
                .isEqualTo(false);
    }

    @Test
    public void whenValidCompanyName_thenCompanyShouldBeFound(){
        CompanyEntity company = new CompanyEntity();
        company.setName("Votit");

        Mockito.when(companyRepository.findByName(company.getName()))
                .thenReturn(company);

        Operation operation = companyService.get("Votit");

        assertThat(operation.getWasSuccessful())
                .isEqualTo(true);
    }

    @Test
    public void whenInvalidCompanyName_thenCompanyShouldNotBeFound(){
        CompanyEntity company = new CompanyEntity();
        company.setName("Votit");

        Mockito.when(companyRepository.findByName(company.getName()))
                .thenReturn(company);

        Operation operation = companyService.get("Apple");

        assertThat(operation.getWasSuccessful())
                .isEqualTo(false);
    }
}
