package com.originno.raihan.repository.search;

import com.originno.raihan.domain.DeptPromotionPhoto;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the DeptPromotionPhoto entity.
 */
public interface DeptPromotionPhotoSearchRepository extends ElasticsearchRepository<DeptPromotionPhoto, Long> {
}
