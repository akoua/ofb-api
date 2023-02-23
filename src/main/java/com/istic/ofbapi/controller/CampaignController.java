package com.istic.ofbapi.controller;

import com.istic.ofbapi.payload.CampaignResponse;
import com.istic.ofbapi.payload.CampaingRequestOnPost;
import com.istic.ofbapi.payload.SheetRequestOnPost;
import com.istic.ofbapi.payload.SheetResponse;
import com.istic.ofbapi.security.CurrentUser;
import com.istic.ofbapi.security.UserPrincipal;
import com.istic.ofbapi.service.CampaignService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/campaigns")
@AllArgsConstructor
public class CampaignController {

    private CampaignService campaignService;

    @PostMapping("/v1/sheets")
    private ResponseEntity<CampaignResponse> createCampaign(@RequestBody @Valid CampaingRequestOnPost campaingRequestOnPost,
                                                            @CurrentUser UserPrincipal currentUser) {

        return new ResponseEntity<>(campaignService.createCampaign(campaingRequestOnPost, currentUser),HttpStatus.CREATED);
    }

}
