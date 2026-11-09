package pe.edu.upc.avisum.identity.interfaces.rest;

import jakarta.validation.constraints.NotNull;

public record ShiftStartRequest(
        @NotNull Long employeeId,
        @NotNull Long busUnitId,
        String routeOrigin,
        String routeDestination) {
}