package com.istic.ofbapi.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class SheetResponse {
    private Long id;
    private String title;
    private Long campaignId;
    private Long userId;
    private String description;
    private List<String> photoLinks;
    private Double longitude;
    private Double latitude;
    private Date createdAt;
    private Date updatedAt;

}
