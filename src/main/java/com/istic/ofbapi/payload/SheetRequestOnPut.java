package com.istic.ofbapi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
public class SheetRequestOnPut {
    @NotBlank
    private String description;

    @NotEmpty
    private List<String> photosLinks;

    @NotNull
    private Double longitude;

    @NotNull
    private Double latitude;
}