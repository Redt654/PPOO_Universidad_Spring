package dev.sadis.ppoo.dto.response;

import dev.sadis.ppoo.entity.Career;

public record CareerResponse(
        Long id,
        String name,
        String code
) {
    public static CareerResponse build(Career career){
        return new CareerResponse(
                career.getId(),
                career.getName(),
                career.getCode()
        );
    }
}
