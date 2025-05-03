package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Client;
import com.mycompany.myapp.domain.Commande;
import com.mycompany.myapp.domain.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Client entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientRepository extends JpaRepository<Client, Long>, JpaSpecificationExecutor<Client> {
 
	@Query("SELECT c.id FROM Client c WHERE c.user.id = :userId")
	int findClientByUserId(@Param("userId") Long userId);


}
