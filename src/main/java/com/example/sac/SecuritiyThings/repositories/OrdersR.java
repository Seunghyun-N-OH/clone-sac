package com.example.sac.SecuritiyThings.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.example.sac.SecuritiyThings.entities.OrdersE;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersR extends JpaRepository<OrdersE, Long> {
    List<OrdersE> findByBuyerUserId(String string);

    Optional<OrdersE> findByMerchantUid(String merchantUid);

    List<OrdersE> findByName(String name);

    List<OrdersE> findByExpiryDateBefore(LocalDate target);
}
