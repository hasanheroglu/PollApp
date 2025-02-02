package com.softeng.votit.UserServiceImplTests;

import com.softeng.votit.model.dao.user.TitleEntity;
import com.softeng.votit.model.dao.user.UserEntity;
import com.softeng.votit.model.repo.user.TitleRepository;
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
public class UserServiceTitleRemoveTest {
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

    @MockBean
    private TitleRepository titleRepository;

    @Before
    public void setUp(){
        UserEntity user = new UserEntity();
        user.setId(1L);

        Optional<UserEntity> optionalUser = Optional.ofNullable(user);

        TitleEntity title_1 = new TitleEntity();
        title_1.setId(1L);
        title_1.setTitle("Manager");
        TitleEntity title_2 = new TitleEntity();
        title_2.setId(2L);
        title_2.setTitle("Developer");

        user.getTitles().add(title_1);

        Mockito.when(userRepository.findById(user.getId()))
                .thenReturn(optionalUser);

        Mockito.when(titleRepository.findById(title_1.getId()))
                .thenReturn(Optional.ofNullable(title_1));
        Mockito.when(titleRepository.findById(title_2.getId()))
                .thenReturn(Optional.ofNullable(title_2));
    }


    @Test
    public void whenValidTitleAndId_thenTitleShouldBeRemoved(){
        Long id = 1L;
        Long titleId = 1L;

        Operation operation = userService.removeTitle(id, titleId);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(true);
    }

    @Test
    public void whenInvalidId_thenTitleShouldNotBeRemoved(){
        Long id = 2L;
        Long titleId = 1L;

        Operation operation = userService.removeTitle(id, titleId);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(false);
    }

    @Test
    public void whenInvalidTitle_thenTitleShouldNotBeRemoved(){
        Long id = 1L;
        Long titleId = 3L;

        Operation operation = userService.removeTitle(id, titleId);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(false);
    }

    @Test
    public void whenTitleDoesNotExist_thenTitleShouldNotBeRemoved(){
        Long id = 1L;
        Long titleId = 2L;

        Operation operation = userService.removeTitle(id, titleId);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(false);
    }
}
