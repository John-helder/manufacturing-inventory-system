package com.johnhelder.inventory.service;

import com.johnhelder.inventory.dto.ProductRawMaterialRequestDTO;
import com.johnhelder.inventory.dto.ProductRawMaterialResponseDTO;

import java.util.List;

public interface ProductRawMaterialService {

    ProductRawMaterialResponseDTO create(ProductRawMaterialRequestDTO dto);

    List<ProductRawMaterialResponseDTO> findAll();

    ProductRawMaterialResponseDTO findById(Long id);

    ProductRawMaterialResponseDTO update(Long id, ProductRawMaterialRequestDTO dto);

    void delete(Long id);
}
