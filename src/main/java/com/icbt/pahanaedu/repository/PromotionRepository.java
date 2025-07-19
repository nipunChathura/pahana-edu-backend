package com.icbt.pahanaedu.repository;

import com.icbt.pahanaedu.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    @Query("select p from Promotion p " +
            "    inner join PromotionBook pb on p.promotionId = pb.promotionBookId " +
            "where pb.book.bookId = :bookId and p.promotionStatus = 'ACTIVE' " +
            "    and current_timestamp between p.promotionStartDate and p.promotionEndDate " +
            "    order by p.createdDatetime")
    List<Promotion> getActivePromotionByBookNow(@Param("bookId") Long bookId);
}
