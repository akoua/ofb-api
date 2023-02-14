package com.istic.ofbapi.payload;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
public class SheetPostDto {
    private Long campaignId;
    private Long userId;
    private String description;
    private List<String> photosLinks;
    private Double longitude;
    private Double latitude;
}
