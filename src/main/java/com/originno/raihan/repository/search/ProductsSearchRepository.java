package com.originno.raihan.repository.search;

import com.originno.raihan.domain.Products;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Products entity.
 */
public interface ProductsSearchRepository extends ElasticsearchRepository<Products, Long> {
}
