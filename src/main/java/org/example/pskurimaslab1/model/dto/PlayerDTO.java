package org.example.pskurimaslab1.model.dto;

public record PlayerDTO(
        Long id,
        String name,
        String surname,
        Integer age,
        Long teamId,
        Long version
) {}
