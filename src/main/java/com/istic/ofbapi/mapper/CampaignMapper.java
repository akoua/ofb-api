package com.istic.ofbapi.mapper;

import com.istic.ofbapi.model.Campaign;
import com.istic.ofbapi.payload.*;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CampaignMapper {
    Campaign campaignRequestOnPostToCampaign(CampaingRequestOnPost campaingRequestOnPost);

    CampaignResponse campaignToCampaignResponse(Campaign campaign);

    List<CampaignResponse> campaignsToCampaignResponses(List<Campaign> Campaigns);

    Campaign campaignRequestOnPutToCampaign(CampaignRequestOnPut campaignRequestOnPut);

}
