package com.icbt.pahanaedu.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "categories")
@Data
public class Category extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;
    @Column(name = "category_name", nullable = false)
    private String categoryName;
    @Column(name = "category_status", nullable = false)
    private String categoryStatus;
}
