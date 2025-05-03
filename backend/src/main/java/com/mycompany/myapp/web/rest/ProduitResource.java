package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Categorie;
import com.mycompany.myapp.domain.Produit;
import com.mycompany.myapp.repository.ProduitRepository;
import com.mycompany.myapp.service.CategorieService;
import com.mycompany.myapp.service.ProduitQueryService;
import com.mycompany.myapp.service.ProduitService;
import com.mycompany.myapp.service.criteria.ProduitCriteria;
import com.mycompany.myapp.service.dto.CategorieDTO;
import com.mycompany.myapp.service.dto.ProduitDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.Paths;
/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Produit}.
 */
@RestController
@RequestMapping("/api/produits")
public class ProduitResource {

    private final Logger log = LoggerFactory.getLogger(ProduitResource.class);

    private static final String ENTITY_NAME = "produit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProduitService produitService;

    private final ProduitRepository produitRepository;

    private final ProduitQueryService produitQueryService;
    
    private final CategorieService categorieService; // Injectez le service de catégorie

    public ProduitResource(ProduitService produitService, ProduitRepository produitRepository, ProduitQueryService produitQueryService, CategorieService categorieService) {
        this.produitService = produitService;
        this.produitRepository = produitRepository;
        this.produitQueryService = produitQueryService;
        this.categorieService = categorieService; // Injectez le service de catégorie
    }

    /**
     * {@code POST  /produits} : Create a new produit.
     *
     * @param produitDTO the produitDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new produitDTO, or with status {@code 400 (Bad Request)} if the produit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ProduitDTO> createProduit(@Valid @RequestBody ProduitDTO produitDTO) throws URISyntaxException {
        log.debug("REST request to save Produit : {}", produitDTO);
        if (produitDTO.getIdProduit() != null) {
            throw new BadRequestAlertException("A new produit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProduitDTO result = produitService.save(produitDTO);
        return ResponseEntity
            .created(new URI("/api/produits/" + result.getIdProduit()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getIdProduit().toString()))
            .body(result);
    }
    
    @PostMapping("/upload-image")
    public ResponseEntity<String> handleImageUpload(@RequestParam("image") MultipartFile file) {
        try {
            // Récupérer le chemin du répertoire de ressources statiques
            String uploadDir = "/src/main/ressources/images/";

            // Créer le chemin complet du fichier dans le répertoire d'upload
            Path uploadPath = Paths.get(uploadDir);
            Path filePath = uploadPath.resolve(file.getOriginalFilename());

            // Copier le fichier dans le répertoire d'upload
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Retourner le chemin relatif du fichier
            return ResponseEntity.ok("content/images/" + file.getOriginalFilename());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erreur lors du téléchargement du fichier.");
        }
    }

    /**
     * {@code PUT  /produits/:idProduit} : Updates an existing produit.
     *
     * @param idProduit the id of the produitDTO to save.
     * @param produitDTO the produitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated produitDTO,
     * or with status {@code 400 (Bad Request)} if the produitDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the produitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{idProduit}")
    public ResponseEntity<ProduitDTO> updateProduit(
        @PathVariable(value = "idProduit", required = false) final Long idProduit,
        @Valid @RequestBody ProduitDTO produitDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Produit : {}, {}", idProduit, produitDTO);
        if (produitDTO.getIdProduit() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idProduit, produitDTO.getIdProduit())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!produitRepository.existsById(idProduit)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProduitDTO result = produitService.update(produitDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, produitDTO.getIdProduit().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /produits/:idProduit} : Partial updates given fields of an existing produit, field will ignore if it is null
     *
     * @param idProduit the id of the produitDTO to save.
     * @param produitDTO the produitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated produitDTO,
     * or with status {@code 400 (Bad Request)} if the produitDTO is not valid,
     * or with status {@code 404 (Not Found)} if the produitDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the produitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{idProduit}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProduitDTO> partialUpdateProduit(
        @PathVariable(value = "idProduit", required = false) final Long idProduit,
        @NotNull @RequestBody ProduitDTO produitDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Produit partially : {}, {}", idProduit, produitDTO);
        if (produitDTO.getIdProduit() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idProduit, produitDTO.getIdProduit())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!produitRepository.existsById(idProduit)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProduitDTO> result = produitService.partialUpdate(produitDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, produitDTO.getIdProduit().toString())
        );
    }

    /**
     * {@code GET  /produits} : get all the produits.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of produits in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ProduitDTO>> getAllProduits(
        ProduitCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Produits by criteria: {}", criteria);

        Page<ProduitDTO> page = produitQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /produits/count} : count all the produits.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countProduits(ProduitCriteria criteria) {
        log.debug("REST request to count Produits by criteria: {}", criteria);
        return ResponseEntity.ok().body(produitQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /produits/:id} : get the "id" produit.
     *
     * @param id the id of the produitDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the produitDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProduitDTO> getProduit(@PathVariable Long id) {
        log.debug("REST request to get Produit : {}", id);
        Optional<ProduitDTO> produitDTO = produitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(produitDTO);
    }

    /**
     * {@code DELETE  /produits/:id} : delete the "id" produit.
     *
     * @param id the id of the produitDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduit(@PathVariable Long id) {
        log.debug("REST request to delete Produit : {}", id);
        produitService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
    
   
    
  
    @GetMapping("/categories/{idCategorie}")
    public ResponseEntity<List<ProduitDTO>> getProduitsByCategorie(@PathVariable Long idCategorie) {
        log.debug("REST request to get Produits by Categorie: {}", idCategorie);
        List<ProduitDTO> produits = produitService.findProduitsByCategorie(idCategorie);
        return ResponseEntity.ok().body(produits);
    }
    
    @GetMapping("/top")
    public ResponseEntity<List<ProduitDTO>> getProduitsTop() {
        log.debug("REST request to get Produits plus commandés: {}");
        List<ProduitDTO> produits = produitService.findProduitsTop();
        return ResponseEntity.ok().body(produits);
    }



}
