package pe.edu.upc.avisum.identity.interfaces.rest;

import jakarta.validation.constraints.PositiveOrZero;

public record ShiftEndRequest(
        @PositiveOrZero Double distanceKm,
        @PositiveOrZero Integer durationSeconds,
        @PositiveOrZero Integer passengerCount,
        @PositiveOrZero Double fareCollected) {
}