package com.originno.raihan.web.rest.mapper;

import com.originno.raihan.domain.*;
import com.originno.raihan.web.rest.dto.DepartmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Department and its DTO DepartmentDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DepartmentMapper {

    DepartmentDTO departmentToDepartmentDTO(Department department);

    Department departmentDTOToDepartment(DepartmentDTO departmentDTO);
}
