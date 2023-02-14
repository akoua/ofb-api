package com.istic.ofbapi.model;

import com.istic.ofbapi.model.audit.DateAudit;
import lombok.*;

import javax.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String description;

    @ElementCollection
    private List<String> photoLinks;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private Double latitude;

}
