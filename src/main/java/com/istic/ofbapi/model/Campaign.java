package com.istic.ofbapi.model;

import com.istic.ofbapi.model.taxon.Taxon;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Campaign extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;


    @ManyToMany
    @JoinTable(
            name = "campaign_taxon",
            joinColumns = @JoinColumn(name = "campaign_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "taxon_id", referencedColumnName = "id"))
    private List<Taxon> taxon;

    @Column(nullable = false)
    private Area area;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    public Campaign(String title, String description, List<Taxon> taxon, Area area, Date startDate, Date endDate) {
        this.title = title;
        this.description = description;
        this.taxon = taxon;
        this.area = area;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
