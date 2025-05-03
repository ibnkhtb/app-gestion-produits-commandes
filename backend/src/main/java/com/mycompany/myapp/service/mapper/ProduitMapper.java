package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Categorie;
import com.mycompany.myapp.domain.Produit;
import com.mycompany.myapp.service.dto.CategorieDTO;
import com.mycompany.myapp.service.dto.ProduitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Produit} and its DTO {@link ProduitDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProduitMapper extends EntityMapper<ProduitDTO, Produit> {
    @Mapping(target = "categorie", source = "categorie", qualifiedByName = "categorieIdCategorie")
    ProduitDTO toDto(Produit s);

    @Named("categorieIdCategorie")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "idCategorie", source = "idCategorie")
    @Mapping(target = "nomCategorie", source = "nomCategorie")
    CategorieDTO toDtoCategorieIdCategorie(Categorie categorie);
}
