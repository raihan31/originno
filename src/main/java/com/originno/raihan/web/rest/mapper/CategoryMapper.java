package com.originno.raihan.web.rest.mapper;

import com.originno.raihan.domain.*;
import com.originno.raihan.web.rest.dto.CategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Category and its DTO CategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategoryMapper {

    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "department.name", target = "departmentName")
    CategoryDTO categoryToCategoryDTO(Category category);

    @Mapping(source = "departmentId", target = "department")
    Category categoryDTOToCategory(CategoryDTO categoryDTO);

    default Department departmentFromId(Long id) {
        if (id == null) {
            return null;
        }
        Department department = new Department();
        department.setId(id);
        return department;
    }
}
