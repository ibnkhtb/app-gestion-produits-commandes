package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Categorie} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CategorieDTO implements Serializable {

    //@NotNull
    private Long idCategorie;

    @NotNull
    private String nomCategorie;

    public Long getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(Long idCategorie) {
        this.idCategorie = idCategorie;
    }

    public String getNomCategorie() {
        return nomCategorie;
    }

    public void setNomCategorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategorieDTO)) {
            return false;
        }

        CategorieDTO categorieDTO = (CategorieDTO) o;
        if (this.idCategorie == null) {
            return false;
        }
        return Objects.equals(this.idCategorie, categorieDTO.idCategorie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idCategorie);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategorieDTO{" +
            "idCategorie=" + getIdCategorie() +
            ", nomCategorie='" + getNomCategorie() + "'" +
            "}";
    }
}
