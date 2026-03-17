package com.johnhelder.inventory.service;

import com.johnhelder.inventory.domain.Product;
import com.johnhelder.inventory.domain.ProductRawMaterial;
import com.johnhelder.inventory.domain.RawMaterial;
import com.johnhelder.inventory.dto.ProductProductionAvailabilityDTO;
import com.johnhelder.inventory.dto.ProductRequestDTO;
import com.johnhelder.inventory.dto.ProductResponseDTO;
import com.johnhelder.inventory.repository.ProductRawMaterialRepository;
import com.johnhelder.inventory.repository.ProductRepository;
import com.johnhelder.inventory.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductRawMaterialRepository productRawMaterialRepository;

    @InjectMocks
    private ProductServiceImpl service;


    // ------ create

    @Test
    void shouldCreateProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setCode("PROD001");
        product.setName("Cadeira");
        product.setValue(new BigDecimal("499.90"));

        when(productRepository.save(any())).thenReturn(product);

        ProductRequestDTO dto =
                new ProductRequestDTO("PROD001", "Cadeira", new BigDecimal("499.90"));

        ProductResponseDTO result = service.create(dto);

        assertEquals("PROD001", result.code());
        assertEquals("Cadeira", result.name());
        assertEquals(new BigDecimal("499.90"), result.value());

        verify(productRepository).save(any());
    }

    // -------------------------
    // findAll
    // -------------------------

    @Test
    void shouldReturnAllProducts() {
        Product product = new Product();
        product.setId(1L);
        product.setCode("PROD001");
        product.setName("Cadeira");
        product.setValue(new BigDecimal("499.90"));

        when(productRepository.findAll()).thenReturn(List.of(product));

        List<ProductResponseDTO> result = service.findAll();

        assertEquals(1, result.size());
        assertEquals("Cadeira", result.get(0).name());

        verify(productRepository).findAll();
    }

    // -------------------------
    // findById
    // -------------------------

    @Test
    void shouldReturnProductWhenIdExists() {
        Product product = new Product();
        product.setId(1L);
        product.setCode("PROD001");
        product.setName("Cadeira");
        product.setValue(new BigDecimal("499.90"));

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductResponseDTO result = service.findById(1L);

        assertEquals(1L, result.id());
        assertEquals("Cadeira", result.name());

        verify(productRepository).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.findById(1L)
        );

        assertEquals("Product not found", exception.getMessage());

        verify(productRepository).findById(1L);
    }

    // -------------------------
    // update
    // -------------------------

    @Test
    void shouldUpdateProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setCode("PROD001");
        product.setName("Cadeira");
        product.setValue(new BigDecimal("499.90"));

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any())).thenReturn(product);

        ProductRequestDTO dto =
                new ProductRequestDTO("PROD002", "Cadeira Ergonômica", new BigDecimal("699.90"));

        ProductResponseDTO updated = service.update(1L, dto);

        assertEquals("PROD002", updated.code());
        assertEquals("Cadeira Ergonômica", updated.name());

        verify(productRepository).findById(1L);
        verify(productRepository).save(any());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        ProductRequestDTO dto =
                new ProductRequestDTO("PROD002", "Cadeira Ergonômica", new BigDecimal("699.90"));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.update(1L, dto)
        );

        assertEquals("Product not found", exception.getMessage());

        verify(productRepository).findById(1L);
        verify(productRepository, never()).save(any());
    }

    // -------------------------
    // delete
    // -------------------------

    @Test
    void shouldDeleteProduct() {
        service.delete(1L);
        verify(productRepository).deleteById(1L);
    }

    // -------------------------
    // getProductionAvailability
    // -------------------------

    @Test
    void shouldReturnCanProduceWhenStockIsSufficient() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Cadeira");

        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setId(1L);
        rawMaterial.setStockQuantity(100);

        ProductRawMaterial relation = new ProductRawMaterial();
        relation.setProduct(product);
        relation.setRawMaterial(rawMaterial);
        relation.setQuantityRequired(4);

        when(productRepository.findAll()).thenReturn(List.of(product));
        when(productRawMaterialRepository.findByProductId(1L)).thenReturn(List.of(relation));

        List<ProductProductionAvailabilityDTO> result = service.getProductionAvailability();

        assertEquals(1, result.size());
        assertTrue(result.get(0).canProduce());
        assertEquals(25, result.get(0).maxQuantityPossible());
    }

    @Test
    void shouldReturnCannotProduceWhenStockIsZero() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Cadeira");

        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setId(1L);
        rawMaterial.setStockQuantity(0);

        ProductRawMaterial relation = new ProductRawMaterial();
        relation.setProduct(product);
        relation.setRawMaterial(rawMaterial);
        relation.setQuantityRequired(4);

        when(productRepository.findAll()).thenReturn(List.of(product));
        when(productRawMaterialRepository.findByProductId(1L)).thenReturn(List.of(relation));

        List<ProductProductionAvailabilityDTO> result = service.getProductionAvailability();

        assertEquals(1, result.size());
        assertFalse(result.get(0).canProduce());
        assertEquals(0, result.get(0).maxQuantityPossible());
    }

    @Test
    void shouldReturnCannotProduceWhenNoRawMaterialsLinked() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Cadeira");

        when(productRepository.findAll()).thenReturn(List.of(product));
        when(productRawMaterialRepository.findByProductId(1L)).thenReturn(List.of());

        List<ProductProductionAvailabilityDTO> result = service.getProductionAvailability();

        assertEquals(1, result.size());
        assertFalse(result.get(0).canProduce());
        assertEquals(0, result.get(0).maxQuantityPossible());
    }

    @Test
    void shouldReturnMinimumQuantityWhenMultipleRawMaterials() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Cadeira");

        RawMaterial rm1 = new RawMaterial();
        rm1.setStockQuantity(100);

        RawMaterial rm2 = new RawMaterial();
        rm2.setStockQuantity(30);

        ProductRawMaterial relation1 = new ProductRawMaterial();
        relation1.setProduct(product);
        relation1.setRawMaterial(rm1);
        relation1.setQuantityRequired(4);

        ProductRawMaterial relation2 = new ProductRawMaterial();
        relation2.setProduct(product);
        relation2.setRawMaterial(rm2);
        relation2.setQuantityRequired(3);

        when(productRepository.findAll()).thenReturn(List.of(product));
        when(productRawMaterialRepository.findByProductId(1L)).thenReturn(List.of(relation1, relation2));

        List<ProductProductionAvailabilityDTO> result = service.getProductionAvailability();

        assertEquals(1, result.size());
        assertTrue(result.get(0).canProduce());
        assertEquals(10, result.get(0).maxQuantityPossible());
    }
}