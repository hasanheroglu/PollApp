package com.hasan.PollApp.model.dto.user;

import java.util.Date;

public class CompanyUpdateDto {
    private String description;
    private Date establishmentDate;


    public CompanyUpdateDto() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getEstablishmentDate() {
        return establishmentDate;
    }

    public void setEstablishmentDate(Date establishmentDate) {
        this.establishmentDate = establishmentDate;
    }
}
