package com.istic.ofbapi.repository;

import com.istic.ofbapi.model.taxon.Taxon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaxonRepository extends JpaRepository<Taxon, Integer> {
    @Override
    List<Taxon> findAll();
}
