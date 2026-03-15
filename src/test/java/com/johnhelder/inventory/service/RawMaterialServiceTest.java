package com.johnhelder.inventory.service;

import com.johnhelder.inventory.domain.RawMaterial;
import com.johnhelder.inventory.dto.RawMaterialRequestDTO;
import com.johnhelder.inventory.dto.RawMaterialResponseDTO;
import com.johnhelder.inventory.repository.RawMaterialRepository;
import com.johnhelder.inventory.service.impl.RawMaterialServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RawMaterialServiceTest {

    @Mock
    private RawMaterialRepository repository;

    @InjectMocks
    private RawMaterialServiceImpl service;

    @Test
    void shouldCreateRawMaterial() {

        RawMaterial material = new RawMaterial();
        material.setName("Steel");

        when(repository.save(any())).thenReturn(material);

        RawMaterialRequestDTO dto =
                new RawMaterialRequestDTO("RM01", "Steel", 100);

        RawMaterialResponseDTO result = service.create(dto);

        assertEquals("Steel", result.name());

        verify(repository).save(any());
    }

    @Test
    void shouldReturnAllRawMaterials() {

        RawMaterial material = new RawMaterial();
                material.setName("Steel");
                material.setCode("RM01");
                material.setStockQuantity(100);

        when(repository.findAll()).thenReturn(List.of(material));

        List<RawMaterialResponseDTO> result = service.findAll();

        assertEquals(1, result.size());
        assertEquals("Steel", result.get(0).name());

        verify(repository).findAll();

    }

    @Test
    void shouldReturnRawMaterialWhenIdExists() {
        RawMaterial material = new RawMaterial();
        material.setId(1L);
        material.setCode("RM01");
        material.setName("Steel");
        material.setStockQuantity(100);

        when(repository.findById(1L)).thenReturn(Optional.of(material));

        RawMaterialResponseDTO result = service.findById(1L);

        assertEquals(1L, result.id());
        assertEquals("Steel", result.name());

        verify(repository).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenRawMaterialNotFound() {

        when(repository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.findById(1L)
        );

        assertEquals("Raw material not found", exception.getMessage());

        verify(repository).findById(1L);

    }

    @Test
    void shouldUpdateRawMaterial() {

        RawMaterial material = new RawMaterial();
        material.setId(1L);
        material.setCode("RM01");
        material.setName("Steel");
        material.setStockQuantity(100);

        when(repository.findById(1L)).thenReturn(Optional.of(material));
        when(repository.save(any())).thenReturn(material);

        RawMaterialRequestDTO dto =
                new RawMaterialRequestDTO("RM001", "Steels", 200);

        RawMaterialResponseDTO updated = service.update(1L, dto);

        assertEquals("Steels", updated.name());
        assertEquals("RM001", updated.code());

        verify(repository).findById(1L);
        verify(repository).save(any());

    }

    @Test
    void shouldDeleteRawMaterial() {

        service.delete(1L);
        verify(repository).deleteById(1L);
    }
}
