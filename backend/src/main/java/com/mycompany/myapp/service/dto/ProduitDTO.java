package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Produit} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProduitDTO implements Serializable {

    private Long idProduit;

    private String nomProduit;

    private String descriptionProduit;

    private Double prixProduit;

    private String imageProduit;

    private CategorieDTO categorie;

    private CommandeDTO commande;

    public Long getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(Long idProduit) {
        this.idProduit = idProduit;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public String getDescriptionProduit() {
        return descriptionProduit;
    }

    public void setDescriptionProduit(String descriptionProduit) {
        this.descriptionProduit = descriptionProduit;
    }

    public Double getPrixProduit() {
        return prixProduit;
    }

    public void setPrixProduit(Double prixProduit) {
        this.prixProduit = prixProduit;
    }

    public String getImageProduit() {
        return imageProduit;
    }

    public void setImageProduit(String imageProduit) {
        this.imageProduit = imageProduit;
    }

    public CategorieDTO getCategorie() {
        return categorie;
    }

    public void setCategorie(CategorieDTO categorie) {
        this.categorie = categorie;
    }

    public CommandeDTO getCommande() {
        return commande;
    }

    public void setCommande(CommandeDTO commande) {
        this.commande = commande;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProduitDTO)) {
            return false;
        }

        ProduitDTO produitDTO = (ProduitDTO) o;
        if (this.idProduit == null) {
            return false;
        }
        return Objects.equals(this.idProduit, produitDTO.idProduit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idProduit);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProduitDTO{" +
            "idProduit=" + getIdProduit() +
            ", nomProduit='" + getNomProduit() + "'" +
            ", descriptionProduit='" + getDescriptionProduit() + "'" +
            ", prixProduit=" + getPrixProduit() +
            ", imageProduit='" + getImageProduit() + "'" +
            ", categorie=" + getCategorie() +
            ", commande=" + getCommande() +
            "}";
    }
}
