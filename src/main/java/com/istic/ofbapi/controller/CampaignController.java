package com.istic.ofbapi.controller;

import com.istic.ofbapi.payload.CampaignResponse;
import com.istic.ofbapi.payload.PagedResponse;
import com.istic.ofbapi.security.CurrentUser;
import com.istic.ofbapi.security.UserPrincipal;
import com.istic.ofbapi.service.CampaignService;
import com.istic.ofbapi.utils.AppConstants;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class CampaignController {

    private CampaignService campaignService;


    @GetMapping("/v1/campaigns")
    private ResponseEntity<PagedResponse<CampaignResponse>> read(@CurrentUser UserPrincipal currentUser, @RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                                                 @RequestParam(value = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {

        PagedResponse<CampaignResponse> campaignResponse = campaignService.readCampaigns(currentUser, page, size);
        return new ResponseEntity<>(campaignResponse, HttpStatus.OK);
    }
}
