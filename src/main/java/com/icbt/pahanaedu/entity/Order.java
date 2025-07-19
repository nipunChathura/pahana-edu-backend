package com.icbt.pahanaedu.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "orders")
@Data
public class Order extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    @Column(name = "order_date", nullable = false)
    private Date orderDate;
    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;
    @Column(name = "discount_amount")
    private BigDecimal discountAmount;
    @Column(name = "paid_amount", nullable = false)
    private BigDecimal paidAmount;
    @Column(name = "payment_type", nullable = false)
    private String paymentType;
}
