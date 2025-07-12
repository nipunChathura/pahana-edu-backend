package com.icbt.pahanaedu.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "books")
@Data
public class Book extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long bookId;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "language", nullable = false)
    private String language;
    @Column(name = "author", nullable = false)
    private String author;
    @Column(name = "publisher", nullable = false)
    private String publisher;
    @Column(name = "publish_date", nullable = false)
    private String publishDate;
    @Column(name = "isbn", nullable = false)
    private String isbn;
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    @Column(name = "quantity", nullable = false)
    private Long quantity;
    @Column(name = "imageUrl", nullable = false)
    private String imageUrl;
    @Column(name = "book_status")
    private String bookStatus;
}
