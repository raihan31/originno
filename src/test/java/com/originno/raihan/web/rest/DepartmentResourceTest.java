package com.originno.raihan.web.rest;

import com.originno.raihan.Application;
import com.originno.raihan.domain.Department;
import com.originno.raihan.repository.DepartmentRepository;
import com.originno.raihan.repository.search.DepartmentSearchRepository;
import com.originno.raihan.web.rest.dto.DepartmentDTO;
import com.originno.raihan.web.rest.mapper.DepartmentMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the DepartmentResource REST controller.
 *
 * @see DepartmentResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class DepartmentResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_LOGO = "AAAAA";
    private static final String UPDATED_LOGO = "BBBBB";
    private static final String DEFAULT_DESCRIPTIONS = "AAAAA";
    private static final String UPDATED_DESCRIPTIONS = "BBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_CREATED_AT_STR = dateTimeFormatter.format(DEFAULT_CREATED_AT);

    private static final ZonedDateTime DEFAULT_UPDATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_UPDATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_UPDATED_AT_STR = dateTimeFormatter.format(DEFAULT_UPDATED_AT);

    @Inject
    private DepartmentRepository departmentRepository;

    @Inject
    private DepartmentMapper departmentMapper;

    @Inject
    private DepartmentSearchRepository departmentSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDepartmentMockMvc;

    private Department department;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DepartmentResource departmentResource = new DepartmentResource();
        ReflectionTestUtils.setField(departmentResource, "departmentRepository", departmentRepository);
        ReflectionTestUtils.setField(departmentResource, "departmentMapper", departmentMapper);
        ReflectionTestUtils.setField(departmentResource, "departmentSearchRepository", departmentSearchRepository);
        this.restDepartmentMockMvc = MockMvcBuilders.standaloneSetup(departmentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        department = new Department();
        department.setName(DEFAULT_NAME);
        department.setLogo(DEFAULT_LOGO);
        department.setDescriptions(DEFAULT_DESCRIPTIONS);
        department.setCreatedAt(DEFAULT_CREATED_AT);
        department.setUpdatedAt(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createDepartment() throws Exception {
        int databaseSizeBeforeCreate = departmentRepository.findAll().size();

        // Create the Department
        DepartmentDTO departmentDTO = departmentMapper.departmentToDepartmentDTO(department);

        restDepartmentMockMvc.perform(post("/api/departments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(departmentDTO)))
                .andExpect(status().isCreated());

        // Validate the Department in the database
        List<Department> departments = departmentRepository.findAll();
        assertThat(departments).hasSize(databaseSizeBeforeCreate + 1);
        Department testDepartment = departments.get(departments.size() - 1);
        assertThat(testDepartment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDepartment.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testDepartment.getDescriptions()).isEqualTo(DEFAULT_DESCRIPTIONS);
        assertThat(testDepartment.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testDepartment.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = departmentRepository.findAll().size();
        // set the field null
        department.setName(null);

        // Create the Department, which fails.
        DepartmentDTO departmentDTO = departmentMapper.departmentToDepartmentDTO(department);

        restDepartmentMockMvc.perform(post("/api/departments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(departmentDTO)))
                .andExpect(status().isBadRequest());

        List<Department> departments = departmentRepository.findAll();
        assertThat(departments).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDepartments() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get all the departments
        restDepartmentMockMvc.perform(get("/api/departments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(department.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].logo").value(hasItem(DEFAULT_LOGO.toString())))
                .andExpect(jsonPath("$.[*].descriptions").value(hasItem(DEFAULT_DESCRIPTIONS.toString())))
                .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT_STR)))
                .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT_STR)));
    }

    @Test
    @Transactional
    public void getDepartment() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

        // Get the department
        restDepartmentMockMvc.perform(get("/api/departments/{id}", department.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(department.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.logo").value(DEFAULT_LOGO.toString()))
            .andExpect(jsonPath("$.descriptions").value(DEFAULT_DESCRIPTIONS.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT_STR))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT_STR));
    }

    @Test
    @Transactional
    public void getNonExistingDepartment() throws Exception {
        // Get the department
        restDepartmentMockMvc.perform(get("/api/departments/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDepartment() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

		int databaseSizeBeforeUpdate = departmentRepository.findAll().size();

        // Update the department
        department.setName(UPDATED_NAME);
        department.setLogo(UPDATED_LOGO);
        department.setDescriptions(UPDATED_DESCRIPTIONS);
        department.setCreatedAt(UPDATED_CREATED_AT);
        department.setUpdatedAt(UPDATED_UPDATED_AT);
        DepartmentDTO departmentDTO = departmentMapper.departmentToDepartmentDTO(department);

        restDepartmentMockMvc.perform(put("/api/departments")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(departmentDTO)))
                .andExpect(status().isOk());

        // Validate the Department in the database
        List<Department> departments = departmentRepository.findAll();
        assertThat(departments).hasSize(databaseSizeBeforeUpdate);
        Department testDepartment = departments.get(departments.size() - 1);
        assertThat(testDepartment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDepartment.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testDepartment.getDescriptions()).isEqualTo(UPDATED_DESCRIPTIONS);
        assertThat(testDepartment.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testDepartment.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void deleteDepartment() throws Exception {
        // Initialize the database
        departmentRepository.saveAndFlush(department);

		int databaseSizeBeforeDelete = departmentRepository.findAll().size();

        // Get the department
        restDepartmentMockMvc.perform(delete("/api/departments/{id}", department.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Department> departments = departmentRepository.findAll();
        assertThat(departments).hasSize(databaseSizeBeforeDelete - 1);
    }
}
