package com.istic.ofbapi.repository;

import com.istic.ofbapi.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    @Override
    List<Campaign> findAll();

    @Override
    List<Campaign> findAllById(Iterable<Long> longs);
}
