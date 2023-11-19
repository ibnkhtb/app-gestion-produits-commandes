package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClientDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientDTO.class);
        ClientDTO clientDTO1 = new ClientDTO();
        clientDTO1.setIdClient(1L);
        ClientDTO clientDTO2 = new ClientDTO();
        assertThat(clientDTO1).isNotEqualTo(clientDTO2);
        clientDTO2.setIdClient(clientDTO1.getIdClient());
        assertThat(clientDTO1).isEqualTo(clientDTO2);
        clientDTO2.setIdClient(2L);
        assertThat(clientDTO1).isNotEqualTo(clientDTO2);
        clientDTO1.setIdClient(null);
        assertThat(clientDTO1).isNotEqualTo(clientDTO2);
    }
}
