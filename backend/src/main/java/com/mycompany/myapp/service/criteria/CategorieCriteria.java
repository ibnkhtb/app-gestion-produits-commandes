package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Categorie} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.CategorieResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /categories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CategorieCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter idCategorie;

    private StringFilter nomCategorie;

    private Boolean distinct;

    public CategorieCriteria() {}

    public CategorieCriteria(CategorieCriteria other) {
        this.idCategorie = other.idCategorie == null ? null : other.idCategorie.copy();
        this.nomCategorie = other.nomCategorie == null ? null : other.nomCategorie.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CategorieCriteria copy() {
        return new CategorieCriteria(this);
    }

    public LongFilter getIdCategorie() {
        return idCategorie;
    }

    public LongFilter idCategorie() {
        if (idCategorie == null) {
            idCategorie = new LongFilter();
        }
        return idCategorie;
    }

    public void setIdCategorie(LongFilter idCategorie) {
        this.idCategorie = idCategorie;
    }

    public StringFilter getNomCategorie() {
        return nomCategorie;
    }

    public StringFilter nomCategorie() {
        if (nomCategorie == null) {
            nomCategorie = new StringFilter();
        }
        return nomCategorie;
    }

    public void setNomCategorie(StringFilter nomCategorie) {
        this.nomCategorie = nomCategorie;
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
        final CategorieCriteria that = (CategorieCriteria) o;
        return (
            Objects.equals(idCategorie, that.idCategorie) &&
            Objects.equals(nomCategorie, that.nomCategorie) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCategorie, nomCategorie, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategorieCriteria{" +
            (idCategorie != null ? "idCategorie=" + idCategorie + ", " : "") +
            (nomCategorie != null ? "nomCategorie=" + nomCategorie + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
