package com.chewedmole.probation.pet.dto;

import lombok.Data;

@Data
public class RqCreatePet {
    private String name;
    private String type;
    private Long ownerId;
    private String password;
}
