package com.johnhelder.inventory.service.impl;

import com.johnhelder.inventory.domain.Product;
import com.johnhelder.inventory.domain.ProductRawMaterial;
import com.johnhelder.inventory.dto.ProductProductionAvailabilityDTO;
import com.johnhelder.inventory.dto.ProductRequestDTO;
import com.johnhelder.inventory.dto.ProductResponseDTO;
import com.johnhelder.inventory.exception.ResourceNotFoundException;
import com.johnhelder.inventory.repository.ProductRawMaterialRepository;
import com.johnhelder.inventory.repository.ProductRepository;
import com.johnhelder.inventory.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ProductRawMaterialRepository productRawMaterialRepository;

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
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

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
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

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

    @Override
    public List<ProductProductionAvailabilityDTO> getProductionAvailability() {

        List<ProductProductionAvailabilityDTO> response = new ArrayList<>();

        List<Product> products = productRepository.findAll();

        for (Product product : products) {

            List<ProductRawMaterial> relations =
                    productRawMaterialRepository.findByProductId(product.getId());

            if (relations.isEmpty()) {
                response.add(
                        new ProductProductionAvailabilityDTO(
                                product.getId(),
                                product.getName(),
                                false,
                                0
                        )
                );
                continue;
            }

            int maxPossible = Integer.MAX_VALUE;

            for (ProductRawMaterial rel : relations) {

                int possible =
                        rel.getRawMaterial().getStockQuantity()
                                / rel.getQuantityRequired();

                maxPossible = Math.min(maxPossible, possible);
            }

            boolean canProduce = maxPossible > 0;

            response.add(
                    new ProductProductionAvailabilityDTO(
                            product.getId(),
                            product.getName(),
                            canProduce,
                            maxPossible
                    )
            );
        }

        return response;
    }

}

