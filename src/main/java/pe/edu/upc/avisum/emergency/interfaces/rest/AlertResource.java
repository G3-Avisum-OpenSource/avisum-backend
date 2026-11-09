package pe.edu.upc.avisum.emergency.interfaces.rest;

import pe.edu.upc.avisum.emergency.domain.model.Alert;

import java.time.Instant;

public record AlertResource(
        Long id,
        Long employeeId,
        Long busUnitId,
        String alertType,
        String description,
        Double latitude,
        Double longitude,
        boolean resolved,
        Instant createdAt,
        Instant resolvedAt) {

    public static AlertResource from(Alert a) {
        return new AlertResource(
                a.getId(), a.getEmployeeId(), a.getBusUnitId(), a.getAlertType(), a.getDescription(),
                a.getLatitude(), a.getLongitude(), a.isResolved(), a.getCreatedAt(), a.getResolvedAt());
    }
}