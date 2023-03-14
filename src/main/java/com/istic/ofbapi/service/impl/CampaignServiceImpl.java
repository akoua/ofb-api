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

            Taxon taxon = taxonRepository.save(new Taxon(TaxonType.ANIMAL, "Insecte"));
            Taxon taxon2 = taxonRepository.save(new Taxon(TaxonType.PLANT, "Dicotylédone"));

            List<Taxon> taxons = Collections.singletonList(taxon);
            List<Taxon> taxons2 = Collections.singletonList(taxon2);


            Campaign c1 = new Campaign("Campagne de recensement d'abeilles", "A la découverte des abeilles !", taxons, area, new Date(2023, 12, 12), new Date(2023, 12, 12));
            Campaign c2 = new Campaign("Chasses aux papillons", "Les papillons sont formidables", taxons, area, new Date(2023, 11, 12), new Date(2023, 12, 12));
            Campaign c3 = new Campaign("Trouve ton cactus", "Les cactus ça piquent !", taxons2, area, new Date(2023, 11, 12), new Date(2023, 12, 12));

            campaignRepository.saveAll(Arrays.asList(c1, c2, c3));

            List<String> photosBees = Arrays.asList("bee1.png", "bee2.png", "bee3.png");
            List<String> photosCactus = Arrays.asList("cactus.png");

            Sheet sheet = new Sheet("Une abeille européenne", c1, user, "Il est agréable de découvrir la beauté des insectes qui nous entourent. Les abeilles européennes font partie de ces créatures fascinantes. Avec leurs rayures noires et jaunes, elles sont facilement reconnaissables et très jolies à observer. Elles sont également importantes pour la pollinisation des plantes et pour maintenir l'équilibre de notre écosystème. C'est pourquoi il est important de les protéger et de préserver leur habitat naturel. La nature est pleine de merveilles, et apercevoir une abeille européenne est une expérience qui nous rappelle la beauté et la diversité de notre environnement.", photosBees, -1.677793, 48.117268, null);
            Sheet sheet2 = new Sheet("Très rare cactus de noël", c3, user, "La découverte d'une espèce rare de cactus de Noël est une expérience passionnante pour les amoureux de la nature. Ces plantes sont appréciées pour leurs fleurs colorées et leur capacité à fleurir pendant les mois d'hiver, ajoutant une touche de beauté à la saison des fêtes. La découverte d'une espèce rare de cactus de Noël est un événement spécial car cela peut aider à élargir notre compréhension de la diversité des plantes et de leur adaptation à des environnements différents. Cela peut également aider à sensibiliser à la nécessité de protéger et de préserver les habitats naturels des plantes. La nature est riche en trésors cachés, et la découverte d'une espèce rare de cactus de Noël est une occasion de célébrer cette richesse et de continuer à explorer les merveilles de notre monde naturel.", photosCactus, -1.677793, 48.117268, null);

            sheetRepository.saveAll(Arrays.asList(sheet, sheet2));
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
