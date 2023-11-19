package com.mycompany.myapp.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.Commande} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.CommandeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /commandes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CommandeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter idCommande;

    private InstantFilter dateCommande;

    private LongFilter clientId;

    private Boolean distinct;

    public CommandeCriteria() {}

    public CommandeCriteria(CommandeCriteria other) {
        this.idCommande = other.idCommande == null ? null : other.idCommande.copy();
        this.dateCommande = other.dateCommande == null ? null : other.dateCommande.copy();
        this.clientId = other.clientId == null ? null : other.clientId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CommandeCriteria copy() {
        return new CommandeCriteria(this);
    }

    public LongFilter getIdCommande() {
        return idCommande;
    }

    public LongFilter idCommande() {
        if (idCommande == null) {
            idCommande = new LongFilter();
        }
        return idCommande;
    }

    public void setIdCommande(LongFilter idCommande) {
        this.idCommande = idCommande;
    }

    public InstantFilter getDateCommande() {
        return dateCommande;
    }

    public InstantFilter dateCommande() {
        if (dateCommande == null) {
            dateCommande = new InstantFilter();
        }
        return dateCommande;
    }

    public void setDateCommande(InstantFilter dateCommande) {
        this.dateCommande = dateCommande;
    }

    public LongFilter getClientId() {
        return clientId;
    }

    public LongFilter clientId() {
        if (clientId == null) {
            clientId = new LongFilter();
        }
        return clientId;
    }

    public void setClientId(LongFilter clientId) {
        this.clientId = clientId;
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
        final CommandeCriteria that = (CommandeCriteria) o;
        return (
            Objects.equals(idCommande, that.idCommande) &&
            Objects.equals(dateCommande, that.dateCommande) &&
            Objects.equals(clientId, that.clientId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCommande, dateCommande, clientId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommandeCriteria{" +
            (idCommande != null ? "idCommande=" + idCommande + ", " : "") +
            (dateCommande != null ? "dateCommande=" + dateCommande + ", " : "") +
            (clientId != null ? "clientId=" + clientId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
