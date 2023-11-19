package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Client;
import com.mycompany.myapp.domain.Commande;
import com.mycompany.myapp.service.dto.ClientDTO;
import com.mycompany.myapp.service.dto.CommandeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Commande} and its DTO {@link CommandeDTO}.
 */
@Mapper(componentModel = "spring")
public interface CommandeMapper extends EntityMapper<CommandeDTO, Commande> {
    @Mapping(target = "client", source = "client", qualifiedByName = "clientIdClient")
    CommandeDTO toDto(Commande s);

    @Named("clientIdClient")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "idClient", source = "idClient")
    ClientDTO toDtoClientIdClient(Client client);
}
