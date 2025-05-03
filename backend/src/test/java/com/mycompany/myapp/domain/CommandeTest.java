package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.ClientTestSamples.*;
import static com.mycompany.myapp.domain.CommandeTestSamples.*;
import static com.mycompany.myapp.domain.ProduitTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CommandeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Commande.class);
        Commande commande1 = getCommandeSample1();
        Commande commande2 = new Commande();
        assertThat(commande1).isNotEqualTo(commande2);

        commande2.setIdCommande(commande1.getIdCommande());
        assertThat(commande1).isEqualTo(commande2);

        commande2 = getCommandeSample2();
        assertThat(commande1).isNotEqualTo(commande2);
    }

    @Test
    void clientTest() throws Exception {
        Commande commande = getCommandeRandomSampleGenerator();
        Client clientBack = getClientRandomSampleGenerator();

        commande.setClient(clientBack);
        assertThat(commande.getClient()).isEqualTo(clientBack);

        commande.client(null);
        assertThat(commande.getClient()).isNull();
    }

    @Test
    void produitTest() throws Exception {
        Commande commande = getCommandeRandomSampleGenerator();
        Produit produitBack = getProduitRandomSampleGenerator();

        commande.setProduit(produitBack);
        assertThat(commande.getProduit()).isEqualTo(produitBack);

        commande.produit(null);
        assertThat(commande.getProduit()).isNull();
    }
}
