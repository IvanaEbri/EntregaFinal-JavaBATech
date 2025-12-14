package com.techlab.NookBooks.repository;

import com.techlab.NookBooks.model.entity.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderLineRepository extends JpaRepository <OrderLine,Long> {

    List<OrderLine> findByPurchaseOrder_Id(Long orderId);
}
