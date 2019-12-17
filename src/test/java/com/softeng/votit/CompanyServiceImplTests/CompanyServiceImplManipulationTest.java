package com.softeng.votit.CompanyServiceImplTests;

import com.softeng.votit.model.dao.user.CompanyEntity;
import com.softeng.votit.model.dto.user.CompanyDto;
import com.softeng.votit.model.dto.user.CompanyUpdateDto;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyServiceImplManipulationTest {

    @TestConfiguration
    static class CompanyServiceImplManipulationTestContextConfiguration {

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
    public void whenCompanyDoesNotExist_thenCompanyShouldBeAdded(){
        CompanyDto companyDto = new CompanyDto();
        companyDto.setName("Votit");

        Operation operation = companyService.add(companyDto);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(true);
    }

    @Test
    public void whenCompanyExists_thenCompanyShouldNotBeAdded(){
        CompanyEntity company = new CompanyEntity();
        company.setName("Votit");

        Mockito.when(companyRepository.findByName(company.getName()))
                .thenReturn(company);

        CompanyDto companyDto = new CompanyDto();
        companyDto.setName("Votit");

        Operation operation = companyService.add(companyDto);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(false);
    }

    @Test
    public void whenCompanyExists_thenCompanyShouldBeUpdated(){
        CompanyEntity company = new CompanyEntity();
        company.setName("Votit");

        Mockito.when(companyRepository.findByName(company.getName()))
                .thenReturn(company);

        String companyName = "Votit";
        CompanyUpdateDto companyUpdateDto = new CompanyUpdateDto();
        companyUpdateDto.setDescription("New description!");

        Operation operation = companyService.update(companyName, companyUpdateDto);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(true);
    }

    @Test
    public void whenCompanyDoesNotExist_thenCompanyShouldNotBeUpdated(){
        CompanyEntity company = new CompanyEntity();
        company.setName("Votit");

        Mockito.when(companyRepository.findByName(company.getName()))
                .thenReturn(company);

        String companyName = "Apple";
        CompanyUpdateDto companyUpdateDto = new CompanyUpdateDto();
        companyUpdateDto.setDescription("New description!");

        Operation operation = companyService.update(companyName, companyUpdateDto);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(false);
    }

    @Test
    public void whenCompanyExists_thenCompanyShouldBeRemoved(){
        CompanyEntity company = new CompanyEntity();
        company.setName("Votit");

        Mockito.when(companyRepository.findByName(company.getName()))
                .thenReturn(company);

        String companyName = "Votit";

        Operation operation = companyService.remove(companyName);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(true);
    }

    @Test
    public void whenCompanyDoesNotExist_thenCompanyShouldNotBeRemoved(){
        CompanyEntity company = new CompanyEntity();
        company.setName("Votit");

        Mockito.when(companyRepository.findByName(company.getName()))
                .thenReturn(company);

        String companyName = "Apple";

        Operation operation = companyService.remove(companyName);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(false);
    }
}
