package com.istic.ofbapi.payload;

import com.istic.ofbapi.model.taxon.TaxonType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaxonResponse {

    private Long id;

    private TaxonType taxonType;

    private String name;
}
