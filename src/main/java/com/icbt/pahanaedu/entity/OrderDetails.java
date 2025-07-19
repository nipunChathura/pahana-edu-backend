package com.icbt.pahanaedu.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "order_details")
@Data
public class OrderDetails extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Long orderDetailId;
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
    @Column(name = "item_price", nullable = false)
    private BigDecimal itemPrice;
    @Column(name = "item_quantity", nullable = false)
    private Integer itemQuantity;
    @Column(name = "discount_price")
    private BigDecimal discountPrice;
    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;
}
