package com.chewedmole.probation.pet.repository;

import com.chewedmole.probation.pet.entity.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<PetEntity, Long> {
}
