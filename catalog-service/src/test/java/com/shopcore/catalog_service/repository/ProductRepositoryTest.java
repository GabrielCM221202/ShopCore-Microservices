// Paquete donde vive esta clase de prueba
package com.shopcore.catalog_service.repository;

// Importamos nuestro modelo Product
import com.shopcore.catalog_service.model.Product;

// Importamos las anotaciones de JUnit y Spring necesarias
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

// --------------------
// ANOTACIONES DE LA CLASE
// --------------------

// @Testcontainers: le dice a JUnit que esta clase usará contenedores Docker.
// JUnit se encargará de encenderlos antes de las pruebas y apagarlos al terminar.
@Testcontainers

// @DataMongoTest: es un "slice test". En lugar de levantar toda la aplicación,
// solo carga lo mínimo necesario para probar MongoDB (repositorios y entidades).
@DataMongoTest
class ProductRepositoryTest {

    // --------------------
    // CONFIGURACIÓN DEL CONTENEDOR DE MONGO
    // --------------------

    // @Container: marca esta variable como el contenedor que JUnit debe administrar.
    // @ServiceConnection: Spring Boot 3.1 se conecta automáticamente al puerto/IP del contenedor
    // sin que tengamos que configurar nada manualmente.
    @Container
    @ServiceConnection
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0");
    // Aquí definimos un contenedor temporal de MongoDB versión 7.0.
    // Al ser "static", se levanta una sola vez para todas las pruebas de esta clase.

    // --------------------
    // INYECCIÓN DEL REPOSITORIO
    // --------------------

    // @Autowired: Spring Boot inyecta el repositorio real (ProductRepository)
    // para que podamos usar sus métodos como save(), findById(), etc.
    @Autowired
    private ProductRepository productRepository;

    // --------------------
    // PRUEBA UNITARIA
    // --------------------

    // @Test: indica que este método es una prueba que JUnit debe ejecutar.
    @Test
    void shouldSaveAndRetrieveDynamicProduct() {
        // --------------------
        // ARRANGE (Preparar escenario)
        // --------------------
        // Creamos un objeto Product con datos de ejemplo.
        Product product = new Product();
        product.setName("Laptop Pro"); // Nombre del producto
        product.setSlug("laptop-pro"); // Identificador único tipo "slug"
        product.setBasePrice(new BigDecimal("1500.00")); // Precio base
        // Atributos dinámicos: MongoDB permite guardar un mapa flexible como JSON.
        product.setAttributes(Map.of("RAM", "16GB", "CPU", "M3", "Color", "Space Gray"));
        product.setActive(true); // Indicamos que el producto está activo

        // --------------------
        // ACT (Ejecutar acción real)
        // --------------------
        // Guardamos el producto en la base de datos temporal (contenedor Mongo).
        Product savedProduct = productRepository.save(product);

        // --------------------
        // ASSERT (Validar resultados)
        // --------------------
        // Verificamos que MongoDB haya asignado un ID único al producto guardado.
        assertNotNull(savedProduct.getId(), "El ID no debería ser nulo después de guardar");

        // Probamos nuestro método personalizado findBySlug:
        // 1. Confirmamos que el producto existe en la BD.
        assertTrue(productRepository.findBySlug("laptop-pro").isPresent());
        // 2. Validamos que el atributo RAM sea exactamente "16GB".
        assertEquals("16GB", productRepository.findBySlug("laptop-pro").get().getAttributes().get("RAM"));
    }
}
