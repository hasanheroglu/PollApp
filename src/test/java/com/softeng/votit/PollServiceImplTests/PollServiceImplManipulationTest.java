package com.softeng.votit.PollServiceImplTests;

import com.softeng.votit.model.dao.poll.PollEntity;
import com.softeng.votit.model.dao.user.AccessibilityEntity;
import com.softeng.votit.model.dao.user.CompanyEntity;
import com.softeng.votit.model.dao.user.UserEntity;
import com.softeng.votit.model.dto.poll.PollDto;
import com.softeng.votit.model.dto.poll.PollUpdateDto;
import com.softeng.votit.model.repo.poll.PollRepository;
import com.softeng.votit.model.repo.user.CompanyRepository;
import com.softeng.votit.model.repo.user.UserRepository;
import com.softeng.votit.service.impl.poll.PollServiceImpl;
import com.softeng.votit.service.poll.PollService;
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

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PollServiceImplManipulationTest {

    @TestConfiguration
    static class PollServiceImplManipulationTestContextConfiguration {

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

    @MockBean
    public UserRepository userRepository;

    @Test
    public void whenValidCompany_thenPollShouldBeAdded(){
        CompanyEntity company = new CompanyEntity();
        company.setName("Votit");

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setEmail("hasanheroglu@gmail.com");

        AccessibilityEntity accessibilityOption_1 = new AccessibilityEntity();
        accessibilityOption_1.setType("email");
        accessibilityOption_1.setContent(user.getEmail());
        AccessibilityEntity accessibilityOption_2 = new AccessibilityEntity();
        accessibilityOption_2.setType("phoneNumber");
        accessibilityOption_2.setContent(user.getPhoneNumber());

        user.getAccessibilityOptions().add(accessibilityOption_1);
        user.getAccessibilityOptions().add(accessibilityOption_2);

        Mockito.when(companyRepository.findByName(company.getName()))
                .thenReturn(company);

        Mockito.when(userRepository.findById(user.getId()))
                .thenReturn(Optional.ofNullable(user));

        PollDto pollDto = new PollDto();
        pollDto.setTitle("What do you prefer?");
        pollDto.getOptions().add("First one");
        pollDto.getOptions().add("Second one");
        pollDto.getVoterIdList().add(1L);
        pollDto.getVoterIdList().add(2L);

        Operation operation = pollService.add("Votit", pollDto);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(true);
    }


    @Test
    public void whenInvalidCompany_thenPollShouldNotBeAdded(){
        CompanyEntity company = new CompanyEntity();
        company.setName("Votit");

        Mockito.when(companyRepository.findByName(company.getName()))
                .thenReturn(company);

        PollDto pollDto = new PollDto();
        pollDto.setTitle("What do you prefer?");

        Operation operation = pollService.add("Apple", pollDto);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(false);
    }

    @Test
    public void whenValidId_thenPollShouldBeUpdated(){
        PollEntity pollEntity = new PollEntity();
        pollEntity.setId(1L);
        pollEntity.setTitle("What do you want to do?");

        Mockito.when(pollRepository.findById(pollEntity.getId()))
                .thenReturn(Optional.ofNullable(pollEntity));

        Long id = 1L;

        PollUpdateDto pollUpdateDto = new PollUpdateDto();

        Operation operation = pollService.update(id, pollUpdateDto);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(true);
    }

    @Test
    public void whenInvalidId_thenPollShouldNotBeUpdated(){
        PollEntity pollEntity = new PollEntity();
        pollEntity.setId(1L);
        pollEntity.setTitle("What do you want to do?");

        Mockito.when(pollRepository.findById(pollEntity.getId()))
                .thenReturn(Optional.ofNullable(pollEntity));

        Long id = 2L;

        PollUpdateDto pollUpdateDto = new PollUpdateDto();

        Operation operation = pollService.update(id, pollUpdateDto);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(false);
    }

    @Test
    public void whenValidId_thenPollShouldBeRemoved(){
        PollEntity pollEntity = new PollEntity();
        pollEntity.setId(1L);
        pollEntity.setTitle("What do you want to do?");

        Mockito.when(pollRepository.findById(pollEntity.getId()))
                .thenReturn(Optional.ofNullable(pollEntity));

        Long id = 1L;

        Operation operation = pollService.remove(id);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(true);
    }

    @Test
    public void whenInvalidId_thenPollShouldNotBeRemoved(){
        PollEntity pollEntity = new PollEntity();
        pollEntity.setId(1L);
        pollEntity.setTitle("What do you want to do?");

        Mockito.when(pollRepository.findById(pollEntity.getId()))
                .thenReturn(Optional.ofNullable(pollEntity));

        Long id = 2L;

        Operation operation = pollService.remove(id);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(false);
    }
}
