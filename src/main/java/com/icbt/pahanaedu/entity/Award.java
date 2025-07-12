package com.icbt.pahanaedu.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "awards")
@Data
public class Award extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "award_id")
    private Long awardId;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    @Column(name = "award_name", nullable = false)
    private String awardName;
    @Column(name = "award_description", nullable = false)
    private String awardDescription;

}
