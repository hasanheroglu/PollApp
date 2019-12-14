package com.hasan.PollApp.model.dto.poll;

import java.util.Date;

public class PollUpdateDto {
    private Date endDate;

    public PollUpdateDto() {
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
