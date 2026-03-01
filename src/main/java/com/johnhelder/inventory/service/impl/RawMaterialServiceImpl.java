package com.johnhelder.inventory.service.impl;

import com.johnhelder.inventory.domain.RawMaterial;
import com.johnhelder.inventory.dto.RawMaterialRequestDTO;
import com.johnhelder.inventory.dto.RawMaterialResponseDTO;
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
                .build();

        RawMaterial saved = repository.save(material);

        return new RawMaterialResponseDTO(
                saved.getId(),
                saved.getCode(),
                saved.getName(),
                saved.getStockQuantity()
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
                        rawMaterial.getStockQuantity()
                ))
                .toList();
    }

    @Override
    public RawMaterialResponseDTO findById(Long id) {

        RawMaterial rawMaterial = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Raw material not found"));

        return new RawMaterialResponseDTO(
                rawMaterial.getId(),
                rawMaterial.getCode(),
                rawMaterial.getName(),
                rawMaterial.getStockQuantity()
        );
    }

    @Override
    public RawMaterialResponseDTO update(Long id, RawMaterialRequestDTO dto) {
        RawMaterial existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existing.setCode(dto.code());
        existing.setName(dto.name());
        existing.setStockQuantity(dto.quantity());

        RawMaterial updated = repository.save(existing);

        return new RawMaterialResponseDTO(
                updated.getId(),
                updated.getCode(),
                updated.getName(),
                updated.getStockQuantity()
        );
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

}