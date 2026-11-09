package pe.edu.upc.avisum.fleet.interfaces.rest;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upc.avisum.fleet.application.BusUnitService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/bus-units")
public class BusUnitController {

    private final BusUnitService service;

    public BusUnitController(BusUnitService service) {
        this.service = service;
    }

    @GetMapping
    public List<BusUnitResource> all() {
        return service.findAll().stream().map(BusUnitResource::from).toList();
    }

    @GetMapping("/{id}")
    public BusUnitResource one(@PathVariable("id") Long id) {
        return BusUnitResource.from(service.get(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BusUnitResource create(@Valid @RequestBody BusUnitRequest request) {
        return BusUnitResource.from(service.create(
                request.plateNumber(), request.route(),
                request.currentLatitude(), request.currentLongitude(), request.status()));
    }

    @PatchMapping("/{id}")
    public BusUnitResource update(@PathVariable("id") Long id, @RequestBody BusUnitPatchRequest request) {
        return BusUnitResource.from(service.update(id, request.plateNumber(), request.route(), request.status()));
    }

    /** Ubicación en vivo de la unidad (la envía el simulador de la flota del frontend). */
    @PatchMapping("/{id}/location")
    public BusUnitResource updateLocation(@PathVariable("id") Long id, @Valid @RequestBody LocationRequest request) {
        return BusUnitResource.from(service.updateLocation(id, request.latitude(), request.longitude()));
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return Map.of();
    }
}