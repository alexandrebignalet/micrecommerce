package com.me.catalog.web.rest;

import com.me.catalog.CatalogApp;

import com.me.catalog.domain.Image;
import com.me.catalog.repository.ImageRepository;
import com.me.catalog.service.ImageService;
import com.me.catalog.service.dto.ImageDTO;
import com.me.catalog.service.mapper.ImageMapper;
import com.me.catalog.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Base64Utils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ImageResource REST controller.
 *
 * @see ImageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CatalogApp.class)
public class ImageResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_CONTENT_TYPE = "image/png";

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImageMapper imageMapper;

    @Autowired
    private ImageService imageService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restImageMockMvc;

    private Image image;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ImageResource imageResource = new ImageResource(imageService);
        this.restImageMockMvc = MockMvcBuilders.standaloneSetup(imageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Image createEntity() {
        Image image = new Image()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .file(DEFAULT_FILE)
            .fileContentType(DEFAULT_FILE_CONTENT_TYPE);
        return image;
    }

    @Before
    public void initTest() {
        imageRepository.deleteAll();
        image = createEntity();
    }

    @Test
    public void createImage() throws Exception {
        int databaseSizeBeforeCreate = imageRepository.findAll().size();

        // Create the Image
        ImageDTO imageDTO = imageMapper.toDto(image);
        restImageMockMvc.perform(post("/api/images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imageDTO)))
            .andExpect(status().isCreated());

        // Validate the Image in the database
        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeCreate + 1);
        Image testImage = imageList.get(imageList.size() - 1);
        assertThat(testImage.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testImage.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testImage.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testImage.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);
    }

    @Test
    public void createImageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = imageRepository.findAll().size();

        // Create the Image with an existing ID
        image.setId("existing_id");
        ImageDTO imageDTO = imageMapper.toDto(image);

        // An entity with an existing ID cannot be created, so this API call must fail
        restImageMockMvc.perform(post("/api/images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Image in the database
        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = imageRepository.findAll().size();
        // set the field null
        image.setName(null);

        // Create the Image, which fails.
        ImageDTO imageDTO = imageMapper.toDto(image);

        restImageMockMvc.perform(post("/api/images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imageDTO)))
            .andExpect(status().isBadRequest());

        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = imageRepository.findAll().size();
        // set the field null
        image.setDescription(null);

        // Create the Image, which fails.
        ImageDTO imageDTO = imageMapper.toDto(image);

        restImageMockMvc.perform(post("/api/images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imageDTO)))
            .andExpect(status().isBadRequest());

        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkFileIsRequired() throws Exception {
        int databaseSizeBeforeTest = imageRepository.findAll().size();
        // set the field null
        image.setFile(null);

        // Create the Image, which fails.
        ImageDTO imageDTO = imageMapper.toDto(image);

        restImageMockMvc.perform(post("/api/images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imageDTO)))
            .andExpect(status().isBadRequest());

        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllImages() throws Exception {
        // Initialize the database
        imageRepository.save(image);

        // Get all the imageList
        restImageMockMvc.perform(get("/api/images?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(image.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))));
    }

    @Test
    public void getImage() throws Exception {
        // Initialize the database
        imageRepository.save(image);

        // Get the image
        restImageMockMvc.perform(get("/api/images/{id}", image.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(image.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.fileContentType").value(DEFAULT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.file").value(Base64Utils.encodeToString(DEFAULT_FILE)));
    }

    @Test
    public void getNonExistingImage() throws Exception {
        // Get the image
        restImageMockMvc.perform(get("/api/images/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateImage() throws Exception {
        // Initialize the database
        imageRepository.save(image);
        int databaseSizeBeforeUpdate = imageRepository.findAll().size();

        // Update the image
        Image updatedImage = imageRepository.findOne(image.getId());
        updatedImage
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE);
        ImageDTO imageDTO = imageMapper.toDto(updatedImage);

        restImageMockMvc.perform(put("/api/images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imageDTO)))
            .andExpect(status().isOk());

        // Validate the Image in the database
        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeUpdate);
        Image testImage = imageList.get(imageList.size() - 1);
        assertThat(testImage.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testImage.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testImage.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testImage.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);
    }

    @Test
    public void updateNonExistingImage() throws Exception {
        int databaseSizeBeforeUpdate = imageRepository.findAll().size();

        // Create the Image
        ImageDTO imageDTO = imageMapper.toDto(image);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restImageMockMvc.perform(put("/api/images")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imageDTO)))
            .andExpect(status().isCreated());

        // Validate the Image in the database
        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteImage() throws Exception {
        // Initialize the database
        imageRepository.save(image);
        int databaseSizeBeforeDelete = imageRepository.findAll().size();

        // Get the image
        restImageMockMvc.perform(delete("/api/images/{id}", image.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Image> imageList = imageRepository.findAll();
        assertThat(imageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Image.class);
        Image image1 = new Image();
        image1.setId("id1");
        Image image2 = new Image();
        image2.setId(image1.getId());
        assertThat(image1).isEqualTo(image2);
        image2.setId("id2");
        assertThat(image1).isNotEqualTo(image2);
        image1.setId(null);
        assertThat(image1).isNotEqualTo(image2);
    }

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImageDTO.class);
        ImageDTO imageDTO1 = new ImageDTO();
        imageDTO1.setId("id1");
        ImageDTO imageDTO2 = new ImageDTO();
        assertThat(imageDTO1).isNotEqualTo(imageDTO2);
        imageDTO2.setId(imageDTO1.getId());
        assertThat(imageDTO1).isEqualTo(imageDTO2);
        imageDTO2.setId("id2");
        assertThat(imageDTO1).isNotEqualTo(imageDTO2);
        imageDTO1.setId(null);
        assertThat(imageDTO1).isNotEqualTo(imageDTO2);
    }
}
