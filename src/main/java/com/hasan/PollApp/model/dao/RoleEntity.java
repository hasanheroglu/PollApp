package com.hasan.PollApp.model.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RoleEntity {
    @Id
    private Long id;
    @Column
    private Long companyId;
    @Column
    private String title;
}
