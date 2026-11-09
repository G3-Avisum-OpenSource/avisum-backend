package pe.edu.upc.avisum.emergency.interfaces.rest;

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
import pe.edu.upc.avisum.emergency.application.AlertService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/alerts")
public class AlertController {

    private final AlertService service;

    public AlertController(AlertService service) {
        this.service = service;
    }

    @GetMapping
    public List<AlertResource> all() {
        return service.findAll().stream().map(AlertResource::from).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlertResource create(@Valid @RequestBody AlertRequest request) {
        return AlertResource.from(service.create(
                request.employeeId(), request.busUnitId(), request.alertType(),
                request.description(), request.latitude(), request.longitude()));
    }

    @PatchMapping("/{id}/resolve")
    public AlertResource resolve(@PathVariable("id") Long id) {
        return AlertResource.from(service.resolve(id));
    }
}