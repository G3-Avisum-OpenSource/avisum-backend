package pe.edu.upc.avisum.fleet.interfaces.rest;

import pe.edu.upc.avisum.fleet.domain.model.BusUnitStatus;

/** Actualización parcial: solo se cambian los campos enviados. */
public record BusUnitPatchRequest(
        String plateNumber,
        String route,
        BusUnitStatus status) {
}