package com.istic.ofbapi.service.impl;

import com.istic.ofbapi.exception.ResourceNotFoundException;
import com.istic.ofbapi.mapper.CampaignMapper;
import com.istic.ofbapi.model.Area;
import com.istic.ofbapi.model.Campaign;
import com.istic.ofbapi.model.Sheet;
import com.istic.ofbapi.model.User;
import com.istic.ofbapi.model.taxon.Taxon;
import com.istic.ofbapi.model.taxon.TaxonType;
import com.istic.ofbapi.payload.*;
import com.istic.ofbapi.repository.CampaignRepository;
import com.istic.ofbapi.repository.SheetRepository;
import com.istic.ofbapi.repository.TaxonRepository;
import com.istic.ofbapi.repository.UserRepository;
import com.istic.ofbapi.security.UserPrincipal;
import com.istic.ofbapi.service.CampaignService;
import com.istic.ofbapi.utils.AppUtils;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.istic.ofbapi.utils.AppConstants.*;

@Service
@AllArgsConstructor
public class CampaignServiceImpl implements CampaignService {

    private final Logger LOGGER = LoggerFactory.getLogger(CampaignServiceImpl.class);
    private final CampaignRepository campaignRepository;
    private final CampaignMapper campaignMapper;
    private final UserRepository userRepository;
    private final TaxonRepository taxonRepository;

    private final SheetRepository sheetRepository;

    @Override
    public CampaignResponse createCampaign(CampaingRequestOnPost campaingRequestOnPost, UserPrincipal currentUser) {
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException(USER, ID, currentUser.getId()));
        Campaign campaign = campaignMapper.campaignRequestOnPostToCampaign(campaingRequestOnPost);
        return campaignMapper.map(campaignRepository.save(campaign));
    }

    @Override
    public PagedResponse<CampaignResponse> readCampaigns(UserPrincipal currentUser, Integer page, Integer size) {
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException(USER, ID, currentUser.getId()));

        initTestData(user);
        
        AppUtils.validatePageNumberAndSize(page, size);
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_AT);
        Page<Campaign> campaignPage = campaignRepository.findAll(pageable);

        List<Campaign> campaigns = campaignPage.getNumberOfElements() == 0 ? Collections.emptyList() : campaignPage.getContent();
        List<CampaignResponse> content = campaignMapper.campaignsToCampaignResponses(campaigns);

        return new PagedResponse<>(content, campaignPage.getNumber(), campaignPage.getSize(), campaignPage.getTotalElements(),
                campaignPage.getTotalPages(), campaignPage.isLast());
    }

    private void initTestData(User user) {
        if (campaignRepository.count() == 0) {
            Area area = new Area("Rennes", "Bretagne", "France");

            Taxon taxon = taxonRepository.save(new Taxon(TaxonType.PLANT, "Mon taxon"));

            List<Taxon> taxons = Collections.singletonList(taxon);

            Campaign c1 = new Campaign("Ma campagne", "Description", taxons, area, new Date(2023, 12, 12), new Date(2023, 12, 12));
            Campaign c2 = new Campaign("Ma campagne2", "Description2", taxons, area, new Date(2023, 11, 12), new Date(2023, 12, 12));

            campaignRepository.saveAll(Arrays.asList(c1, c2));

            List<String> photos = Arrays.asList("https://jardinage.lemonde.fr/images/dossiers/categories3/racedecien-083123-650-325.jpg", "https://media.istockphoto.com/id/1213516345/fr/photo/crazy-regardant-le-chien-de-collie-de-fronti%C3%A8re-noir-et-blanc-disent-regardant-attentivement.jpg?s=612x612&w=0&k=20&c=5PoFiTkaUzbpcLymFyKop3vbkodHksibg3dLPf7UNBg=");

            Sheet sheet = new Sheet("Mon sheet", c1, user, "Description", photos, -1.677793, 48.117268, null);

            sheetRepository.save(sheet);
        }
    }


    @Override
    public CampaignResponse updateCampaign(Long id, CampaignRequestOnPut campaignRequestOnPut, UserPrincipal currentUser) {
        return null;
    }

    @Override
    public ApiResponse deleteCampaign(Long id, UserPrincipal currentUser) {
        return null;

    }

}
