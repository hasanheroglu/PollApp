package com.hasan.PollApp.UserServiceImplTests;

import com.hasan.PollApp.model.dao.user.TitleEntity;
import com.hasan.PollApp.model.dao.user.UserEntity;
import com.hasan.PollApp.model.repo.user.TitleRepository;
import com.hasan.PollApp.model.repo.user.UserRepository;
import com.hasan.PollApp.service.impl.user.UserServiceImpl;
import com.hasan.PollApp.service.user.UserService;
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

        TitleEntity title = new TitleEntity();
        title.setTitle("Manager");

        user.getTitles().add(title);

        Mockito.when(userRepository.findById(user.getId()))
                .thenReturn(optionalUser);

        Mockito.when(titleRepository.findByTitle(title.getTitle()))
                .thenReturn(title);
    }


    @Test
    public void whenValidTitleAndId_then_TitleShouldBeRemoved(){
        Long id = 1L;
        String title = "Manager";

        Operation operation = userService.removeTitle(id, title);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(true);
    }
}
