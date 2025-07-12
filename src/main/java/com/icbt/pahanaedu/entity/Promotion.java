package com.icbt.pahanaedu.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "promotions")
@Data
public class Promotion extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promotion_id")
    private Long promotionId;
    @Column(name = "promotion_name", nullable = false)
    private String promotionName;
    @Column(name = "promotion_start_date", nullable = false)
    private Date promotionStartDate;
    @Column(name = "promotion_end_date", nullable = false)
    private Date promotionEndDate;
    @Column(name = "promotion_type", nullable = false)
    private String promotionType;
    @Column(name = "promotion_price", nullable = false)
    private BigDecimal promotionPrice;
    @Column(name = "priority", nullable = false)
    private Integer priority;
    @Column(name = "promotion_status", nullable = false)
    private String promotionStatus;
}
