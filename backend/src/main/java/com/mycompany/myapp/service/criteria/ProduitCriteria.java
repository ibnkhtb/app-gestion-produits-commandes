package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Produit} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.ProduitResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /produits?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProduitCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter idProduit;

    private StringFilter nomProduit;

    private StringFilter descriptionProduit;

    private DoubleFilter prixProduit;

    private StringFilter imageProduit;

    private LongFilter categorieId;

    private Boolean distinct;

    public ProduitCriteria() {}

    public ProduitCriteria(ProduitCriteria other) {
        this.idProduit = other.idProduit == null ? null : other.idProduit.copy();
        this.nomProduit = other.nomProduit == null ? null : other.nomProduit.copy();
        this.descriptionProduit = other.descriptionProduit == null ? null : other.descriptionProduit.copy();
        this.prixProduit = other.prixProduit == null ? null : other.prixProduit.copy();
        this.imageProduit = other.imageProduit == null ? null : other.imageProduit.copy();
        this.categorieId = other.categorieId == null ? null : other.categorieId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ProduitCriteria copy() {
        return new ProduitCriteria(this);
    }

    public LongFilter getIdProduit() {
        return idProduit;
    }

    public LongFilter idProduit() {
        if (idProduit == null) {
            idProduit = new LongFilter();
        }
        return idProduit;
    }

    public void setIdProduit(LongFilter idProduit) {
        this.idProduit = idProduit;
    }

    public StringFilter getNomProduit() {
        return nomProduit;
    }

    public StringFilter nomProduit() {
        if (nomProduit == null) {
            nomProduit = new StringFilter();
        }
        return nomProduit;
    }

    public void setNomProduit(StringFilter nomProduit) {
        this.nomProduit = nomProduit;
    }

    public StringFilter getDescriptionProduit() {
        return descriptionProduit;
    }

    public StringFilter descriptionProduit() {
        if (descriptionProduit == null) {
            descriptionProduit = new StringFilter();
        }
        return descriptionProduit;
    }

    public void setDescriptionProduit(StringFilter descriptionProduit) {
        this.descriptionProduit = descriptionProduit;
    }

    public DoubleFilter getPrixProduit() {
        return prixProduit;
    }

    public DoubleFilter prixProduit() {
        if (prixProduit == null) {
            prixProduit = new DoubleFilter();
        }
        return prixProduit;
    }

    public void setPrixProduit(DoubleFilter prixProduit) {
        this.prixProduit = prixProduit;
    }

    public StringFilter getImageProduit() {
        return imageProduit;
    }

    public StringFilter imageProduit() {
        if (imageProduit == null) {
            imageProduit = new StringFilter();
        }
        return imageProduit;
    }

    public void setImageProduit(StringFilter imageProduit) {
        this.imageProduit = imageProduit;
    }

    public LongFilter getCategorieId() {
        return categorieId;
    }

    public LongFilter categorieId() {
        if (categorieId == null) {
            categorieId = new LongFilter();
        }
        return categorieId;
    }

    public void setCategorieId(LongFilter categorieId) {
        this.categorieId = categorieId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProduitCriteria that = (ProduitCriteria) o;
        return (
            Objects.equals(idProduit, that.idProduit) &&
            Objects.equals(nomProduit, that.nomProduit) &&
            Objects.equals(descriptionProduit, that.descriptionProduit) &&
            Objects.equals(prixProduit, that.prixProduit) &&
            Objects.equals(imageProduit, that.imageProduit) &&
            Objects.equals(categorieId, that.categorieId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduit, nomProduit, descriptionProduit, prixProduit, imageProduit, categorieId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProduitCriteria{" +
            (idProduit != null ? "idProduit=" + idProduit + ", " : "") +
            (nomProduit != null ? "nomProduit=" + nomProduit + ", " : "") +
            (descriptionProduit != null ? "descriptionProduit=" + descriptionProduit + ", " : "") +
            (prixProduit != null ? "prixProduit=" + prixProduit + ", " : "") +
            (imageProduit != null ? "imageProduit=" + imageProduit + ", " : "") +
            (categorieId != null ? "categorieId=" + categorieId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
