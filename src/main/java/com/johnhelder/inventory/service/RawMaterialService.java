package com.johnhelder.inventory.service;

import com.johnhelder.inventory.dto.RawMaterialRequestDTO;
import com.johnhelder.inventory.dto.RawMaterialResponseDTO;

import java.util.List;

public interface RawMaterialService {

    RawMaterialResponseDTO create(RawMaterialRequestDTO dto);

    List<RawMaterialResponseDTO> findAll();

    RawMaterialResponseDTO findById(Long id);

    RawMaterialResponseDTO update(Long id, RawMaterialRequestDTO dto);

    void delete(Long id);
}
