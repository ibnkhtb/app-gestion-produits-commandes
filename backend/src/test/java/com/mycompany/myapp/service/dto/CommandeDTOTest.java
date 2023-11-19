package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CommandeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommandeDTO.class);
        CommandeDTO commandeDTO1 = new CommandeDTO();
        commandeDTO1.setIdCommande(1L);
        CommandeDTO commandeDTO2 = new CommandeDTO();
        assertThat(commandeDTO1).isNotEqualTo(commandeDTO2);
        commandeDTO2.setIdCommande(commandeDTO1.getIdCommande());
        assertThat(commandeDTO1).isEqualTo(commandeDTO2);
        commandeDTO2.setIdCommande(2L);
        assertThat(commandeDTO1).isNotEqualTo(commandeDTO2);
        commandeDTO1.setIdCommande(null);
        assertThat(commandeDTO1).isNotEqualTo(commandeDTO2);
    }
}
