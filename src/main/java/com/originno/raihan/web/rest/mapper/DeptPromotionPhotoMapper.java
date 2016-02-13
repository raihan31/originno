package com.originno.raihan.web.rest.mapper;

import com.originno.raihan.domain.*;
import com.originno.raihan.web.rest.dto.DeptPromotionPhotoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DeptPromotionPhoto and its DTO DeptPromotionPhotoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DeptPromotionPhotoMapper {

    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "department.name", target = "departmentName")
    DeptPromotionPhotoDTO deptPromotionPhotoToDeptPromotionPhotoDTO(DeptPromotionPhoto deptPromotionPhoto);

    @Mapping(source = "departmentId", target = "department")
    DeptPromotionPhoto deptPromotionPhotoDTOToDeptPromotionPhoto(DeptPromotionPhotoDTO deptPromotionPhotoDTO);

    default Department departmentFromId(Long id) {
        if (id == null) {
            return null;
        }
        Department department = new Department();
        department.setId(id);
        return department;
    }
}
