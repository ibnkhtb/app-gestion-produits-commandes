package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Categorie;
import com.mycompany.myapp.domain.Produit;
import com.mycompany.myapp.repository.ProduitRepository;
import com.mycompany.myapp.service.dto.ProduitDTO;
import com.mycompany.myapp.service.mapper.ProduitMapper;
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
 * Integration tests for the {@link ProduitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProduitResourceIT {

    private static final String DEFAULT_NOM_PRODUIT = "AAAAAAAAAA";
    private static final String UPDATED_NOM_PRODUIT = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_PRODUIT = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_PRODUIT = "BBBBBBBBBB";

    private static final Double DEFAULT_PRIX_PRODUIT = 1D;
    private static final Double UPDATED_PRIX_PRODUIT = 2D;
    private static final Double SMALLER_PRIX_PRODUIT = 1D - 1D;

    private static final String DEFAULT_IMAGE_PRODUIT = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_PRODUIT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/produits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{idProduit}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private ProduitMapper produitMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProduitMockMvc;

    private Produit produit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Produit createEntity(EntityManager em) {
        Produit produit = new Produit()
            .nomProduit(DEFAULT_NOM_PRODUIT)
            .descriptionProduit(DEFAULT_DESCRIPTION_PRODUIT)
            .prixProduit(DEFAULT_PRIX_PRODUIT)
            .imageProduit(DEFAULT_IMAGE_PRODUIT);
        return produit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Produit createUpdatedEntity(EntityManager em) {
        Produit produit = new Produit()
            .nomProduit(UPDATED_NOM_PRODUIT)
            .descriptionProduit(UPDATED_DESCRIPTION_PRODUIT)
            .prixProduit(UPDATED_PRIX_PRODUIT)
            .imageProduit(UPDATED_IMAGE_PRODUIT);
        return produit;
    }

    @BeforeEach
    public void initTest() {
        produit = createEntity(em);
    }

    @Test
    @Transactional
    void createProduit() throws Exception {
        int databaseSizeBeforeCreate = produitRepository.findAll().size();
        // Create the Produit
        ProduitDTO produitDTO = produitMapper.toDto(produit);
        restProduitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(produitDTO)))
            .andExpect(status().isCreated());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeCreate + 1);
        Produit testProduit = produitList.get(produitList.size() - 1);
        assertThat(testProduit.getNomProduit()).isEqualTo(DEFAULT_NOM_PRODUIT);
        assertThat(testProduit.getDescriptionProduit()).isEqualTo(DEFAULT_DESCRIPTION_PRODUIT);
        assertThat(testProduit.getPrixProduit()).isEqualTo(DEFAULT_PRIX_PRODUIT);
        assertThat(testProduit.getImageProduit()).isEqualTo(DEFAULT_IMAGE_PRODUIT);
    }

    @Test
    @Transactional
    void createProduitWithExistingId() throws Exception {
        // Create the Produit with an existing ID
        produit.setIdProduit(1L);
        ProduitDTO produitDTO = produitMapper.toDto(produit);

        int databaseSizeBeforeCreate = produitRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProduitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(produitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomProduitIsRequired() throws Exception {
        int databaseSizeBeforeTest = produitRepository.findAll().size();
        // set the field null
        produit.setNomProduit(null);

        // Create the Produit, which fails.
        ProduitDTO produitDTO = produitMapper.toDto(produit);

        restProduitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(produitDTO)))
            .andExpect(status().isBadRequest());

        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescriptionProduitIsRequired() throws Exception {
        int databaseSizeBeforeTest = produitRepository.findAll().size();
        // set the field null
        produit.setDescriptionProduit(null);

        // Create the Produit, which fails.
        ProduitDTO produitDTO = produitMapper.toDto(produit);

        restProduitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(produitDTO)))
            .andExpect(status().isBadRequest());

        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrixProduitIsRequired() throws Exception {
        int databaseSizeBeforeTest = produitRepository.findAll().size();
        // set the field null
        produit.setPrixProduit(null);

        // Create the Produit, which fails.
        ProduitDTO produitDTO = produitMapper.toDto(produit);

        restProduitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(produitDTO)))
            .andExpect(status().isBadRequest());

        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkImageProduitIsRequired() throws Exception {
        int databaseSizeBeforeTest = produitRepository.findAll().size();
        // set the field null
        produit.setImageProduit(null);

        // Create the Produit, which fails.
        ProduitDTO produitDTO = produitMapper.toDto(produit);

        restProduitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(produitDTO)))
            .andExpect(status().isBadRequest());

        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProduits() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList
        restProduitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=idProduit,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].idProduit").value(hasItem(produit.getIdProduit().intValue())))
            .andExpect(jsonPath("$.[*].nomProduit").value(hasItem(DEFAULT_NOM_PRODUIT)))
            .andExpect(jsonPath("$.[*].descriptionProduit").value(hasItem(DEFAULT_DESCRIPTION_PRODUIT)))
            .andExpect(jsonPath("$.[*].prixProduit").value(hasItem(DEFAULT_PRIX_PRODUIT.doubleValue())))
            .andExpect(jsonPath("$.[*].imageProduit").value(hasItem(DEFAULT_IMAGE_PRODUIT)));
    }

    @Test
    @Transactional
    void getProduit() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get the produit
        restProduitMockMvc
            .perform(get(ENTITY_API_URL_ID, produit.getIdProduit()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.idProduit").value(produit.getIdProduit().intValue()))
            .andExpect(jsonPath("$.nomProduit").value(DEFAULT_NOM_PRODUIT))
            .andExpect(jsonPath("$.descriptionProduit").value(DEFAULT_DESCRIPTION_PRODUIT))
            .andExpect(jsonPath("$.prixProduit").value(DEFAULT_PRIX_PRODUIT.doubleValue()))
            .andExpect(jsonPath("$.imageProduit").value(DEFAULT_IMAGE_PRODUIT));
    }

    @Test
    @Transactional
    void getProduitsByIdFiltering() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        Long id = produit.getIdProduit();

        defaultProduitShouldBeFound("idProduit.equals=" + id);
        defaultProduitShouldNotBeFound("idProduit.notEquals=" + id);

        defaultProduitShouldBeFound("idProduit.greaterThanOrEqual=" + id);
        defaultProduitShouldNotBeFound("idProduit.greaterThan=" + id);

        defaultProduitShouldBeFound("idProduit.lessThanOrEqual=" + id);
        defaultProduitShouldNotBeFound("idProduit.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProduitsByNomProduitIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where nomProduit equals to DEFAULT_NOM_PRODUIT
        defaultProduitShouldBeFound("nomProduit.equals=" + DEFAULT_NOM_PRODUIT);

        // Get all the produitList where nomProduit equals to UPDATED_NOM_PRODUIT
        defaultProduitShouldNotBeFound("nomProduit.equals=" + UPDATED_NOM_PRODUIT);
    }

    @Test
    @Transactional
    void getAllProduitsByNomProduitIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where nomProduit in DEFAULT_NOM_PRODUIT or UPDATED_NOM_PRODUIT
        defaultProduitShouldBeFound("nomProduit.in=" + DEFAULT_NOM_PRODUIT + "," + UPDATED_NOM_PRODUIT);

        // Get all the produitList where nomProduit equals to UPDATED_NOM_PRODUIT
        defaultProduitShouldNotBeFound("nomProduit.in=" + UPDATED_NOM_PRODUIT);
    }

    @Test
    @Transactional
    void getAllProduitsByNomProduitIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where nomProduit is not null
        defaultProduitShouldBeFound("nomProduit.specified=true");

        // Get all the produitList where nomProduit is null
        defaultProduitShouldNotBeFound("nomProduit.specified=false");
    }

    @Test
    @Transactional
    void getAllProduitsByNomProduitContainsSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where nomProduit contains DEFAULT_NOM_PRODUIT
        defaultProduitShouldBeFound("nomProduit.contains=" + DEFAULT_NOM_PRODUIT);

        // Get all the produitList where nomProduit contains UPDATED_NOM_PRODUIT
        defaultProduitShouldNotBeFound("nomProduit.contains=" + UPDATED_NOM_PRODUIT);
    }

    @Test
    @Transactional
    void getAllProduitsByNomProduitNotContainsSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where nomProduit does not contain DEFAULT_NOM_PRODUIT
        defaultProduitShouldNotBeFound("nomProduit.doesNotContain=" + DEFAULT_NOM_PRODUIT);

        // Get all the produitList where nomProduit does not contain UPDATED_NOM_PRODUIT
        defaultProduitShouldBeFound("nomProduit.doesNotContain=" + UPDATED_NOM_PRODUIT);
    }

    @Test
    @Transactional
    void getAllProduitsByDescriptionProduitIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where descriptionProduit equals to DEFAULT_DESCRIPTION_PRODUIT
        defaultProduitShouldBeFound("descriptionProduit.equals=" + DEFAULT_DESCRIPTION_PRODUIT);

        // Get all the produitList where descriptionProduit equals to UPDATED_DESCRIPTION_PRODUIT
        defaultProduitShouldNotBeFound("descriptionProduit.equals=" + UPDATED_DESCRIPTION_PRODUIT);
    }

    @Test
    @Transactional
    void getAllProduitsByDescriptionProduitIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where descriptionProduit in DEFAULT_DESCRIPTION_PRODUIT or UPDATED_DESCRIPTION_PRODUIT
        defaultProduitShouldBeFound("descriptionProduit.in=" + DEFAULT_DESCRIPTION_PRODUIT + "," + UPDATED_DESCRIPTION_PRODUIT);

        // Get all the produitList where descriptionProduit equals to UPDATED_DESCRIPTION_PRODUIT
        defaultProduitShouldNotBeFound("descriptionProduit.in=" + UPDATED_DESCRIPTION_PRODUIT);
    }

    @Test
    @Transactional
    void getAllProduitsByDescriptionProduitIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where descriptionProduit is not null
        defaultProduitShouldBeFound("descriptionProduit.specified=true");

        // Get all the produitList where descriptionProduit is null
        defaultProduitShouldNotBeFound("descriptionProduit.specified=false");
    }

    @Test
    @Transactional
    void getAllProduitsByDescriptionProduitContainsSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where descriptionProduit contains DEFAULT_DESCRIPTION_PRODUIT
        defaultProduitShouldBeFound("descriptionProduit.contains=" + DEFAULT_DESCRIPTION_PRODUIT);

        // Get all the produitList where descriptionProduit contains UPDATED_DESCRIPTION_PRODUIT
        defaultProduitShouldNotBeFound("descriptionProduit.contains=" + UPDATED_DESCRIPTION_PRODUIT);
    }

    @Test
    @Transactional
    void getAllProduitsByDescriptionProduitNotContainsSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where descriptionProduit does not contain DEFAULT_DESCRIPTION_PRODUIT
        defaultProduitShouldNotBeFound("descriptionProduit.doesNotContain=" + DEFAULT_DESCRIPTION_PRODUIT);

        // Get all the produitList where descriptionProduit does not contain UPDATED_DESCRIPTION_PRODUIT
        defaultProduitShouldBeFound("descriptionProduit.doesNotContain=" + UPDATED_DESCRIPTION_PRODUIT);
    }

    @Test
    @Transactional
    void getAllProduitsByPrixProduitIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where prixProduit equals to DEFAULT_PRIX_PRODUIT
        defaultProduitShouldBeFound("prixProduit.equals=" + DEFAULT_PRIX_PRODUIT);

        // Get all the produitList where prixProduit equals to UPDATED_PRIX_PRODUIT
        defaultProduitShouldNotBeFound("prixProduit.equals=" + UPDATED_PRIX_PRODUIT);
    }

    @Test
    @Transactional
    void getAllProduitsByPrixProduitIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where prixProduit in DEFAULT_PRIX_PRODUIT or UPDATED_PRIX_PRODUIT
        defaultProduitShouldBeFound("prixProduit.in=" + DEFAULT_PRIX_PRODUIT + "," + UPDATED_PRIX_PRODUIT);

        // Get all the produitList where prixProduit equals to UPDATED_PRIX_PRODUIT
        defaultProduitShouldNotBeFound("prixProduit.in=" + UPDATED_PRIX_PRODUIT);
    }

    @Test
    @Transactional
    void getAllProduitsByPrixProduitIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where prixProduit is not null
        defaultProduitShouldBeFound("prixProduit.specified=true");

        // Get all the produitList where prixProduit is null
        defaultProduitShouldNotBeFound("prixProduit.specified=false");
    }

    @Test
    @Transactional
    void getAllProduitsByPrixProduitIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where prixProduit is greater than or equal to DEFAULT_PRIX_PRODUIT
        defaultProduitShouldBeFound("prixProduit.greaterThanOrEqual=" + DEFAULT_PRIX_PRODUIT);

        // Get all the produitList where prixProduit is greater than or equal to UPDATED_PRIX_PRODUIT
        defaultProduitShouldNotBeFound("prixProduit.greaterThanOrEqual=" + UPDATED_PRIX_PRODUIT);
    }

    @Test
    @Transactional
    void getAllProduitsByPrixProduitIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where prixProduit is less than or equal to DEFAULT_PRIX_PRODUIT
        defaultProduitShouldBeFound("prixProduit.lessThanOrEqual=" + DEFAULT_PRIX_PRODUIT);

        // Get all the produitList where prixProduit is less than or equal to SMALLER_PRIX_PRODUIT
        defaultProduitShouldNotBeFound("prixProduit.lessThanOrEqual=" + SMALLER_PRIX_PRODUIT);
    }

    @Test
    @Transactional
    void getAllProduitsByPrixProduitIsLessThanSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where prixProduit is less than DEFAULT_PRIX_PRODUIT
        defaultProduitShouldNotBeFound("prixProduit.lessThan=" + DEFAULT_PRIX_PRODUIT);

        // Get all the produitList where prixProduit is less than UPDATED_PRIX_PRODUIT
        defaultProduitShouldBeFound("prixProduit.lessThan=" + UPDATED_PRIX_PRODUIT);
    }

    @Test
    @Transactional
    void getAllProduitsByPrixProduitIsGreaterThanSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where prixProduit is greater than DEFAULT_PRIX_PRODUIT
        defaultProduitShouldNotBeFound("prixProduit.greaterThan=" + DEFAULT_PRIX_PRODUIT);

        // Get all the produitList where prixProduit is greater than SMALLER_PRIX_PRODUIT
        defaultProduitShouldBeFound("prixProduit.greaterThan=" + SMALLER_PRIX_PRODUIT);
    }

    @Test
    @Transactional
    void getAllProduitsByImageProduitIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where imageProduit equals to DEFAULT_IMAGE_PRODUIT
        defaultProduitShouldBeFound("imageProduit.equals=" + DEFAULT_IMAGE_PRODUIT);

        // Get all the produitList where imageProduit equals to UPDATED_IMAGE_PRODUIT
        defaultProduitShouldNotBeFound("imageProduit.equals=" + UPDATED_IMAGE_PRODUIT);
    }

    @Test
    @Transactional
    void getAllProduitsByImageProduitIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where imageProduit in DEFAULT_IMAGE_PRODUIT or UPDATED_IMAGE_PRODUIT
        defaultProduitShouldBeFound("imageProduit.in=" + DEFAULT_IMAGE_PRODUIT + "," + UPDATED_IMAGE_PRODUIT);

        // Get all the produitList where imageProduit equals to UPDATED_IMAGE_PRODUIT
        defaultProduitShouldNotBeFound("imageProduit.in=" + UPDATED_IMAGE_PRODUIT);
    }

    @Test
    @Transactional
    void getAllProduitsByImageProduitIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where imageProduit is not null
        defaultProduitShouldBeFound("imageProduit.specified=true");

        // Get all the produitList where imageProduit is null
        defaultProduitShouldNotBeFound("imageProduit.specified=false");
    }

    @Test
    @Transactional
    void getAllProduitsByImageProduitContainsSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where imageProduit contains DEFAULT_IMAGE_PRODUIT
        defaultProduitShouldBeFound("imageProduit.contains=" + DEFAULT_IMAGE_PRODUIT);

        // Get all the produitList where imageProduit contains UPDATED_IMAGE_PRODUIT
        defaultProduitShouldNotBeFound("imageProduit.contains=" + UPDATED_IMAGE_PRODUIT);
    }

    @Test
    @Transactional
    void getAllProduitsByImageProduitNotContainsSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where imageProduit does not contain DEFAULT_IMAGE_PRODUIT
        defaultProduitShouldNotBeFound("imageProduit.doesNotContain=" + DEFAULT_IMAGE_PRODUIT);

        // Get all the produitList where imageProduit does not contain UPDATED_IMAGE_PRODUIT
        defaultProduitShouldBeFound("imageProduit.doesNotContain=" + UPDATED_IMAGE_PRODUIT);
    }

    @Test
    @Transactional
    void getAllProduitsByCategorieIsEqualToSomething() throws Exception {
        Categorie categorie;
        if (TestUtil.findAll(em, Categorie.class).isEmpty()) {
            produitRepository.saveAndFlush(produit);
            categorie = CategorieResourceIT.createEntity(em);
        } else {
            categorie = TestUtil.findAll(em, Categorie.class).get(0);
        }
        em.persist(categorie);
        em.flush();
        produit.setCategorie(categorie);
        produitRepository.saveAndFlush(produit);
        Long categorieId = categorie.getIdCategorie();
        // Get all the produitList where categorie equals to categorieId
        defaultProduitShouldBeFound("categorieId.equals=" + categorieId);

        // Get all the produitList where categorie equals to (categorieId + 1)
        defaultProduitShouldNotBeFound("categorieId.equals=" + (categorieId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProduitShouldBeFound(String filter) throws Exception {
        restProduitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=idProduit,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].idProduit").value(hasItem(produit.getIdProduit().intValue())))
            .andExpect(jsonPath("$.[*].nomProduit").value(hasItem(DEFAULT_NOM_PRODUIT)))
            .andExpect(jsonPath("$.[*].descriptionProduit").value(hasItem(DEFAULT_DESCRIPTION_PRODUIT)))
            .andExpect(jsonPath("$.[*].prixProduit").value(hasItem(DEFAULT_PRIX_PRODUIT.doubleValue())))
            .andExpect(jsonPath("$.[*].imageProduit").value(hasItem(DEFAULT_IMAGE_PRODUIT)));

        // Check, that the count call also returns 1
        restProduitMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=idProduit,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProduitShouldNotBeFound(String filter) throws Exception {
        restProduitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=idProduit,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProduitMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=idProduit,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProduit() throws Exception {
        // Get the produit
        restProduitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProduit() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        int databaseSizeBeforeUpdate = produitRepository.findAll().size();

        // Update the produit
        Produit updatedProduit = produitRepository.findById(produit.getIdProduit()).orElseThrow();
        // Disconnect from session so that the updates on updatedProduit are not directly saved in db
        em.detach(updatedProduit);
        updatedProduit
            .nomProduit(UPDATED_NOM_PRODUIT)
            .descriptionProduit(UPDATED_DESCRIPTION_PRODUIT)
            .prixProduit(UPDATED_PRIX_PRODUIT)
            .imageProduit(UPDATED_IMAGE_PRODUIT);
        ProduitDTO produitDTO = produitMapper.toDto(updatedProduit);

        restProduitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, produitDTO.getIdProduit())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(produitDTO))
            )
            .andExpect(status().isOk());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeUpdate);
        Produit testProduit = produitList.get(produitList.size() - 1);
        assertThat(testProduit.getNomProduit()).isEqualTo(UPDATED_NOM_PRODUIT);
        assertThat(testProduit.getDescriptionProduit()).isEqualTo(UPDATED_DESCRIPTION_PRODUIT);
        assertThat(testProduit.getPrixProduit()).isEqualTo(UPDATED_PRIX_PRODUIT);
        assertThat(testProduit.getImageProduit()).isEqualTo(UPDATED_IMAGE_PRODUIT);
    }

    @Test
    @Transactional
    void putNonExistingProduit() throws Exception {
        int databaseSizeBeforeUpdate = produitRepository.findAll().size();
        produit.setIdProduit(longCount.incrementAndGet());

        // Create the Produit
        ProduitDTO produitDTO = produitMapper.toDto(produit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProduitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, produitDTO.getIdProduit())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(produitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProduit() throws Exception {
        int databaseSizeBeforeUpdate = produitRepository.findAll().size();
        produit.setIdProduit(longCount.incrementAndGet());

        // Create the Produit
        ProduitDTO produitDTO = produitMapper.toDto(produit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProduitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(produitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProduit() throws Exception {
        int databaseSizeBeforeUpdate = produitRepository.findAll().size();
        produit.setIdProduit(longCount.incrementAndGet());

        // Create the Produit
        ProduitDTO produitDTO = produitMapper.toDto(produit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProduitMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(produitDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProduitWithPatch() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        int databaseSizeBeforeUpdate = produitRepository.findAll().size();

        // Update the produit using partial update
        Produit partialUpdatedProduit = new Produit();
        partialUpdatedProduit.setIdProduit(produit.getIdProduit());

        partialUpdatedProduit
            .nomProduit(UPDATED_NOM_PRODUIT)
            .descriptionProduit(UPDATED_DESCRIPTION_PRODUIT)
            .prixProduit(UPDATED_PRIX_PRODUIT)
            .imageProduit(UPDATED_IMAGE_PRODUIT);

        restProduitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProduit.getIdProduit())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProduit))
            )
            .andExpect(status().isOk());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeUpdate);
        Produit testProduit = produitList.get(produitList.size() - 1);
        assertThat(testProduit.getNomProduit()).isEqualTo(UPDATED_NOM_PRODUIT);
        assertThat(testProduit.getDescriptionProduit()).isEqualTo(UPDATED_DESCRIPTION_PRODUIT);
        assertThat(testProduit.getPrixProduit()).isEqualTo(UPDATED_PRIX_PRODUIT);
        assertThat(testProduit.getImageProduit()).isEqualTo(UPDATED_IMAGE_PRODUIT);
    }

    @Test
    @Transactional
    void fullUpdateProduitWithPatch() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        int databaseSizeBeforeUpdate = produitRepository.findAll().size();

        // Update the produit using partial update
        Produit partialUpdatedProduit = new Produit();
        partialUpdatedProduit.setIdProduit(produit.getIdProduit());

        partialUpdatedProduit
            .nomProduit(UPDATED_NOM_PRODUIT)
            .descriptionProduit(UPDATED_DESCRIPTION_PRODUIT)
            .prixProduit(UPDATED_PRIX_PRODUIT)
            .imageProduit(UPDATED_IMAGE_PRODUIT);

        restProduitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProduit.getIdProduit())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProduit))
            )
            .andExpect(status().isOk());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeUpdate);
        Produit testProduit = produitList.get(produitList.size() - 1);
        assertThat(testProduit.getNomProduit()).isEqualTo(UPDATED_NOM_PRODUIT);
        assertThat(testProduit.getDescriptionProduit()).isEqualTo(UPDATED_DESCRIPTION_PRODUIT);
        assertThat(testProduit.getPrixProduit()).isEqualTo(UPDATED_PRIX_PRODUIT);
        assertThat(testProduit.getImageProduit()).isEqualTo(UPDATED_IMAGE_PRODUIT);
    }

    @Test
    @Transactional
    void patchNonExistingProduit() throws Exception {
        int databaseSizeBeforeUpdate = produitRepository.findAll().size();
        produit.setIdProduit(longCount.incrementAndGet());

        // Create the Produit
        ProduitDTO produitDTO = produitMapper.toDto(produit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProduitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, produitDTO.getIdProduit())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(produitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProduit() throws Exception {
        int databaseSizeBeforeUpdate = produitRepository.findAll().size();
        produit.setIdProduit(longCount.incrementAndGet());

        // Create the Produit
        ProduitDTO produitDTO = produitMapper.toDto(produit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProduitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(produitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProduit() throws Exception {
        int databaseSizeBeforeUpdate = produitRepository.findAll().size();
        produit.setIdProduit(longCount.incrementAndGet());

        // Create the Produit
        ProduitDTO produitDTO = produitMapper.toDto(produit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProduitMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(produitDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProduit() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        int databaseSizeBeforeDelete = produitRepository.findAll().size();

        // Delete the produit
        restProduitMockMvc
            .perform(delete(ENTITY_API_URL_ID, produit.getIdProduit()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
