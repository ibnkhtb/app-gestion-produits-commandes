package com.mycompany.myapp.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Produit.
 */
@Entity
@Table(name = "produit")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Produit implements Serializable {

    private static final long serialVersionUID = 1L;

    //@NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produit", nullable = false)
    private Long idProduit;

    @NotNull
    @Column(name = "nom_produit", nullable = false)
    private String nomProduit;

    @NotNull
    @Column(name = "description_produit", nullable = false)
    private String descriptionProduit;

    @NotNull
    @Column(name = "prix_produit", nullable = false)
    private Double prixProduit;

    @NotNull
    @Column(name = "image_produit", nullable = false)
    private String imageProduit;

    @ManyToOne(fetch = FetchType.LAZY)
    private Categorie categorie;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getIdProduit() {
        return this.idProduit;
    }

    public Produit idProduit(Long idProduit) {
        this.setIdProduit(idProduit);
        return this;
    }

    public void setIdProduit(Long idProduit) {
        this.idProduit = idProduit;
    }

    public String getNomProduit() {
        return this.nomProduit;
    }

    public Produit nomProduit(String nomProduit) {
        this.setNomProduit(nomProduit);
        return this;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public String getDescriptionProduit() {
        return this.descriptionProduit;
    }

    public Produit descriptionProduit(String descriptionProduit) {
        this.setDescriptionProduit(descriptionProduit);
        return this;
    }

    public void setDescriptionProduit(String descriptionProduit) {
        this.descriptionProduit = descriptionProduit;
    }

    public Double getPrixProduit() {
        return this.prixProduit;
    }

    public Produit prixProduit(Double prixProduit) {
        this.setPrixProduit(prixProduit);
        return this;
    }

    public void setPrixProduit(Double prixProduit) {
        this.prixProduit = prixProduit;
    }

    public String getImageProduit() {
        return this.imageProduit;
    }

    public Produit imageProduit(String imageProduit) {
        this.setImageProduit(imageProduit);
        return this;
    }

    public void setImageProduit(String imageProduit) {
        this.imageProduit = imageProduit;
    }

    public Categorie getCategorie() {
        return this.categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Produit categorie(Categorie categorie) {
        this.setCategorie(categorie);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Produit)) {
            return false;
        }
        return getIdProduit() != null && getIdProduit().equals(((Produit) o).getIdProduit());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Produit{" +
            "idProduit=" + getIdProduit() +
            ", nomProduit='" + getNomProduit() + "'" +
            ", descriptionProduit='" + getDescriptionProduit() + "'" +
            ", prixProduit=" + getPrixProduit() +
            ", imageProduit='" + getImageProduit() + "'" +
            "}";
    }
}
