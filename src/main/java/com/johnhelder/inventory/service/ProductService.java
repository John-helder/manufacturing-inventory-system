package com.johnhelder.inventory.service;

import com.johnhelder.inventory.dto.ProductProductionAvailabilityDTO;
import com.johnhelder.inventory.dto.ProductRequestDTO;
import com.johnhelder.inventory.dto.ProductResponseDTO;

import java.util.List;

public interface ProductService {

    ProductResponseDTO create(ProductRequestDTO dto);

    List<ProductResponseDTO> findAll();

    ProductResponseDTO findById(Long id);

    ProductResponseDTO update(Long id, ProductRequestDTO dto);

    void delete(Long id);

    List<ProductProductionAvailabilityDTO> getProductionAvailability();

}
