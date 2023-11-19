package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class ProduitMapperTest {

    private ProduitMapper produitMapper;

    @BeforeEach
    public void setUp() {
        produitMapper = new ProduitMapperImpl();
    }
}
