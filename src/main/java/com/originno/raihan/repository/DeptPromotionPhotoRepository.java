package com.originno.raihan.repository;

import com.originno.raihan.domain.DeptPromotionPhoto;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DeptPromotionPhoto entity.
 */
public interface DeptPromotionPhotoRepository extends JpaRepository<DeptPromotionPhoto,Long> {

}
