package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.CategorieTestSamples.*;
import static com.mycompany.myapp.domain.ProduitTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProduitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Produit.class);
        Produit produit1 = getProduitSample1();
        Produit produit2 = new Produit();
        assertThat(produit1).isNotEqualTo(produit2);

        produit2.setIdProduit(produit1.getIdProduit());
        assertThat(produit1).isEqualTo(produit2);

        produit2 = getProduitSample2();
        assertThat(produit1).isNotEqualTo(produit2);
    }

    @Test
    void categorieTest() throws Exception {
        Produit produit = getProduitRandomSampleGenerator();
        Categorie categorieBack = getCategorieRandomSampleGenerator();

        produit.setCategorie(categorieBack);
        assertThat(produit.getCategorie()).isEqualTo(categorieBack);

        produit.categorie(null);
        assertThat(produit.getCategorie()).isNull();
    }
}
