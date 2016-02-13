package com.originno.raihan.repository.search;

import com.originno.raihan.domain.UserReviews;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the UserReviews entity.
 */
public interface UserReviewsSearchRepository extends ElasticsearchRepository<UserReviews, Long> {
}
