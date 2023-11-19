package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CategorieTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Categorie getCategorieSample1() {
        return new Categorie().idCategorie(1L).nomCategorie("nomCategorie1");
    }

    public static Categorie getCategorieSample2() {
        return new Categorie().idCategorie(2L).nomCategorie("nomCategorie2");
    }

    public static Categorie getCategorieRandomSampleGenerator() {
        return new Categorie().idCategorie(longCount.incrementAndGet()).nomCategorie(UUID.randomUUID().toString());
    }
}
