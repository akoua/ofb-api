package com.istic.ofbapi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class CampaignGetDto {
    private Long id;
    private String title;
    private Date startDate;
    private Date endDate;
    private Date createdAt;
    private Date updatedAt;
}
