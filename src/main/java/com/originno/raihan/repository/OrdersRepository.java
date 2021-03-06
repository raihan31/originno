package com.originno.raihan.repository;

import com.originno.raihan.domain.Orders;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Orders entity.
 */
public interface OrdersRepository extends JpaRepository<Orders,Long> {

    @Query("select orders from Orders orders where orders.ordersProducts.login = ?#{principal.username}")
    List<Orders> findByOrdersProductsIsCurrentUser();

}
