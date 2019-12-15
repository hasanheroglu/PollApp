package com.hasan.PollApp.PollServiceImplTests;

import com.hasan.PollApp.model.dao.poll.OptionEntity;
import com.hasan.PollApp.model.dao.poll.PollEntity;
import com.hasan.PollApp.model.dao.poll.UserVoteEntity;
import com.hasan.PollApp.model.dao.user.UserEntity;
import com.hasan.PollApp.model.dto.poll.VoteDto;
import com.hasan.PollApp.model.repo.poll.OptionRepository;
import com.hasan.PollApp.model.repo.poll.PollRepository;
import com.hasan.PollApp.model.repo.poll.UserVoteRepository;
import com.hasan.PollApp.model.repo.user.UserRepository;
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

import javax.swing.text.html.Option;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PollServiceImplVoteTest {

    @TestConfiguration
    static class PollServiceImplVoteTestContextConfiguration {

        @Bean
        public PollService pollService() {
            return new PollServiceImpl();
        }
    }

    @Autowired
    private PollService pollService;

    @MockBean
    public PollRepository pollRepository;

    @MockBean
    public UserRepository userRepository;

    @MockBean
    public OptionRepository optionRepository;

    @MockBean
    public UserVoteRepository userVoteRepository;

    @Before
    public void setUp(){
        PollEntity poll = new PollEntity();
        poll.setId(1L);
        poll.setTitle("What do you want to eat?");
        poll.setMaxSelectionCount(1);
        poll.setType("Standard");

        OptionEntity option_1 = new OptionEntity();
        option_1.setId(1L);
        option_1.setBody("Spaghetti");
        OptionEntity option_2 = new OptionEntity();
        option_2.setId(2L);
        option_2.setBody("Kebab");

        poll.getOptions().add(option_1);
        poll.getOptions().add(option_2);

        UserEntity user_1 = new UserEntity();
        user_1.setId(1L);
        user_1.setEmail("hasanheroglu@gmail.com");

        UserEntity user_2 = new UserEntity();
        user_2.setId(3L);
        user_2.setEmail("berkkaanbilir@gmail.com");

        UserEntity user_3 = new UserEntity();
        user_3.setId(2L);
        user_3.setEmail("ilmiyepasaoglu@gmail.com");

        poll.getUsers().add(user_1);

        UserVoteEntity userVote_1 = new UserVoteEntity();
        userVote_1.setUser(user_1);
        userVote_1.setPoll(poll);

        UserVoteEntity userVote_2 = new UserVoteEntity();
        userVote_2.setUser(user_3);
        userVote_2.setPoll(poll);
        userVote_2.setVoted(true);

        poll.getVotes().add(userVote_1);
        poll.getVotes().add(userVote_2);

        Mockito.when(pollRepository.findById(poll.getId()))
                .thenReturn(Optional.ofNullable(poll));

        Mockito.when(optionRepository.findById(option_1.getId()))
                .thenReturn(Optional.ofNullable(option_1));

        Mockito.when(optionRepository.findById(option_2.getId()))
                .thenReturn(Optional.ofNullable(option_2));

        Mockito.when(userRepository.findById(user_1.getId()))
                .thenReturn(Optional.ofNullable(user_1));

        Mockito.when(userRepository.findById(user_2.getId()))
                .thenReturn(Optional.ofNullable(user_2));

        Mockito.when(userRepository.findById(user_3.getId()))
                .thenReturn(Optional.ofNullable(user_3));

        Mockito.when(userVoteRepository.findById(userVote_1.getId()))
                .thenReturn(Optional.ofNullable(userVote_1));

        Mockito.when(userVoteRepository.findById(userVote_2.getId()))
                .thenReturn(Optional.ofNullable(userVote_2));
    }

    @Test
    public void whenValidPoll_thenVoteShouldBeSuccessful(){
        VoteDto voteDto = new VoteDto();
        voteDto.setPollId(1L);
        voteDto.setVoterId(1L);
        voteDto.getOptionIds().add(1L);
        voteDto.getOptionIds().add(2L);
        voteDto.getVotePoints().add(1);
        voteDto.getVotePoints().add(0);

        Operation operation = pollService.vote(voteDto);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(true);
    }

    @Test
    public void whenInvalidPollId_thenVoteShouldNotBeSuccessful(){
        VoteDto voteDto = new VoteDto();
        voteDto.setPollId(2L);
        voteDto.setVoterId(1L);
        voteDto.getOptionIds().add(1L);
        voteDto.getOptionIds().add(2L);
        voteDto.getVotePoints().add(1);
        voteDto.getVotePoints().add(0);

        Operation operation = pollService.vote(voteDto);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(false);
    }

    @Test
    public void whenUserDoesNotExist_thenVoteShouldNotBeSuccessful(){
        VoteDto voteDto = new VoteDto();
        voteDto.setPollId(1L);
        voteDto.setVoterId(10L);
        voteDto.getOptionIds().add(1L);
        voteDto.getOptionIds().add(2L);
        voteDto.getVotePoints().add(1);
        voteDto.getVotePoints().add(0);

        Operation operation = pollService.vote(voteDto);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(false);
    }

    @Test
    public void whenInvalidUserId_thenVoteShouldNotBeSuccessful(){
        VoteDto voteDto = new VoteDto();
        voteDto.setPollId(1L);
        voteDto.setVoterId(3L);
        voteDto.getOptionIds().add(1L);
        voteDto.getOptionIds().add(2L);
        voteDto.getVotePoints().add(1);
        voteDto.getVotePoints().add(0);

        Operation operation = pollService.vote(voteDto);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(false);
    }

    @Test
    public void whenVotedBefore_thenVoteShouldNotBeSuccessful(){
        VoteDto voteDto = new VoteDto();
        voteDto.setPollId(1L);
        voteDto.setVoterId(2L);
        voteDto.getOptionIds().add(1L);
        voteDto.getOptionIds().add(2L);
        voteDto.getVotePoints().add(1);
        voteDto.getVotePoints().add(0);

        Operation operation = pollService.vote(voteDto);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(false);
    }

    @Test
    public void whenEmptyOptions_thenVoteShouldNotBeSuccessful(){
        VoteDto voteDto = new VoteDto();
        voteDto.setPollId(1L);
        voteDto.setVoterId(1L);
        voteDto.getVotePoints().add(1);
        voteDto.getVotePoints().add(0);

        Operation operation = pollService.vote(voteDto);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(false);
    }

    @Test
    public void whenInvalidOptions_thenVoteShouldNotBeSuccessful(){
        VoteDto voteDto = new VoteDto();
        voteDto.setPollId(1L);
        voteDto.setVoterId(1L);
        voteDto.getOptionIds().add(3L);
        voteDto.getOptionIds().add(4L);
        voteDto.getVotePoints().add(1);
        voteDto.getVotePoints().add(0);

        Operation operation = pollService.vote(voteDto);

        assertThat(operation.getWasSuccessful())
                .isEqualTo(false);
    }
}
