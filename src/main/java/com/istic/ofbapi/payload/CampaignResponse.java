package com.istic.ofbapi.payload;

import com.istic.ofbapi.model.Area;
import com.istic.ofbapi.model.taxon.Taxon;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class CampaignResponse {
    private Long id;
    private String title;
    private String description;
    private List<Taxon> taxons;
    private Area area;
    private Date startDate;
    private Date endDate;
    private Date createdAt;
    private Date updatedAt;
}
