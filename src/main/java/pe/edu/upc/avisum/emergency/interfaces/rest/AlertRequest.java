package pe.edu.upc.avisum.emergency.interfaces.rest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AlertRequest(
        @NotNull Long employeeId,
        @NotNull Long busUnitId,
        @NotBlank String alertType,
        String description,
        Double latitude,
        Double longitude) {
}