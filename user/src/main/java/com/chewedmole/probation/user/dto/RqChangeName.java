package com.chewedmole.probation.user.dto;

import lombok.Data;

@Data
public class RqChangeName {
    private String name;
    private String surname;
    private String patronymic;
}
