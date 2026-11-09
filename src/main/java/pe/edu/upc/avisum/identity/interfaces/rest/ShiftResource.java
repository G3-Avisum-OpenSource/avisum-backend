package pe.edu.upc.avisum.identity.interfaces.rest;

import pe.edu.upc.avisum.identity.domain.model.Shift;
import pe.edu.upc.avisum.identity.domain.model.ShiftStatus;

import java.time.Instant;

public record ShiftResource(
        Long id,
        Long employeeId,
        Long busUnitId,
        String routeName,
        String routeOrigin,
        String routeDestination,
        Double distanceKm,
        Integer durationSeconds,
        Integer passengerCount,
        Double fareCollected,
        ShiftStatus status,
        Instant startedAt,
        Instant endedAt) {

    public static ShiftResource from(Shift s) {
        return new ShiftResource(
                s.getId(), s.getEmployeeId(), s.getBusUnitId(), s.getRouteName(), s.getRouteOrigin(),
                s.getRouteDestination(), s.getDistanceKm(), s.getDurationSeconds(), s.getPassengerCount(),
                s.getFareCollected(), s.getStatus(), s.getStartedAt(), s.getEndedAt());
    }
}