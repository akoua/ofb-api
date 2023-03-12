package com.istic.ofbapi.service;

import com.istic.ofbapi.payload.*;
import com.istic.ofbapi.security.UserPrincipal;

public interface CampaignService {
    CampaignResponse createCampaign(CampaingRequestOnPost campaingRequestOnPost, UserPrincipal currentUser);
    PagedResponse<CampaignResponse> readCampaigns(Integer page, Integer size);
    CampaignResponse updateCampaign(Long id, CampaignRequestOnPut campaignRequestOnPut , UserPrincipal currentUser);
    ApiResponse deleteCampaign(Long id,  UserPrincipal currentUser);
}
