package com.istic.ofbapi.payload;

import com.istic.ofbapi.model.Area;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignResponse {
    private Long id;
    private String title;
    private String description;
    private List<TaxonResponse> taxons;
    private Area area;
    private Date startDate;
    private Date endDate;
    private Date createdAt;
    private Date updatedAt;
}
