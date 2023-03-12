package com.istic.ofbapi.mapper;

import com.istic.ofbapi.model.Campaign;
import com.istic.ofbapi.model.taxon.Taxon;
import com.istic.ofbapi.payload.CampaignRequestOnPut;
import com.istic.ofbapi.payload.CampaignResponse;
import com.istic.ofbapi.payload.CampaingRequestOnPost;
import com.istic.ofbapi.payload.TaxonResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CampaignMapper {
    Campaign campaignRequestOnPostToCampaign(CampaingRequestOnPost campaingRequestOnPost);

    //CampaignResponse campaignToCampaignResponse(Campaign campaign);

    List<CampaignResponse> campaignsToCampaignResponses(List<Campaign> Campaigns);

    Campaign campaignRequestOnPutToCampaign(CampaignRequestOnPut campaignRequestOnPut);

    TaxonResponse taxonToTaxonResponse(Taxon taxon);

    default List<TaxonResponse> taxonListToTaxonResponseList(List<Taxon> taxonList) {
        return taxonList.stream()
                .map(this::taxonToTaxonResponse)
                .collect(Collectors.toList());
    }

    default CampaignResponse map(Campaign campaign) {
        CampaignResponse response = new CampaignResponse();
        response.setId(campaign.getId());
        response.setTitle(campaign.getTitle());
        response.setDescription(campaign.getDescription());
        response.setArea(campaign.getArea());
        response.setStartDate(campaign.getStartDate());
        response.setEndDate(campaign.getEndDate());
        response.setTaxons(taxonListToTaxonResponseList(campaign.getTaxon()));
        response.setUpdatedAt(Date.from(campaign.getUpdatedAt()));
        response.setCreatedAt(Date.from(campaign.getCreatedAt()));
        return response;
    }
}
