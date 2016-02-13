package com.originno.raihan.repository.search;

import com.originno.raihan.domain.ProPromotionPhoto;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the ProPromotionPhoto entity.
 */
public interface ProPromotionPhotoSearchRepository extends ElasticsearchRepository<ProPromotionPhoto, Long> {
}
