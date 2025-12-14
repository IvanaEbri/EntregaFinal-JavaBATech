package com.techlab.NookBooks.repository;

import com.techlab.NookBooks.model.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository1 extends JpaRepository<PurchaseOrder,Long> {
    //querys personalizadas

    // TODO: query methods: https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
    List<PurchaseOrder> findByClient_Id(Long clientId);

    List<PurchaseOrder> findByState (Integer state);
}