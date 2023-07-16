package com.chewedmole.probation.pet.dto;

import lombok.Data;

@Data
public class RqEditPetInfo {
    private String type;
    private String name;
    private Long ownerId;
}
