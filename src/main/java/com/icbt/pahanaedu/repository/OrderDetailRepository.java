package com.icbt.pahanaedu.repository;

import com.icbt.pahanaedu.entity.Book;
import com.icbt.pahanaedu.entity.Order;
import com.icbt.pahanaedu.entity.OrderDetails;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetails, Long> {
    List<OrderDetails> findByOrder(Order order);
    List<OrderDetails> findByOrder_OrderId(Long orderId);

    @Query("SELECT od.book " +
            "FROM OrderDetails od " +
            "WHERE od.order.createdDatetime >= :fromDate " +
            "AND od.order.createdDatetime <= :toDate " +
            "GROUP BY od.book " +
            "ORDER BY SUM(od.itemQuantity) DESC")
    List<Book> findTopSellingBooks(LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable);
}
