package com.johnhelder.inventory.service.impl;

import com.johnhelder.inventory.domain.Product;
import com.johnhelder.inventory.domain.ProductRawMaterial;
import com.johnhelder.inventory.domain.RawMaterial;
import com.johnhelder.inventory.dto.ProductRawMaterialRequestDTO;
import com.johnhelder.inventory.dto.ProductRawMaterialResponseDTO;
import com.johnhelder.inventory.exception.BusinessException;
import com.johnhelder.inventory.exception.ResourceNotFoundException;
import com.johnhelder.inventory.repository.ProductRawMaterialRepository;
import com.johnhelder.inventory.repository.ProductRepository;
import com.johnhelder.inventory.repository.RawMaterialRepository;
import com.johnhelder.inventory.service.ProductRawMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductRawMaterialServiceImpl implements ProductRawMaterialService {

    private final ProductRawMaterialRepository repository;
    private final ProductRepository productRepository;
    private final RawMaterialRepository rawMaterialRepository;

    @Override
    public ProductRawMaterialResponseDTO create(ProductRawMaterialRequestDTO dto) {

        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        RawMaterial rawMaterial = rawMaterialRepository.findById(dto.rawMaterialId())
                .orElseThrow(() -> new ResourceNotFoundException("Raw material not found"));

        repository.findByProductIdAndRawMaterialId(
                dto.productId(),
                dto.rawMaterialId()
        ).ifPresent(rel -> {
            throw new BusinessException("Relationship already exists");
        });

        ProductRawMaterial entity = ProductRawMaterial.builder()
                .product(product)
                .rawMaterial(rawMaterial)
                .quantityRequired(dto.quantityRequired())
                .build();

        ProductRawMaterial saved = repository.save(entity);

        return new ProductRawMaterialResponseDTO(
                saved.getId(),
                saved.getProduct().getId(),
                saved.getProduct().getName(),
                saved.getRawMaterial().getId(),
                saved.getRawMaterial().getName(),
                saved.getQuantityRequired()
        );
    }

    @Override
    public List<ProductRawMaterialResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(entity -> new ProductRawMaterialResponseDTO(
                        entity.getId(),
                        entity.getProduct().getId(),
                        entity.getProduct().getName(),
                        entity.getRawMaterial().getId(),
                        entity.getRawMaterial().getName(),
                        entity.getQuantityRequired()
                ))
                .toList();
    }

    @Override
    public ProductRawMaterialResponseDTO findById(Long id) {

        ProductRawMaterial entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Relationship not found"));

        return new ProductRawMaterialResponseDTO(
                entity.getId(),
                entity.getProduct().getId(),
                entity.getProduct().getName(),
                entity.getRawMaterial().getId(),
                entity.getRawMaterial().getName(),
                entity.getQuantityRequired()
        );
    }

    @Override
    public ProductRawMaterialResponseDTO update(Long id, ProductRawMaterialRequestDTO dto) {

        ProductRawMaterial existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Relationship not found"));

        repository.findByProductIdAndRawMaterialId(dto.productId(), dto.rawMaterialId())
                .filter(rel -> !rel.getId().equals(id))
                .ifPresent(rel -> {
                    throw new BusinessException("Relationship already exists");
                });

        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        RawMaterial rawMaterial = rawMaterialRepository.findById(dto.rawMaterialId())
                .orElseThrow(() -> new ResourceNotFoundException("Raw material not found"));

        existing.setProduct(product);
        existing.setRawMaterial(rawMaterial);
        existing.setQuantityRequired(dto.quantityRequired());

        ProductRawMaterial updated = repository.save(existing);

        return new ProductRawMaterialResponseDTO(
                updated.getId(),
                updated.getProduct().getId(),
                updated.getProduct().getName(),
                updated.getRawMaterial().getId(),
                updated.getRawMaterial().getName(),
                updated.getQuantityRequired()
        );
    }

    @Override
    public void delete(Long id) {
        ProductRawMaterial entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Relationship not found"));

        repository.delete(entity);
    }
}
