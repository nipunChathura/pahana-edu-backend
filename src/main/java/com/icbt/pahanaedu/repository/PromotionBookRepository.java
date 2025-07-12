package com.icbt.pahanaedu.repository;

import com.icbt.pahanaedu.entity.Book;
import com.icbt.pahanaedu.entity.Promotion;
import com.icbt.pahanaedu.entity.PromotionBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionBookRepository extends JpaRepository<PromotionBook, Long> {
    PromotionBook findByBookAndPromotion(Book book, Promotion promotion);

    List<PromotionBook> findAllByPromotion(Promotion promotion);
}
