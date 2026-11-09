package pe.edu.upc.avisum.fleet.interfaces.rest;

import jakarta.validation.constraints.NotBlank;
import pe.edu.upc.avisum.fleet.domain.model.BusUnitStatus;

public record BusUnitRequest(
        @NotBlank String plateNumber,
        @NotBlank String route,
        Double currentLatitude,
        Double currentLongitude,
        BusUnitStatus status) {
}