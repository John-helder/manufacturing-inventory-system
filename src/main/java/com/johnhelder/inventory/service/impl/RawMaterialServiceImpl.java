package com.johnhelder.inventory.service.impl;

import com.johnhelder.inventory.domain.RawMaterial;
import com.johnhelder.inventory.dto.RawMaterialRequestDTO;
import com.johnhelder.inventory.dto.RawMaterialResponseDTO;
import com.johnhelder.inventory.exception.ResourceNotFoundException;
import com.johnhelder.inventory.repository.RawMaterialRepository;
import com.johnhelder.inventory.service.RawMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RawMaterialServiceImpl implements RawMaterialService {

    private final RawMaterialRepository repository;

    @Override
    public RawMaterialResponseDTO create(RawMaterialRequestDTO dto) {

        RawMaterial material = RawMaterial.builder()
                .code(dto.code())
                .name(dto.name())
                .stockQuantity(dto.quantity())
                .category(dto.category())
                .minimumQuantity(dto.minimumQuantity())
                .unit(dto.unit())
                .location(dto.location())
                .unitPrice(dto.unitPrice())
                .build();


        RawMaterial saved = repository.save(material);

        return new RawMaterialResponseDTO(
                saved.getId(),
                saved.getCode(),
                saved.getName(),
                saved.getStockQuantity(),
                saved.getCategory(),
                saved.getMinimumQuantity(),
                saved.getUnit(),
                saved.getLocation(),
                saved.getUnitPrice()
        );
    }

    @Override
    public List<RawMaterialResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(rawMaterial -> new RawMaterialResponseDTO(
                        rawMaterial.getId(),
                        rawMaterial.getCode(),
                        rawMaterial.getName(),
                        rawMaterial.getStockQuantity(),
                        rawMaterial.getCategory(),
                        rawMaterial.getMinimumQuantity(),
                        rawMaterial.getUnit(),
                        rawMaterial.getLocation(),
                        rawMaterial.getUnitPrice()
                ))
                .toList();
    }

    @Override
    public RawMaterialResponseDTO findById(Long id) {

        RawMaterial rawMaterial = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Raw material not found"));

        return new RawMaterialResponseDTO(
                rawMaterial.getId(),
                rawMaterial.getCode(),
                rawMaterial.getName(),
                rawMaterial.getStockQuantity(),
                rawMaterial.getCategory(),
                rawMaterial.getMinimumQuantity(),
                rawMaterial.getUnit(),
                rawMaterial.getLocation(),
                rawMaterial.getUnitPrice()

        );
    }

    @Override
    public RawMaterialResponseDTO update(Long id, RawMaterialRequestDTO dto) {
        RawMaterial existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Raw material not found"));

        existing.setCode(dto.code());
        existing.setName(dto.name());
        existing.setStockQuantity(dto.quantity());
        existing.setCategory(dto.category());
        existing.setMinimumQuantity(dto.minimumQuantity());
        existing.setLocation(dto.location());
        existing.setUnit(dto.unit());
        existing.setUnitPrice(dto.unitPrice());

        RawMaterial updated = repository.save(existing);

        return new RawMaterialResponseDTO(
                updated.getId(),
                updated.getCode(),
                updated.getName(),
                updated.getStockQuantity(),
                updated.getCategory(),
                updated.getMinimumQuantity(),
                updated.getUnit(),
                updated.getLocation(),
                updated.getUnitPrice()
        );
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

}