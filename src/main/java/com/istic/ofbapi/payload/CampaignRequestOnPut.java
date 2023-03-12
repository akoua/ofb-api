package com.istic.ofbapi.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

public class CampaignRequestOnPut {
    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private Instant startDate;

    @NotNull
    private Instant endDate;
}
