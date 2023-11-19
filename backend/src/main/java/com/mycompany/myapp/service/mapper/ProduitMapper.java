package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Categorie;
import com.mycompany.myapp.domain.Commande;
import com.mycompany.myapp.domain.Produit;
import com.mycompany.myapp.service.dto.CategorieDTO;
import com.mycompany.myapp.service.dto.CommandeDTO;
import com.mycompany.myapp.service.dto.ProduitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Produit} and its DTO {@link ProduitDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProduitMapper extends EntityMapper<ProduitDTO, Produit> {
    @Mapping(target = "categorie", source = "categorie", qualifiedByName = "categorieIdCategorie")
    @Mapping(target = "commande", source = "commande", qualifiedByName = "commandeIdCommande")
    ProduitDTO toDto(Produit s);

    @Named("categorieIdCategorie")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "idCategorie", source = "idCategorie")
    CategorieDTO toDtoCategorieIdCategorie(Categorie categorie);

    @Named("commandeIdCommande")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "idCommande", source = "idCommande")
    CommandeDTO toDtoCommandeIdCommande(Commande commande);
}
