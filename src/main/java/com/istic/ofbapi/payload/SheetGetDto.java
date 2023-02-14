package com.istic.ofbapi.payload;

import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class SheetGetDto {
    private Long id;
    private Long campaignId;
    private Long userId;
    private String description;
    private List<String> photosLinks;
    private Double longitude;
    private Double latitude;
    private Date createdAt;
    private Date updatedAt;

}
