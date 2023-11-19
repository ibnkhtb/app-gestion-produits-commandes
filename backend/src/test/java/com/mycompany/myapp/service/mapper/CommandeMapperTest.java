package com.mycompany.myapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class CommandeMapperTest {

    private CommandeMapper commandeMapper;

    @BeforeEach
    public void setUp() {
        commandeMapper = new CommandeMapperImpl();
    }
}
