package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Client;
import com.mycompany.myapp.domain.Commande;
import com.mycompany.myapp.domain.Produit;
import com.mycompany.myapp.service.dto.ClientDTO;
import com.mycompany.myapp.service.dto.CommandeDTO;
import com.mycompany.myapp.service.dto.ProduitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Commande} and its DTO {@link CommandeDTO}.
 */
@Mapper(componentModel = "spring")
public interface CommandeMapper extends EntityMapper<CommandeDTO, Commande> {
    @Mapping(target = "client", source = "client", qualifiedByName = "clientIdClient")
    @Mapping(target = "produit", source = "produit", qualifiedByName = "produitIdProduit")
    CommandeDTO toDto(Commande s);

    @Named("clientIdClient")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "idClient", source = "idClient")
    @Mapping(target = "nom", source = "nom")
    @Mapping(target = "prenom", source = "prenom")
    @Mapping(target = "adresse", source = "adresse")
    @Mapping(target = "telephone", source = "telephone")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "user", source = "user")
    ClientDTO toDtoClientIdClient(Client client);

    @Named("produitIdProduit")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "idProduit", source = "idProduit")
    @Mapping(target = "nomProduit", source = "nomProduit")
    @Mapping(target = "descriptionProduit", source = "descriptionProduit")
    @Mapping(target = "imageProduit", source = "descriptionProduit")
    @Mapping(target = "categorie", source = "categorie")
    ProduitDTO toDtoProduitIdProduit(Produit produit);
}
