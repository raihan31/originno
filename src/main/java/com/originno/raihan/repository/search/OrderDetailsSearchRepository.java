package com.originno.raihan.repository.search;

import com.originno.raihan.domain.OrderDetails;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the OrderDetails entity.
 */
public interface OrderDetailsSearchRepository extends ElasticsearchRepository<OrderDetails, Long> {
}
