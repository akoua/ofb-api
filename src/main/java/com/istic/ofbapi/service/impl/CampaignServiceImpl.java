package com.istic.ofbapi.service.impl;

import com.istic.ofbapi.exception.ResourceNotFoundException;
import com.istic.ofbapi.exception.UnauthorizedException;
import com.istic.ofbapi.mapper.CampaignMapper;
import com.istic.ofbapi.model.Area;
import com.istic.ofbapi.model.Campaign;
import com.istic.ofbapi.model.Sheet;
import com.istic.ofbapi.model.User;
import com.istic.ofbapi.model.role.RoleName;
import com.istic.ofbapi.model.taxon.Taxon;
import com.istic.ofbapi.model.taxon.TaxonType;
import com.istic.ofbapi.payload.*;
import com.istic.ofbapi.repository.CampaignRepository;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Calendar;
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


    @Override
    public CampaignResponse createCampaign(CampaingRequestOnPost campaingRequestOnPost, UserPrincipal currentUser) {
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException(USER, ID, currentUser.getId()));
        Campaign campaign = campaignMapper.campaignRequestOnPostToCampaign(campaingRequestOnPost);
        return  campaignMapper.campaignToCampaignResponse(campaignRepository.save(campaign));
    }

    @Override
    public PagedResponse<CampaignResponse> readCampaigns(Integer page, Integer size) {
/*

        if(campaignRepository.count() == 0){
            Campaign campaign = new Campaign();
            campaign.setArea(new Area("Rennes", "France", "RENNES"));
            campaign.setTitle("Lions");
            campaign.setDescription("Chasse aux lions");
            campaign.setStartDate(new Date());
            campaign.setEndDate(new Date());

            Taxon taxon = taxonRepository.save(new Taxon(TaxonType.ANIMAL, "Mamifere"));
            Taxon taxon2 =taxonRepository.save(new Taxon(TaxonType.PLANT, "Ovipare"));

            List<Taxon> listTaxon = new ArrayList<>();
            listTaxon.add(taxon);
            listTaxon.add(taxon2);

            campaign.setTaxon(listTaxon);

            campaignRepository.save(campaign);
        }

 */


        if(campaignRepository.count() == 0){
            //Pour une campagne

            Area area = new Area("Rennes", "Bretagne", "France");
            initializeCampaignsData("Xylocopes", "Les Xylocopes ou Abeilles charpentières " +
                    "forment un genre d'insectes hyménoptères de la famille des Apidés. Toutes les " +
                    "espèces d'abeilles de la tribu Xylocopini sont aujourd'hui classées dans ce genre.",
                    TaxonType.ANIMAL, "ovipares", area);

            Area area2 = new Area("Paris", "Ile-de-France", "France");
            initializeCampaignsData("Chêne blanc", "Le chêne blanc  est une espèce de " +
                    "chêne mesurant en général une trentaine de mètres",TaxonType.PLANT, "Cyclobalanopsis ", area2);

            Area area3 = new Area("Lille", "Hauts-de-France", "France");
            initializeCampaignsData("Quercus phellos", "Quercus phellos, le Chêne à" +
                    " feuilles de Saule, est une espèce d'arbres du genre Quercus et de la section Lobatae.",
                    TaxonType.PLANT, " Fagacées", area3);

            Area area4 = new Area("Toulouse", "Occitanie ", "France");
            initializeCampaignsData("Bourdons", "Les bourdons sont des insectes sociaux du " +
                    "genre Bombus et de la famille des Apidae. Importants pollinisateurs, les différentes " +
                    "espèces de bourdons se nourrissent du nectar des fleurs et récoltent le pollen pour " +
                    "nourrir leur larves",TaxonType.ANIMAL, "ovipares", area4);

            Area area5 = new Area("Orléans", "Centre-Val de Loire", "France");
            initializeCampaignsData("Berger allemand", "Le berger allemand est " +
                    "une race de chiens tirant son nom de son pays d'origine, l'Allemagne, où elle" +
                    " est apparue à la fin du XIXᵉ siècle",TaxonType.ANIMAL, "ammifère", area5);
        }


        AppUtils.validatePageNumberAndSize(page, size);
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_AT);
        Page<Campaign> campaignPage = campaignRepository.findAll(pageable);

        List<Campaign> campaigns = campaignPage.getNumberOfElements() == 0 ? Collections.emptyList() : campaignPage.getContent();
        List<CampaignResponse> content = campaignMapper.campaignsToCampaignResponses(campaigns);

        return new PagedResponse<>(content, campaignPage.getNumber(), campaignPage.getSize(), campaignPage.getTotalElements(),
                campaignPage.getTotalPages(), campaignPage.isLast());
    }

    private void initializeCampaignsData(String title, String description, TaxonType taxonType, String taxonName, Area area){
        Campaign campaign = setCampaignValues(title, description);
        Taxon taxon = new Taxon(taxonType, taxonName);
        addCampaignToRepository(area, campaign,taxon);
    }

    private Campaign setCampaignValues(String title, String description){


        Campaign campaign = new Campaign();
        campaign.setTitle(title);
        campaign.setDescription(description);

        Calendar now = Calendar.getInstance();
        campaign.setStartDate(now.getTime());

        now.add(Calendar.MONTH, 3);
        campaign.setEndDate(now.getTime());

        return campaign;
    }

    private void addCampaignToRepository(Area area, Campaign campaign, Taxon taxon){

        List<Taxon> listTaxon = new ArrayList<>();
        listTaxon.add(taxon);
        taxonRepository.save(taxon);

        campaign.setArea(area);
        campaign.setTaxon(listTaxon);

        campaignRepository.save(campaign);




    }


    @Override
    public CampaignResponse updateCampaign(Long id, CampaignRequestOnPut campaignRequestOnPut, UserPrincipal currentUser) {
        /*


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
         */
        return null;
    }

    @Override
    public ApiResponse deleteCampaign(Long id, UserPrincipal currentUser) {
        /*
        Campaign campaign = campaignRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(CAMPAIGN, ID, id));

        if(campaign.getUser().getId().equals(currentUser.getId())
                || currentUser.getAuthorities().contains(
                new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))){
            campaignRepository.delete(campaign);
            return new ApiResponse(Boolean.TRUE, "You successfully deleted sheet");

        }
        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You don't have permission to delete this sheet");
        throw new UnauthorizedException(apiResponse);
          */
        return null;

    }

}
