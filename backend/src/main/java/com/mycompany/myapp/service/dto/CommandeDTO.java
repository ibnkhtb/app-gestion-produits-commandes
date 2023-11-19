package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Commande} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CommandeDTO implements Serializable {

    private Long idCommande;

    private Instant dateCommande;

    private ClientDTO client;

    public Long getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(Long idCommande) {
        this.idCommande = idCommande;
    }

    public Instant getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Instant dateCommande) {
        this.dateCommande = dateCommande;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommandeDTO)) {
            return false;
        }

        CommandeDTO commandeDTO = (CommandeDTO) o;
        if (this.idCommande == null) {
            return false;
        }
        return Objects.equals(this.idCommande, commandeDTO.idCommande);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idCommande);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommandeDTO{" +
            "idCommande=" + getIdCommande() +
            ", dateCommande='" + getDateCommande() + "'" +
            ", client=" + getClient() +
            "}";
    }
}
