package com.icbt.pahanaedu.repository;

import com.icbt.pahanaedu.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT DATE(o.createdDatetime) AS orderDate, COUNT(o) AS orderCount " +
            "FROM Order o " +
            "WHERE o.createdDatetime >= :fromDate " +
            "GROUP BY DATE(o.createdDatetime) " +
            "ORDER BY DATE(o.createdDatetime) ASC")
    List<Object[]> findDailyOrderCountSince(LocalDateTime fromDate);

    @Query("SELECT DATE(o.createdDatetime) AS orderDate, SUM(o.paidAmount) AS totalIncome " +
            "FROM Order o " +
            "WHERE o.createdDatetime >= :fromDate " +
            "GROUP BY DATE(o.createdDatetime) " +
            "ORDER BY DATE(o.createdDatetime) ASC")
    List<Object[]> findDailyIncomeSince(LocalDateTime fromDate);
}
