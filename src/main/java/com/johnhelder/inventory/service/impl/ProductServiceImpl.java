package com.johnhelder.inventory.service.impl;

import com.johnhelder.inventory.domain.Product;
import com.johnhelder.inventory.dto.ProductRequestDTO;
import com.johnhelder.inventory.dto.ProductResponseDTO;
import com.johnhelder.inventory.repository.ProductRepository;
import com.johnhelder.inventory.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    @Override
    public ProductResponseDTO create(ProductRequestDTO dto) {
        Product product = Product.builder()
                .code(dto.code())
                .name(dto.name())
                .value(dto.value())
                .build();

        Product saved = productRepository.save(product);

        return new ProductResponseDTO(
                saved.getId(),
                saved.getCode(),
                saved.getName(),
                saved.getValue()
        );
    }

    @Override
    public List<ProductResponseDTO> findAll() {
        return productRepository.findAll()
                .stream()
                .map(product -> new ProductResponseDTO(
                        product.getId(),
                        product.getCode(),
                        product.getName(),
                        product.getValue()
                ))
                .toList();
    }

    @Override
    public ProductResponseDTO findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return new ProductResponseDTO(
                product.getId(),
                product.getCode(),
                product.getName(),
                product.getValue()
        );
    }

    @Override
    public ProductResponseDTO update(Long id, ProductRequestDTO dto) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existing.setCode(dto.code());
        existing.setName(dto.name());
        existing.setValue(dto.value());

        Product updated = productRepository.save(existing);

        return new ProductResponseDTO(
                updated.getId(),
                updated.getCode(),
                updated.getName(),
                updated.getValue()
        );
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}

