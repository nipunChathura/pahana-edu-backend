package com.icbt.pahanaedu.repository;

import com.icbt.pahanaedu.entity.Order;
import com.icbt.pahanaedu.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetails, Long> {
    List<OrderDetails> findByOrder(Order order);
    List<OrderDetails> findByOrder_OrderId(Long orderId);
}
