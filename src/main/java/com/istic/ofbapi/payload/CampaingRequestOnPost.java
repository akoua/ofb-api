package com.istic.ofbapi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@AllArgsConstructor
public class CampaingRequestOnPost {


    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private Instant startDate;

    @NotNull
    private Instant endDate;

}
