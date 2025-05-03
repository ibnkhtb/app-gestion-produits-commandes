package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Categorie;
import com.mycompany.myapp.domain.Produit;
import com.mycompany.myapp.service.dto.CategorieDTO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Produit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long>, JpaSpecificationExecutor<Produit> {
	

	//On déclare la méthode pour récupérer les produits par catégorie
	@Query("SELECT p FROM Produit p WHERE p.categorie.idCategorie = :idCategorie")
	List<Produit> chercherProduitParCategorie(@Param("idCategorie") Long idCategorie);

		
	//On déclare la méthode pour récupérer les produits les plus commandés
	@Query("SELECT p, COUNT(c) AS totalCommandes " +
		       "FROM Produit p " +
		       "LEFT JOIN Commande c ON p.idProduit = c.produit.idProduit " +
		       "GROUP BY p.idProduit " +
		       "ORDER BY totalCommandes DESC")
    List<Produit> chercherTopProduits(Pageable pageable);
	
	
	
}

