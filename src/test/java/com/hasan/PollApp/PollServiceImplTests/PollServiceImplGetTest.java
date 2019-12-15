package com.hasan.PollApp.PollServiceImplTests;

import com.hasan.PollApp.model.dao.poll.PollEntity;
import com.hasan.PollApp.model.dao.user.CompanyEntity;
import com.hasan.PollApp.model.repo.poll.PollRepository;
import com.hasan.PollApp.model.repo.user.CompanyRepository;
import com.hasan.PollApp.service.impl.poll.PollServiceImpl;
import com.hasan.PollApp.service.poll.PollService;
import com.hasan.PollApp.util.Operation;
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

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PollServiceImplGetTest {

    @TestConfiguration
    static class PollServiceImplGetTestContextConfiguration {

        @Bean
        public PollService pollService() {
            return new PollServiceImpl();
        }
    }

    @Autowired
    public PollService pollService;

    @MockBean
    public PollRepository pollRepository;

    @MockBean
    public CompanyRepository companyRepository;

    @Before
    public void setUp(){
        CompanyEntity company = new CompanyEntity();
        company.setName("Votit");
        PollEntity poll_1 = new PollEntity();
        PollEntity poll_2 = new PollEntity();
        poll_1.setId(1L);
        poll_1.setTitle("How are you?");
        poll_2.setId(2L);
        poll_2.setTitle("What do you prefer?");
        company.getPolls().add(poll_1);
        company.getPolls().add(poll_2);

        Mockito.when(companyRepository.findByName(company.getName()))
                .thenReturn(company);

        Mockito.when(pollRepository.findById(poll_1.getId()))
                .thenReturn(Optional.ofNullable(poll_1));
    }

    @Test
    public void whenValidCompany_thenPollShouldBeFound(){
        String companyName = "Votit";
        Operation operation = pollService.getAll(companyName);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(true);
    }

    @Test
    public void whenInvalidCompany_thenPollShouldNotBeFound(){
        String companyName = "Apple";
        Operation operation = pollService.getAll(companyName);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(false);
    }

    @Test
    public void whenValidId_thenPollShouldBeFound(){
        Long id = 1L;
        Operation operation = pollService.get(id);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(true);
    }

    @Test
    public void whenInvalidId_thenPollShouldNotBeFound(){
        Long id = 2L;
        Operation operation = pollService.get(id);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(false);
    }
}
