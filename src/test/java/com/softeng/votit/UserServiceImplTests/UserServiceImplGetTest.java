package com.softeng.votit.UserServiceImplTests;

import com.softeng.votit.model.dao.user.UserEntity;
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
public class UserServiceImplGetTest {

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
    private UserRepository userRepository;

    @Before
    public void setUp() {
        UserEntity user = new UserEntity();
        String email = "hasanheroglu@gmail.com";
        user.setId(2L);
        user.setEmail(email);

        Optional<UserEntity> optionalUser = Optional.ofNullable(user);

        Mockito.when(userRepository.findByEmail(user.getEmail()))
                .thenReturn(user);

        Mockito.when(userRepository.findById(user.getId()))
                .thenReturn(optionalUser);
    }

    @Test
    public void whenValidEmail_thenUserShouldBeFound(){
        String email = "hasanheroglu@gmail.com";
        Operation<UserEntity> operation = userService.getByEmail(email);

        assertThat(operation.getOperationObject().getEmail())
                .isEqualTo(email);
    }

    @Test
    public void whenValidId_thenUserShouldBeFound(){
        Long id = 2L;
        Operation<UserEntity> operation = userService.get(id);

        assertThat(operation.getOperationObject().getId())
                .isEqualTo(id);
    }

    @Test
    public void whenInvalidId_thenUserShouldNotBeFound(){
        Long id = 3L;
        Operation<UserEntity> operation = userService.get(id);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(false);
    }

    @Test
    public void whenInvalidEmail_thenUserShouldNotBeFound(){
        String email = "ilmiyepasaoglu@gmail.com";
        Operation<UserEntity> operation = userService.getByEmail(email);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(false);
    }
}
