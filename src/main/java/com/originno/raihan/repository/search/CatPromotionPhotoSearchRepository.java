package com.originno.raihan.repository.search;

import com.originno.raihan.domain.CatPromotionPhoto;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the CatPromotionPhoto entity.
 */
public interface CatPromotionPhotoSearchRepository extends ElasticsearchRepository<CatPromotionPhoto, Long> {
}
