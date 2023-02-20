package com.istic.ofbapi.controller;

import com.istic.ofbapi.service.CampaignService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/campaigns")
@AllArgsConstructor
public class CampaignController {

    private CampaignService campaignService;
}
