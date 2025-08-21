package com.icbt.pahanaedu.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "promotion_book")
@Data
public class PromotionBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promotion_book_id")
    private Long promotionBookId;
    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    @Column(name = "status")
    private String status;
}
