package com.istic.ofbapi.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sheet")
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Sheet extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @ElementCollection
    private List<String> photoLinks;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private Double latitude;

    @OneToMany(mappedBy = "sheet", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    public Sheet(String title, Campaign campaign, User user, String description, List<String> photoLinks, Double longitude, Double latitude, List<Comment> comments) {
        this.title = title;
        this.campaign = campaign;
        this.user = user;
        this.description = description;
        this.photoLinks = photoLinks;
        this.longitude = longitude;
        this.latitude = latitude;
        this.comments = comments;
    }
}
