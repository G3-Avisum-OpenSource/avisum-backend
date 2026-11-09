package pe.edu.upc.avisum.identity.interfaces.rest;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upc.avisum.identity.application.ShiftService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shifts")
public class ShiftController {

    private final ShiftService service;

    public ShiftController(ShiftService service) {
        this.service = service;
    }

    @GetMapping
    public List<ShiftResource> all() {
        return service.findAll().stream().map(ShiftResource::from).toList();
    }

    @GetMapping("/employee/{employeeId}")
    public List<ShiftResource> byEmployee(@PathVariable("employeeId") Long employeeId) {
        return service.findByEmployee(employeeId).stream().map(ShiftResource::from).toList();
    }

    @GetMapping("/bus-unit/{busUnitId}/active")
    public ShiftResource activeByBusUnit(@PathVariable("busUnitId") Long busUnitId) {
        return ShiftResource.from(service.findActiveByBusUnit(busUnitId));
    }

    /** Inicio de servicio (US02, US11). */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShiftResource start(@Valid @RequestBody ShiftStartRequest request) {
        return ShiftResource.from(service.start(
                request.employeeId(), request.busUnitId(), request.routeOrigin(), request.routeDestination()));
    }

    /** Fin de servicio (US25, US31). */
    @PatchMapping("/{id}/end")
    public ShiftResource end(@PathVariable("id") Long id, @Valid @RequestBody ShiftEndRequest request) {
        return ShiftResource.from(service.end(
                id, request.distanceKm(), request.durationSeconds(), request.passengerCount(), request.fareCollected()));
    }
}