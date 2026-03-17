package com.johnhelder.inventory.service;

import com.johnhelder.inventory.domain.Product;
import com.johnhelder.inventory.domain.ProductRawMaterial;
import com.johnhelder.inventory.domain.RawMaterial;
import com.johnhelder.inventory.dto.ProductRawMaterialRequestDTO;
import com.johnhelder.inventory.dto.ProductRawMaterialResponseDTO;
import com.johnhelder.inventory.repository.ProductRawMaterialRepository;
import com.johnhelder.inventory.repository.ProductRepository;
import com.johnhelder.inventory.repository.RawMaterialRepository;
import com.johnhelder.inventory.service.impl.ProductRawMaterialServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductRawMaterialServiceTest {

    @Mock
    private ProductRawMaterialRepository repository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private RawMaterialRepository rawMaterialRepository;

    @InjectMocks
    private ProductRawMaterialServiceImpl service;

    // -------------------------
    // create
    // -------------------------

    @Test
    void shouldCreateProductRawMaterial() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Cadeira");

        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setId(1L);
        rawMaterial.setName("Aço");

        ProductRawMaterial entity = new ProductRawMaterial();
        entity.setId(1L);
        entity.setProduct(product);
        entity.setRawMaterial(rawMaterial);
        entity.setQuantityRequired(4);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(rawMaterialRepository.findById(1L)).thenReturn(Optional.of(rawMaterial));
        when(repository.findByProductIdAndRawMaterialId(1L, 1L)).thenReturn(Optional.empty());
        when(repository.save(any())).thenReturn(entity);

        ProductRawMaterialRequestDTO dto =
                new ProductRawMaterialRequestDTO(1L, 1L, 4);

        ProductRawMaterialResponseDTO result = service.create(dto);

        assertEquals(1L, result.id());
        assertEquals("Cadeira", result.productName());
        assertEquals("Aço", result.rawMaterialName());
        assertEquals(4, result.quantityRequired());

        verify(repository).save(any());
    }

    @Test
    void shouldThrowExceptionWhenProductNotFoundOnCreate() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        ProductRawMaterialRequestDTO dto =
                new ProductRawMaterialRequestDTO(1L, 1L, 4);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.create(dto)
        );

        assertEquals("Product not found", exception.getMessage());

        verify(repository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenRawMaterialNotFoundOnCreate() {
        Product product = new Product();
        product.setId(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(rawMaterialRepository.findById(1L)).thenReturn(Optional.empty());

        ProductRawMaterialRequestDTO dto =
                new ProductRawMaterialRequestDTO(1L, 1L, 4);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.create(dto)
        );

        assertEquals("Raw material not found", exception.getMessage());

        verify(repository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenRelationshipAlreadyExistsOnCreate() {
        Product product = new Product();
        product.setId(1L);

        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setId(1L);

        ProductRawMaterial existing = new ProductRawMaterial();
        existing.setId(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(rawMaterialRepository.findById(1L)).thenReturn(Optional.of(rawMaterial));
        when(repository.findByProductIdAndRawMaterialId(1L, 1L)).thenReturn(Optional.of(existing));

        ProductRawMaterialRequestDTO dto =
                new ProductRawMaterialRequestDTO(1L, 1L, 4);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.create(dto)
        );

        assertEquals("Relationship already exists", exception.getMessage());

        verify(repository, never()).save(any());
    }

    // -------------------------
    // findAll
    // -------------------------

    @Test
    void shouldReturnAllProductRawMaterials() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Cadeira");

        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setId(1L);
        rawMaterial.setName("Aço");

        ProductRawMaterial entity = new ProductRawMaterial();
        entity.setId(1L);
        entity.setProduct(product);
        entity.setRawMaterial(rawMaterial);
        entity.setQuantityRequired(4);

        when(repository.findAll()).thenReturn(List.of(entity));

        List<ProductRawMaterialResponseDTO> result = service.findAll();

        assertEquals(1, result.size());
        assertEquals("Cadeira", result.get(0).productName());
        assertEquals("Aço", result.get(0).rawMaterialName());

        verify(repository).findAll();
    }

    // -------------------------
    // findById
    // -------------------------

    @Test
    void shouldReturnProductRawMaterialWhenIdExists() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Cadeira");

        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setId(1L);
        rawMaterial.setName("Aço");

        ProductRawMaterial entity = new ProductRawMaterial();
        entity.setId(1L);
        entity.setProduct(product);
        entity.setRawMaterial(rawMaterial);
        entity.setQuantityRequired(4);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        ProductRawMaterialResponseDTO result = service.findById(1L);

        assertEquals(1L, result.id());
        assertEquals("Cadeira", result.productName());
        assertEquals("Aço", result.rawMaterialName());

        verify(repository).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenProductRawMaterialNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.findById(1L)
        );

        assertEquals("Relationship not found", exception.getMessage());

        verify(repository).findById(1L);
    }

    // -------------------------
    // update
    // -------------------------

    @Test
    void shouldUpdateProductRawMaterial() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Cadeira");

        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setId(1L);
        rawMaterial.setName("Aço");

        ProductRawMaterial existing = new ProductRawMaterial();
        existing.setId(1L);
        existing.setProduct(product);
        existing.setRawMaterial(rawMaterial);
        existing.setQuantityRequired(4);

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.findByProductIdAndRawMaterialId(1L, 1L)).thenReturn(Optional.empty());
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(rawMaterialRepository.findById(1L)).thenReturn(Optional.of(rawMaterial));
        when(repository.save(any())).thenReturn(existing);

        ProductRawMaterialRequestDTO dto =
                new ProductRawMaterialRequestDTO(1L, 1L, 10);

        ProductRawMaterialResponseDTO updated = service.update(1L, dto);

        assertEquals("Cadeira", updated.productName());
        assertEquals("Aço", updated.rawMaterialName());

        verify(repository).findById(1L);
        verify(repository).save(any());
    }

    @Test
    void shouldThrowExceptionWhenRelationshipNotFoundOnUpdate() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        ProductRawMaterialRequestDTO dto =
                new ProductRawMaterialRequestDTO(1L, 1L, 4);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.update(1L, dto)
        );

        assertEquals("Relationship not found", exception.getMessage());

        verify(repository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenRelationshipAlreadyExistsOnUpdate() {
        ProductRawMaterial existing = new ProductRawMaterial();
        existing.setId(1L);

        ProductRawMaterial duplicate = new ProductRawMaterial();
        duplicate.setId(2L); // ID diferente = é duplicata

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.findByProductIdAndRawMaterialId(1L, 1L)).thenReturn(Optional.of(duplicate));

        ProductRawMaterialRequestDTO dto =
                new ProductRawMaterialRequestDTO(1L, 1L, 4);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.update(1L, dto)
        );

        assertEquals("Relationship already exists", exception.getMessage());

        verify(repository, never()).save(any());
    }

    // -------------------------
    // delete
    // -------------------------

    @Test
    void shouldDeleteProductRawMaterial() {
        ProductRawMaterial entity = new ProductRawMaterial();
        entity.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        service.delete(1L);

        verify(repository).delete(entity);
    }

    @Test
    void shouldThrowExceptionWhenProductRawMaterialNotFoundOnDelete() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.delete(1L)
        );

        assertEquals("Relationship not found", exception.getMessage());

        verify(repository, never()).delete(any());
    }
}