package com.istic.ofbapi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
public class SheetRequestOnPost {

    @NotBlank
    private String title;

    @NotNull
    private Long campaignId;

    @NotBlank
    private String description;

    @NotEmpty
    private List<String> photoLinks;

    @NotNull
    private Double longitude;

    @NotNull
    private Double latitude;
}
