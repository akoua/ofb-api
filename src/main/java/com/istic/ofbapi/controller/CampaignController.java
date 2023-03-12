package com.istic.ofbapi.controller;

import com.istic.ofbapi.payload.*;
import com.istic.ofbapi.security.CurrentUser;
import com.istic.ofbapi.security.UserPrincipal;
import com.istic.ofbapi.service.CampaignService;
import com.istic.ofbapi.utils.AppConstants;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class CampaignController {

    private CampaignService campaignService;


    @PostMapping("/v1/campaigns")
    private ResponseEntity<CampaignResponse> createCampaign(@RequestBody @Valid CampaingRequestOnPost campaingRequestOnPost,
                                                            @CurrentUser UserPrincipal currentUser) {
        return new ResponseEntity<>(campaignService.createCampaign(campaingRequestOnPost, currentUser),HttpStatus.CREATED);
    }

    @GetMapping("/v1/campaigns")
    private ResponseEntity<PagedResponse<CampaignResponse>> read(@RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                                  @RequestParam(value = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size){

        PagedResponse<CampaignResponse> campaignResponse = campaignService.readCampaigns(page,size);
        return new ResponseEntity<>(campaignResponse, HttpStatus.OK);
    }
}
