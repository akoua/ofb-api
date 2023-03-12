package com.istic.ofbapi.payload;

import com.istic.ofbapi.model.Area;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
public class CampaingRequestOnPost {


    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private Area area;

    @NotNull
    private Date startDate;

    @NotNull
    private Date endDate;

}
