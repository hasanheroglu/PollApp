package com.softeng.votit.UserServiceImplTests;

import com.softeng.votit.model.dao.user.CompanyEntity;
import com.softeng.votit.model.dao.user.UserEntity;
import com.softeng.votit.model.dto.user.UserDto;
import com.softeng.votit.model.repo.user.AccessibilityRepository;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplAddTest {

    @TestConfiguration
    static class UserServiceImplAddTestContextConfiguration {

        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }
    }

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private CompanyRepository companyRepository;

    @MockBean
    private AccessibilityRepository accessibilityRepository;

    @Before
    public void setUp() {
        CompanyEntity company = new CompanyEntity();
        company.setName("Votit");

        Mockito.when(companyRepository.findByName(company.getName()))
                .thenReturn(company);
    }

    @Test
    public void whenValidUser_thenUserShouldBeAdded(){
        UserDto user = new UserDto();
        user.setName("Hasan");
        user.setSurname("Eroglu");
        user.setPassword("123456");
        user.setPasswordConfirmation("123456");
        user.setEmail("hasanheroglu@gmail.com");
        user.setPhoneNumber("05395979754");
        Operation<UserEntity> operation = userService.add("Votit", user);

        assertThat(operation.getOperationObject().getEmail())
                .isEqualTo(user.getEmail());
    }

    @Test
    public void whenInvalidUser_thenUserShouldNotBeAdded(){
        UserDto user = new UserDto();
        user.setName("Hasan");
        user.setSurname("Eroglu");
        user.setPassword("123456");
        user.setPasswordConfirmation("123456");
        user.setPhoneNumber("05395979754");
        Operation<UserEntity> operation = userService.add("Votit", user);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(false);
    }

    @Test
    public void whenCompanyIsNull_thenUserShouldNotBeAdded(){
        UserDto user = new UserDto();
        user.setName("Hasan");
        user.setSurname("Eroglu");
        user.setPassword("123456");
        user.setPasswordConfirmation("123456");
        user.setEmail("hasanheroglu@gmail.com");
        user.setPhoneNumber("05395979754");
        Operation<UserEntity> operation = userService.add("", user);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(false);
    }

    @Test
    public void whenUserExists_thenUserShouldNotBeAdded(){
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("hasanheroglu@gmail.com");

        Mockito.when(userRepository.findByEmail(userEntity.getEmail()))
                .thenReturn(userEntity);

        UserDto user = new UserDto();
        user.setName("Hasan");
        user.setSurname("Eroglu");
        user.setPassword("123456");
        user.setPasswordConfirmation("123456");
        user.setEmail("hasanheroglu@gmail.com");
        user.setPhoneNumber("05395979754");
        Operation<UserEntity> operation = userService.add("Votit", user);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(false);
    }
}
