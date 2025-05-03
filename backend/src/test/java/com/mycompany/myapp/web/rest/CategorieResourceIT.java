package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Categorie;
import com.mycompany.myapp.repository.CategorieRepository;
import com.mycompany.myapp.service.dto.CategorieDTO;
import com.mycompany.myapp.service.mapper.CategorieMapper;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CategorieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CategorieResourceIT {

    private static final String DEFAULT_NOM_CATEGORIE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_CATEGORIE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{idCategorie}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private CategorieMapper categorieMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategorieMockMvc;

    private Categorie categorie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Categorie createEntity(EntityManager em) {
        Categorie categorie = new Categorie().nomCategorie(DEFAULT_NOM_CATEGORIE);
        return categorie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Categorie createUpdatedEntity(EntityManager em) {
        Categorie categorie = new Categorie().nomCategorie(UPDATED_NOM_CATEGORIE);
        return categorie;
    }

    @BeforeEach
    public void initTest() {
        categorie = createEntity(em);
    }

    @Test
    @Transactional
    void createCategorie() throws Exception {
        int databaseSizeBeforeCreate = categorieRepository.findAll().size();
        // Create the Categorie
        CategorieDTO categorieDTO = categorieMapper.toDto(categorie);
        restCategorieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categorieDTO)))
            .andExpect(status().isCreated());

        // Validate the Categorie in the database
        List<Categorie> categorieList = categorieRepository.findAll();
        assertThat(categorieList).hasSize(databaseSizeBeforeCreate + 1);
        Categorie testCategorie = categorieList.get(categorieList.size() - 1);
        assertThat(testCategorie.getNomCategorie()).isEqualTo(DEFAULT_NOM_CATEGORIE);
    }

    @Test
    @Transactional
    void createCategorieWithExistingId() throws Exception {
        // Create the Categorie with an existing ID
        categorie.setIdCategorie(1L);
        CategorieDTO categorieDTO = categorieMapper.toDto(categorie);

        int databaseSizeBeforeCreate = categorieRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategorieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categorieDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Categorie in the database
        List<Categorie> categorieList = categorieRepository.findAll();
        assertThat(categorieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomCategorieIsRequired() throws Exception {
        int databaseSizeBeforeTest = categorieRepository.findAll().size();
        // set the field null
        categorie.setNomCategorie(null);

        // Create the Categorie, which fails.
        CategorieDTO categorieDTO = categorieMapper.toDto(categorie);

        restCategorieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categorieDTO)))
            .andExpect(status().isBadRequest());

        List<Categorie> categorieList = categorieRepository.findAll();
        assertThat(categorieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCategories() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList
        restCategorieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=idCategorie,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].idCategorie").value(hasItem(categorie.getIdCategorie().intValue())))
            .andExpect(jsonPath("$.[*].nomCategorie").value(hasItem(DEFAULT_NOM_CATEGORIE)));
    }

    @Test
    @Transactional
    void getCategorie() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get the categorie
        restCategorieMockMvc
            .perform(get(ENTITY_API_URL_ID, categorie.getIdCategorie()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.idCategorie").value(categorie.getIdCategorie().intValue()))
            .andExpect(jsonPath("$.nomCategorie").value(DEFAULT_NOM_CATEGORIE));
    }

    @Test
    @Transactional
    void getCategoriesByIdFiltering() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        Long id = categorie.getIdCategorie();

        defaultCategorieShouldBeFound("idCategorie.equals=" + id);
        defaultCategorieShouldNotBeFound("idCategorie.notEquals=" + id);

        defaultCategorieShouldBeFound("idCategorie.greaterThanOrEqual=" + id);
        defaultCategorieShouldNotBeFound("idCategorie.greaterThan=" + id);

        defaultCategorieShouldBeFound("idCategorie.lessThanOrEqual=" + id);
        defaultCategorieShouldNotBeFound("idCategorie.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCategoriesByNomCategorieIsEqualToSomething() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where nomCategorie equals to DEFAULT_NOM_CATEGORIE
        defaultCategorieShouldBeFound("nomCategorie.equals=" + DEFAULT_NOM_CATEGORIE);

        // Get all the categorieList where nomCategorie equals to UPDATED_NOM_CATEGORIE
        defaultCategorieShouldNotBeFound("nomCategorie.equals=" + UPDATED_NOM_CATEGORIE);
    }

    @Test
    @Transactional
    void getAllCategoriesByNomCategorieIsInShouldWork() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where nomCategorie in DEFAULT_NOM_CATEGORIE or UPDATED_NOM_CATEGORIE
        defaultCategorieShouldBeFound("nomCategorie.in=" + DEFAULT_NOM_CATEGORIE + "," + UPDATED_NOM_CATEGORIE);

        // Get all the categorieList where nomCategorie equals to UPDATED_NOM_CATEGORIE
        defaultCategorieShouldNotBeFound("nomCategorie.in=" + UPDATED_NOM_CATEGORIE);
    }

    @Test
    @Transactional
    void getAllCategoriesByNomCategorieIsNullOrNotNull() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where nomCategorie is not null
        defaultCategorieShouldBeFound("nomCategorie.specified=true");

        // Get all the categorieList where nomCategorie is null
        defaultCategorieShouldNotBeFound("nomCategorie.specified=false");
    }

    @Test
    @Transactional
    void getAllCategoriesByNomCategorieContainsSomething() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where nomCategorie contains DEFAULT_NOM_CATEGORIE
        defaultCategorieShouldBeFound("nomCategorie.contains=" + DEFAULT_NOM_CATEGORIE);

        // Get all the categorieList where nomCategorie contains UPDATED_NOM_CATEGORIE
        defaultCategorieShouldNotBeFound("nomCategorie.contains=" + UPDATED_NOM_CATEGORIE);
    }

    @Test
    @Transactional
    void getAllCategoriesByNomCategorieNotContainsSomething() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        // Get all the categorieList where nomCategorie does not contain DEFAULT_NOM_CATEGORIE
        defaultCategorieShouldNotBeFound("nomCategorie.doesNotContain=" + DEFAULT_NOM_CATEGORIE);

        // Get all the categorieList where nomCategorie does not contain UPDATED_NOM_CATEGORIE
        defaultCategorieShouldBeFound("nomCategorie.doesNotContain=" + UPDATED_NOM_CATEGORIE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCategorieShouldBeFound(String filter) throws Exception {
        restCategorieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=idCategorie,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].idCategorie").value(hasItem(categorie.getIdCategorie().intValue())))
            .andExpect(jsonPath("$.[*].nomCategorie").value(hasItem(DEFAULT_NOM_CATEGORIE)));

        // Check, that the count call also returns 1
        restCategorieMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=idCategorie,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCategorieShouldNotBeFound(String filter) throws Exception {
        restCategorieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=idCategorie,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCategorieMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=idCategorie,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCategorie() throws Exception {
        // Get the categorie
        restCategorieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCategorie() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        int databaseSizeBeforeUpdate = categorieRepository.findAll().size();

        // Update the categorie
        Categorie updatedCategorie = categorieRepository.findById(categorie.getIdCategorie()).orElseThrow();
        // Disconnect from session so that the updates on updatedCategorie are not directly saved in db
        em.detach(updatedCategorie);
        updatedCategorie.nomCategorie(UPDATED_NOM_CATEGORIE);
        CategorieDTO categorieDTO = categorieMapper.toDto(updatedCategorie);

        restCategorieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categorieDTO.getIdCategorie())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categorieDTO))
            )
            .andExpect(status().isOk());

        // Validate the Categorie in the database
        List<Categorie> categorieList = categorieRepository.findAll();
        assertThat(categorieList).hasSize(databaseSizeBeforeUpdate);
        Categorie testCategorie = categorieList.get(categorieList.size() - 1);
        assertThat(testCategorie.getNomCategorie()).isEqualTo(UPDATED_NOM_CATEGORIE);
    }

    @Test
    @Transactional
    void putNonExistingCategorie() throws Exception {
        int databaseSizeBeforeUpdate = categorieRepository.findAll().size();
        categorie.setIdCategorie(longCount.incrementAndGet());

        // Create the Categorie
        CategorieDTO categorieDTO = categorieMapper.toDto(categorie);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategorieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categorieDTO.getIdCategorie())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categorieDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Categorie in the database
        List<Categorie> categorieList = categorieRepository.findAll();
        assertThat(categorieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCategorie() throws Exception {
        int databaseSizeBeforeUpdate = categorieRepository.findAll().size();
        categorie.setIdCategorie(longCount.incrementAndGet());

        // Create the Categorie
        CategorieDTO categorieDTO = categorieMapper.toDto(categorie);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categorieDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Categorie in the database
        List<Categorie> categorieList = categorieRepository.findAll();
        assertThat(categorieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCategorie() throws Exception {
        int databaseSizeBeforeUpdate = categorieRepository.findAll().size();
        categorie.setIdCategorie(longCount.incrementAndGet());

        // Create the Categorie
        CategorieDTO categorieDTO = categorieMapper.toDto(categorie);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categorieDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Categorie in the database
        List<Categorie> categorieList = categorieRepository.findAll();
        assertThat(categorieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCategorieWithPatch() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        int databaseSizeBeforeUpdate = categorieRepository.findAll().size();

        // Update the categorie using partial update
        Categorie partialUpdatedCategorie = new Categorie();
        partialUpdatedCategorie.setIdCategorie(categorie.getIdCategorie());

        partialUpdatedCategorie.nomCategorie(UPDATED_NOM_CATEGORIE);

        restCategorieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategorie.getIdCategorie())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategorie))
            )
            .andExpect(status().isOk());

        // Validate the Categorie in the database
        List<Categorie> categorieList = categorieRepository.findAll();
        assertThat(categorieList).hasSize(databaseSizeBeforeUpdate);
        Categorie testCategorie = categorieList.get(categorieList.size() - 1);
        assertThat(testCategorie.getNomCategorie()).isEqualTo(UPDATED_NOM_CATEGORIE);
    }

    @Test
    @Transactional
    void fullUpdateCategorieWithPatch() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        int databaseSizeBeforeUpdate = categorieRepository.findAll().size();

        // Update the categorie using partial update
        Categorie partialUpdatedCategorie = new Categorie();
        partialUpdatedCategorie.setIdCategorie(categorie.getIdCategorie());

        partialUpdatedCategorie.nomCategorie(UPDATED_NOM_CATEGORIE);

        restCategorieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategorie.getIdCategorie())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategorie))
            )
            .andExpect(status().isOk());

        // Validate the Categorie in the database
        List<Categorie> categorieList = categorieRepository.findAll();
        assertThat(categorieList).hasSize(databaseSizeBeforeUpdate);
        Categorie testCategorie = categorieList.get(categorieList.size() - 1);
        assertThat(testCategorie.getNomCategorie()).isEqualTo(UPDATED_NOM_CATEGORIE);
    }

    @Test
    @Transactional
    void patchNonExistingCategorie() throws Exception {
        int databaseSizeBeforeUpdate = categorieRepository.findAll().size();
        categorie.setIdCategorie(longCount.incrementAndGet());

        // Create the Categorie
        CategorieDTO categorieDTO = categorieMapper.toDto(categorie);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategorieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, categorieDTO.getIdCategorie())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categorieDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Categorie in the database
        List<Categorie> categorieList = categorieRepository.findAll();
        assertThat(categorieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCategorie() throws Exception {
        int databaseSizeBeforeUpdate = categorieRepository.findAll().size();
        categorie.setIdCategorie(longCount.incrementAndGet());

        // Create the Categorie
        CategorieDTO categorieDTO = categorieMapper.toDto(categorie);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categorieDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Categorie in the database
        List<Categorie> categorieList = categorieRepository.findAll();
        assertThat(categorieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCategorie() throws Exception {
        int databaseSizeBeforeUpdate = categorieRepository.findAll().size();
        categorie.setIdCategorie(longCount.incrementAndGet());

        // Create the Categorie
        CategorieDTO categorieDTO = categorieMapper.toDto(categorie);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorieMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(categorieDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Categorie in the database
        List<Categorie> categorieList = categorieRepository.findAll();
        assertThat(categorieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCategorie() throws Exception {
        // Initialize the database
        categorieRepository.saveAndFlush(categorie);

        int databaseSizeBeforeDelete = categorieRepository.findAll().size();

        // Delete the categorie
        restCategorieMockMvc
            .perform(delete(ENTITY_API_URL_ID, categorie.getIdCategorie()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Categorie> categorieList = categorieRepository.findAll();
        assertThat(categorieList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
