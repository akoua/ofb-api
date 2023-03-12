package com.istic.ofbapi.model.taxon;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "taxon")
public class Taxon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(name = "type")
    private TaxonType taxonType;

    @Column(name = "name")
    private String name;

    public Taxon(TaxonType taxonType,String name ){
        this.taxonType = taxonType;
        this.name = name;
    }
}
