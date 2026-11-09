package pe.edu.upc.avisum.fleet.interfaces.rest;

import pe.edu.upc.avisum.fleet.domain.model.BusUnit;
import pe.edu.upc.avisum.fleet.domain.model.BusUnitStatus;

public record BusUnitResource(
        Long id,
        String plateNumber,
        String route,
        Double currentLatitude,
        Double currentLongitude,
        BusUnitStatus status) {

    public static BusUnitResource from(BusUnit unit) {
        return new BusUnitResource(
                unit.getId(),
                unit.getPlateNumber(),
                unit.getRoute(),
                unit.getCurrentLatitude(),
                unit.getCurrentLongitude(),
                unit.getStatus());
    }
}