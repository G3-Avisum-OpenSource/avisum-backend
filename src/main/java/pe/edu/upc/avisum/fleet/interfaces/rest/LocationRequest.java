package pe.edu.upc.avisum.fleet.interfaces.rest;

import jakarta.validation.constraints.NotNull;

public record LocationRequest(
        @NotNull Double latitude,
        @NotNull Double longitude) {
}