package pe.edu.upc.avisum.identity.interfaces.rest;

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
import pe.edu.upc.avisum.identity.application.EmployeeService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping
    public List<EmployeeResource> all() {
        return service.findAll().stream().map(EmployeeResource::from).toList();
    }

    @GetMapping("/{id}")
    public EmployeeResource one(@PathVariable("id") Long id) {
        return EmployeeResource.from(service.get(id));
    }

    /** Validación de identidad del conductor por código (US01, US10, US14). */
    @GetMapping("/code/{code}")
    public EmployeeResource byCode(@PathVariable("code") String code) {
        return EmployeeResource.from(service.validateByCode(code));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResource create(@Valid @RequestBody EmployeeRequest request) {
        return EmployeeResource.from(service.create(
                request.firstName(), request.lastName(), request.dni(), request.plateNumber(), request.status()));
    }

    @PatchMapping("/{id}")
    public EmployeeResource update(@PathVariable("id") Long id, @Valid @RequestBody EmployeePatchRequest request) {
        return EmployeeResource.from(service.update(
                id, request.firstName(), request.lastName(), request.dni(), request.plateNumber(), request.status()));
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return Map.of();
    }
}