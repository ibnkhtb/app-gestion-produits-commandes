package com.mycompany.myapp.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Categorie.
 */
@Entity
@Table(name = "categorie")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Categorie implements Serializable {

    private static final long serialVersionUID = 1L;

   // @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categorie", nullable = false)
    private Long idCategorie;

    @NotNull
    @Column(name = "nom_categorie", nullable = false)
    private String nomCategorie;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getIdCategorie() {
        return this.idCategorie;
    }

    public Categorie idCategorie(Long idCategorie) {
        this.setIdCategorie(idCategorie);
        return this;
    }

    public void setIdCategorie(Long idCategorie) {
        this.idCategorie = idCategorie;
    }

    public String getNomCategorie() {
        return this.nomCategorie;
    }

    public Categorie nomCategorie(String nomCategorie) {
        this.setNomCategorie(nomCategorie);
        return this;
    }

    public void setNomCategorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Categorie)) {
            return false;
        }
        return getIdCategorie() != null && getIdCategorie().equals(((Categorie) o).getIdCategorie());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Categorie{" +
            "idCategorie=" + getIdCategorie() +
            ", nomCategorie='" + getNomCategorie() + "'" +
            "}";
    }
}
