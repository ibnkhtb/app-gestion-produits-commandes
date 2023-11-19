package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProduitTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Produit getProduitSample1() {
        return new Produit()
            .idProduit(1L)
            .nomProduit("nomProduit1")
            .descriptionProduit("descriptionProduit1")
            .imageProduit("imageProduit1");
    }

    public static Produit getProduitSample2() {
        return new Produit()
            .idProduit(2L)
            .nomProduit("nomProduit2")
            .descriptionProduit("descriptionProduit2")
            .imageProduit("imageProduit2");
    }

    public static Produit getProduitRandomSampleGenerator() {
        return new Produit()
            .idProduit(longCount.incrementAndGet())
            .nomProduit(UUID.randomUUID().toString())
            .descriptionProduit(UUID.randomUUID().toString())
            .imageProduit(UUID.randomUUID().toString());
    }
}
