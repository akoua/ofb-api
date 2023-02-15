package com.istic.ofbapi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SheetPutDto {
    private String description;
    private List<String> photosLinks;
    private Double longitude;
    private Double latitude;
}