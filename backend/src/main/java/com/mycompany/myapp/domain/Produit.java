package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produit")
    private Long idProduit;

    @Column(name = "nom_produit")
    private String nomProduit;

    @Column(name = "description_produit")
    private String descriptionProduit;

    @Column(name = "prix_produit")
    private Double prixProduit;

    @Column(name = "image_produit")
    private String imageProduit;

    @ManyToOne(fetch = FetchType.LAZY)
    private Categorie categorie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "client" }, allowSetters = true)
    private Commande commande;

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

    public Commande getCommande() {
        return this.commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public Produit commande(Commande commande) {
        this.setCommande(commande);
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
