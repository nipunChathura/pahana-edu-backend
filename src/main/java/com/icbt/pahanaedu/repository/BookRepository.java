package com.icbt.pahanaedu.repository;

import com.icbt.pahanaedu.entity.Book;
import com.icbt.pahanaedu.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByBookStatus(String status);
    @Query(" select b from Book b " +
            " inner join PromotionBook pb on b.bookId = pb.book.bookId " +
            " inner join Promotion p on pb.promotion.promotionId = p.promotionId " +
            " where p.promotionId = :promotionId and b.bookStatus = 'ACTIVE'")
    List<Book> findAllByPromotion(@Param("promotionId") Long promotionId);

    @Query(" select b " +
            "    from Book b " +
            "    inner join Category c on b.category.categoryId = c.categoryId " +
            "    where c.categoryId = :categoryId and b.bookStatus = 'ACTIVE' ")
    List<Book> findAllByCategory(@Param("categoryId") Long categoryId);
}
