package com.originno.raihan.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.originno.raihan.domain.Department;
import com.originno.raihan.repository.DepartmentRepository;
import com.originno.raihan.repository.search.DepartmentSearchRepository;
import com.originno.raihan.web.rest.util.HeaderUtil;
import com.originno.raihan.web.rest.util.PaginationUtil;
import com.originno.raihan.web.rest.dto.DepartmentDTO;
import com.originno.raihan.web.rest.mapper.DepartmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Department.
 */
@RestController
@RequestMapping("/api")
public class DepartmentResource {

    private final Logger log = LoggerFactory.getLogger(DepartmentResource.class);

    @Inject
    private DepartmentRepository departmentRepository;

    @Inject
    private DepartmentMapper departmentMapper;

    @Inject
    private DepartmentSearchRepository departmentSearchRepository;

    /**
     * POST  /departments -> Create a new department.
     */
    @RequestMapping(value = "/departments",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DepartmentDTO> createDepartment(@Valid @RequestBody DepartmentDTO departmentDTO) throws URISyntaxException {
        log.debug("REST request to save Department : {}", departmentDTO);
        if (departmentDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new department cannot already have an ID").body(null);
        }
        Department department = departmentMapper.departmentDTOToDepartment(departmentDTO);
        Department result = departmentRepository.save(department);
        departmentSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/departments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("department", result.getId().toString()))
            .body(departmentMapper.departmentToDepartmentDTO(result));
    }

    /**
     * PUT  /departments -> Updates an existing department.
     */
    @RequestMapping(value = "/departments",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DepartmentDTO> updateDepartment(@Valid @RequestBody DepartmentDTO departmentDTO) throws URISyntaxException {
        log.debug("REST request to update Department : {}", departmentDTO);
        if (departmentDTO.getId() == null) {
            return createDepartment(departmentDTO);
        }
        Department department = departmentMapper.departmentDTOToDepartment(departmentDTO);
        Department result = departmentRepository.save(department);
        departmentSearchRepository.save(department);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("department", departmentDTO.getId().toString()))
            .body(departmentMapper.departmentToDepartmentDTO(result));
    }

    /**
     * GET  /departments -> get all the departments.
     */
    @RequestMapping(value = "/departments",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments(Pageable pageable)
        throws URISyntaxException {
        Page<Department> page = departmentRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/departments");
        return new ResponseEntity<>(page.getContent().stream()
            .map(departmentMapper::departmentToDepartmentDTO)
            .collect(Collectors.toCollection(LinkedList::new)), headers, HttpStatus.OK);
    }

    /**
     * GET  /departments/:id -> get the "id" department.
     */
    @RequestMapping(value = "/departments/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DepartmentDTO> getDepartment(@PathVariable Long id) {
        log.debug("REST request to get Department : {}", id);
        return Optional.ofNullable(departmentRepository.findOne(id))
            .map(departmentMapper::departmentToDepartmentDTO)
            .map(departmentDTO -> new ResponseEntity<>(
                departmentDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /departments/:id -> delete the "id" department.
     */
    @RequestMapping(value = "/departments/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        log.debug("REST request to delete Department : {}", id);
        departmentRepository.delete(id);
        departmentSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("department", id.toString())).build();
    }

    /**
     * SEARCH  /_search/departments/:query -> search for the department corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/departments/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DepartmentDTO> searchDepartments(@PathVariable String query) {
        return StreamSupport
            .stream(departmentSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(departmentMapper::departmentToDepartmentDTO)
            .collect(Collectors.toList());
    }
}
