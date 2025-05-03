package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Commande;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Commande entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long>, JpaSpecificationExecutor<Commande> {
	
    // On déclare la méthode pour récupérer la liste des commandes avec les détails associés
 @Query("SELECT DISTINCT c FROM Commande c " +
	       "JOIN FETCH c.client cl " +
	       "JOIN FETCH c.produit p " +
	       "JOIN FETCH p.categorie " +
	       "ORDER BY c.idCommande DESC ")
	List<Commande> chercherListeCommandesDetails();

}
