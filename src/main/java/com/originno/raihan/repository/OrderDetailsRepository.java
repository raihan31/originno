package com.originno.raihan.repository;

import com.originno.raihan.domain.OrderDetails;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the OrderDetails entity.
 */
public interface OrderDetailsRepository extends JpaRepository<OrderDetails,Long> {

}
