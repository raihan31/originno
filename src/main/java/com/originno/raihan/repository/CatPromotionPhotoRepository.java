package com.originno.raihan.repository;

import com.originno.raihan.domain.CatPromotionPhoto;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CatPromotionPhoto entity.
 */
public interface CatPromotionPhotoRepository extends JpaRepository<CatPromotionPhoto,Long> {

}
