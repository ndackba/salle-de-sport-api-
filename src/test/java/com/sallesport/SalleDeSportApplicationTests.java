package com.sallesport;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/**
 * Vérifie que le contexte Spring démarre correctement (toutes les beans
 * s'assemblent sans erreur). Utilise une base H2 en mémoire pour ne pas
 * dépendre d'un vrai MySQL pendant les tests.
 */
@SpringBootTest
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;MODE=MySQL",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class SalleDeSportApplicationTests {

    @Test
    void contextLoads() {
    }
}
