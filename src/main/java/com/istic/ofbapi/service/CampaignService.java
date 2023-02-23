package com.istic.ofbapi.service;

import com.istic.ofbapi.payload.*;
import com.istic.ofbapi.security.UserPrincipal;

public interface CampaignService {
    CampaignResponse createCampaign(CampaingRequestOnPost campaingRequestOnPost, UserPrincipal currentUser);
    CampaignResponse readCampaign(Long id);
    CampaignResponse updateCampaign(Long id, CampaignRequestOnPut campaignRequestOnPut , UserPrincipal currentUser);
    ApiResponse deleteCampaign(Long id,  UserPrincipal currentUser);
}
