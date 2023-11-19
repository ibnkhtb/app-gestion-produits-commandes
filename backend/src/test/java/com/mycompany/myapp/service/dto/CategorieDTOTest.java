package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CategorieDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategorieDTO.class);
        CategorieDTO categorieDTO1 = new CategorieDTO();
        categorieDTO1.setIdCategorie(1L);
        CategorieDTO categorieDTO2 = new CategorieDTO();
        assertThat(categorieDTO1).isNotEqualTo(categorieDTO2);
        categorieDTO2.setIdCategorie(categorieDTO1.getIdCategorie());
        assertThat(categorieDTO1).isEqualTo(categorieDTO2);
        categorieDTO2.setIdCategorie(2L);
        assertThat(categorieDTO1).isNotEqualTo(categorieDTO2);
        categorieDTO1.setIdCategorie(null);
        assertThat(categorieDTO1).isNotEqualTo(categorieDTO2);
    }
}
