package com.sallesport.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cours_collectif")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoursCollectif {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(nullable = false, length = 50)
    private String type;

    @Column(nullable = false)
    private Integer capacite;

    @Column(nullable = false, length = 100)
    private String creneau;

    @Column(nullable = false, length = 50)
    private String salle;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "coach_id", nullable = false)
    private Coach coach;
}
