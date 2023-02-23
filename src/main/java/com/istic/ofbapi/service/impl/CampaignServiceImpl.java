package com.istic.ofbapi.service.impl;

import com.istic.ofbapi.exception.ResourceNotFoundException;
import com.istic.ofbapi.exception.UnauthorizedException;
import com.istic.ofbapi.mapper.CampaignMapper;
import com.istic.ofbapi.model.Campaign;
import com.istic.ofbapi.model.User;
import com.istic.ofbapi.model.role.RoleName;
import com.istic.ofbapi.payload.*;
import com.istic.ofbapi.repository.CampaignRepository;
import com.istic.ofbapi.repository.UserRepository;
import com.istic.ofbapi.security.UserPrincipal;
import com.istic.ofbapi.service.CampaignService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import static com.istic.ofbapi.utils.AppConstants.*;

@Service
@AllArgsConstructor
public class CampaignServiceImpl implements CampaignService {

    private final Logger LOGGER = LoggerFactory.getLogger(CampaignServiceImpl.class);
    private final CampaignRepository campaignRepository;
    private final CampaignMapper campaignMapper;
    private final UserRepository userRepository;


    @Override
    public CampaignResponse createCampaign(CampaingRequestOnPost campaingRequestOnPost, UserPrincipal currentUser) {
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException(USER, ID, currentUser.getId()));
        Campaign campaign = campaignMapper.campaignRequestOnPostToCampaign(campaingRequestOnPost);
        campaign.setUser(user);
        return  campaignMapper.campaignToCampaignResponse(campaignRepository.save(campaign));
    }

    @Override
    public CampaignResponse readCampaign(Long id) {
        Campaign campaign = campaignRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(SHEET, ID, id));
        return campaignMapper.campaignToCampaignResponse(campaign);
    }

    @Override
    public CampaignResponse updateCampaign(Long id, CampaignRequestOnPut campaignRequestOnPut, UserPrincipal currentUser) {
        Campaign campaign = campaignRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(CAMPAIGN, ID, id));

        if(campaign.getUser().getId().equals(currentUser.getId())
            || currentUser.getAuthorities().contains(
                    new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))){

            Campaign campaignUpdate = campaignMapper.campaignRequestOnPutToCampaign(campaignRequestOnPut);

            campaignUpdate.setDescription(campaign.getDescription());
            campaignUpdate.setEndDate(campaign.getEndDate());

            return campaignMapper.campaignToCampaignResponse(campaignRepository.save(campaign));
        }
        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You don't have permission to edit this sheet");
        throw new UnauthorizedException(apiResponse);
    }

    @Override
    public ApiResponse deleteCampaign(Long id, UserPrincipal currentUser) {
        Campaign campaign = campaignRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(CAMPAIGN, ID, id));

        if(campaign.getUser().getId().equals(currentUser.getId())
                || currentUser.getAuthorities().contains(
                new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))){
            campaignRepository.delete(campaign);
            return new ApiResponse(Boolean.TRUE, "You successfully deleted sheet");

        }
        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You don't have permission to delete this sheet");
        throw new UnauthorizedException(apiResponse);    }
}
