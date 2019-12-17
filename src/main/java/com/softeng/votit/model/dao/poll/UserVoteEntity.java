package com.softeng.votit.model.dao.poll;

import com.softeng.votit.model.dao.user.UserEntity;

import javax.persistence.*;

@Entity
public class UserVoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "poll_id")
    private PollEntity poll;
    @Column
    private boolean  voted;

    public UserVoteEntity() {
        this.voted = false;
    }

    public UserVoteEntity(UserEntity user, PollEntity poll) {
        this.user = user;
        this.poll = poll;
        this.voted = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public PollEntity getPoll() {
        return poll;
    }

    public void setPoll(PollEntity poll) {
        this.poll = poll;
    }

    public boolean isVoted() {
        return voted;
    }

    public void setVoted(boolean voted) {
        this.voted = voted;
    }
}
