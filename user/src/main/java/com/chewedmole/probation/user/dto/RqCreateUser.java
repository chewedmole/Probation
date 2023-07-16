package com.chewedmole.probation.user.dto;

import lombok.Data;

@Data
public class RqCreateUser {
    private String name;
    private String surname;
    private String patronymic;
    private String email;
    private String password;
    private String phoneNumber;
}
