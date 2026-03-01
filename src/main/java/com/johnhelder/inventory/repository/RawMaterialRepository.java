package com.johnhelder.inventory.repository;

import com.johnhelder.inventory.domain.RawMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RawMaterialRepository extends JpaRepository<RawMaterial, Long> {
}
