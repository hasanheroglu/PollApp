package com.softeng.votit.UserServiceImplTests;

import com.softeng.votit.model.dao.user.CompanyEntity;
import com.softeng.votit.model.dao.user.UserEntity;
import com.softeng.votit.model.repo.user.CompanyRepository;
import com.softeng.votit.model.repo.user.UserRepository;
import com.softeng.votit.service.impl.user.UserServiceImpl;
import com.softeng.votit.service.user.UserService;
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

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplRemoveTest {

    @TestConfiguration
    static class UserServiceImplGetTestContextConfiguration {

        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }
    }

    @Autowired
    private UserService userService;

    @MockBean
    private CompanyRepository companyRepository;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUp(){
        CompanyEntity company = new CompanyEntity();
        company.setName("Votit");

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setEmail("hasanheroglu@gmail.com");

        Optional<UserEntity> optionalUser = Optional.ofNullable(user);

        Mockito.when(companyRepository.findByName(company.getName()))
                .thenReturn(company);

        Mockito.when(userRepository.findById(user.getId()))
                .thenReturn(optionalUser);
    }

    @Test
    public void whenValidCompanyAndId_thenUserShouldBeRemoved(){
        Long id = 1L;
        String companyName = "Votit";

        Operation operation = userService.remove(companyName, id);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(true);
    }

    @Test
    public void whenInvalidCompany_thenUserShouldNotBeRemoved(){
        Long id = 1L;
        String companyName = "Apple";

        Operation operation = userService.remove(companyName, id);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(false);
    }

    @Test
    public void whenInvalidId_thenUserShouldNotBeRemoved(){
        Long id = 3L;
        String companyName = "Votit";

        Operation operation = userService.remove(companyName, id);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(false);
    }


}
