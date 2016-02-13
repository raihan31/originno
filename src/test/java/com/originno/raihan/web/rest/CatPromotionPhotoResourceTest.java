package com.originno.raihan.web.rest;

import com.originno.raihan.Application;
import com.originno.raihan.domain.CatPromotionPhoto;
import com.originno.raihan.repository.CatPromotionPhotoRepository;
import com.originno.raihan.repository.search.CatPromotionPhotoSearchRepository;
import com.originno.raihan.web.rest.dto.CatPromotionPhotoDTO;
import com.originno.raihan.web.rest.mapper.CatPromotionPhotoMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the CatPromotionPhotoResource REST controller.
 *
 * @see CatPromotionPhotoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CatPromotionPhotoResourceTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_PHOTO = "AAAAA";
    private static final String UPDATED_PHOTO = "BBBBB";
    private static final String DEFAULT_DESCRIPTIONS = "AAAAA";
    private static final String UPDATED_DESCRIPTIONS = "BBBBB";

    @Inject
    private CatPromotionPhotoRepository catPromotionPhotoRepository;

    @Inject
    private CatPromotionPhotoMapper catPromotionPhotoMapper;

    @Inject
    private CatPromotionPhotoSearchRepository catPromotionPhotoSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCatPromotionPhotoMockMvc;

    private CatPromotionPhoto catPromotionPhoto;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CatPromotionPhotoResource catPromotionPhotoResource = new CatPromotionPhotoResource();
        ReflectionTestUtils.setField(catPromotionPhotoResource, "catPromotionPhotoRepository", catPromotionPhotoRepository);
        ReflectionTestUtils.setField(catPromotionPhotoResource, "catPromotionPhotoMapper", catPromotionPhotoMapper);
        ReflectionTestUtils.setField(catPromotionPhotoResource, "catPromotionPhotoSearchRepository", catPromotionPhotoSearchRepository);
        this.restCatPromotionPhotoMockMvc = MockMvcBuilders.standaloneSetup(catPromotionPhotoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        catPromotionPhoto = new CatPromotionPhoto();
        catPromotionPhoto.setName(DEFAULT_NAME);
        catPromotionPhoto.setPhoto(DEFAULT_PHOTO);
        catPromotionPhoto.setDescriptions(DEFAULT_DESCRIPTIONS);
    }

    @Test
    @Transactional
    public void createCatPromotionPhoto() throws Exception {
        int databaseSizeBeforeCreate = catPromotionPhotoRepository.findAll().size();

        // Create the CatPromotionPhoto
        CatPromotionPhotoDTO catPromotionPhotoDTO = catPromotionPhotoMapper.catPromotionPhotoToCatPromotionPhotoDTO(catPromotionPhoto);

        restCatPromotionPhotoMockMvc.perform(post("/api/catPromotionPhotos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(catPromotionPhotoDTO)))
                .andExpect(status().isCreated());

        // Validate the CatPromotionPhoto in the database
        List<CatPromotionPhoto> catPromotionPhotos = catPromotionPhotoRepository.findAll();
        assertThat(catPromotionPhotos).hasSize(databaseSizeBeforeCreate + 1);
        CatPromotionPhoto testCatPromotionPhoto = catPromotionPhotos.get(catPromotionPhotos.size() - 1);
        assertThat(testCatPromotionPhoto.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCatPromotionPhoto.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testCatPromotionPhoto.getDescriptions()).isEqualTo(DEFAULT_DESCRIPTIONS);
    }

    @Test
    @Transactional
    public void getAllCatPromotionPhotos() throws Exception {
        // Initialize the database
        catPromotionPhotoRepository.saveAndFlush(catPromotionPhoto);

        // Get all the catPromotionPhotos
        restCatPromotionPhotoMockMvc.perform(get("/api/catPromotionPhotos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(catPromotionPhoto.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].photo").value(hasItem(DEFAULT_PHOTO.toString())))
                .andExpect(jsonPath("$.[*].descriptions").value(hasItem(DEFAULT_DESCRIPTIONS.toString())));
    }

    @Test
    @Transactional
    public void getCatPromotionPhoto() throws Exception {
        // Initialize the database
        catPromotionPhotoRepository.saveAndFlush(catPromotionPhoto);

        // Get the catPromotionPhoto
        restCatPromotionPhotoMockMvc.perform(get("/api/catPromotionPhotos/{id}", catPromotionPhoto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(catPromotionPhoto.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.photo").value(DEFAULT_PHOTO.toString()))
            .andExpect(jsonPath("$.descriptions").value(DEFAULT_DESCRIPTIONS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCatPromotionPhoto() throws Exception {
        // Get the catPromotionPhoto
        restCatPromotionPhotoMockMvc.perform(get("/api/catPromotionPhotos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCatPromotionPhoto() throws Exception {
        // Initialize the database
        catPromotionPhotoRepository.saveAndFlush(catPromotionPhoto);

		int databaseSizeBeforeUpdate = catPromotionPhotoRepository.findAll().size();

        // Update the catPromotionPhoto
        catPromotionPhoto.setName(UPDATED_NAME);
        catPromotionPhoto.setPhoto(UPDATED_PHOTO);
        catPromotionPhoto.setDescriptions(UPDATED_DESCRIPTIONS);
        CatPromotionPhotoDTO catPromotionPhotoDTO = catPromotionPhotoMapper.catPromotionPhotoToCatPromotionPhotoDTO(catPromotionPhoto);

        restCatPromotionPhotoMockMvc.perform(put("/api/catPromotionPhotos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(catPromotionPhotoDTO)))
                .andExpect(status().isOk());

        // Validate the CatPromotionPhoto in the database
        List<CatPromotionPhoto> catPromotionPhotos = catPromotionPhotoRepository.findAll();
        assertThat(catPromotionPhotos).hasSize(databaseSizeBeforeUpdate);
        CatPromotionPhoto testCatPromotionPhoto = catPromotionPhotos.get(catPromotionPhotos.size() - 1);
        assertThat(testCatPromotionPhoto.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCatPromotionPhoto.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testCatPromotionPhoto.getDescriptions()).isEqualTo(UPDATED_DESCRIPTIONS);
    }

    @Test
    @Transactional
    public void deleteCatPromotionPhoto() throws Exception {
        // Initialize the database
        catPromotionPhotoRepository.saveAndFlush(catPromotionPhoto);

		int databaseSizeBeforeDelete = catPromotionPhotoRepository.findAll().size();

        // Get the catPromotionPhoto
        restCatPromotionPhotoMockMvc.perform(delete("/api/catPromotionPhotos/{id}", catPromotionPhoto.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CatPromotionPhoto> catPromotionPhotos = catPromotionPhotoRepository.findAll();
        assertThat(catPromotionPhotos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
