package com.originno.raihan.web.rest.mapper;

import com.originno.raihan.domain.*;
import com.originno.raihan.web.rest.dto.ProductsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Products and its DTO ProductsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductsMapper {

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    ProductsDTO productsToProductsDTO(Products products);

    @Mapping(source = "categoryId", target = "category")
    Products productsDTOToProducts(ProductsDTO productsDTO);

    default Category categoryFromId(Long id) {
        if (id == null) {
            return null;
        }
        Category category = new Category();
        category.setId(id);
        return category;
    }
}
