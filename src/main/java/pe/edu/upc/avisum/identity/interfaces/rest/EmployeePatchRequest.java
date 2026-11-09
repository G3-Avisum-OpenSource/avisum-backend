package pe.edu.upc.avisum.identity.interfaces.rest;

import jakarta.validation.constraints.Pattern;
import pe.edu.upc.avisum.identity.domain.model.EmployeeStatus;

/** Actualización parcial: solo se cambian los campos enviados. */
public record EmployeePatchRequest(
        String firstName,
        String lastName,
        String fullName,
        @Pattern(regexp = "\\d{8}", message = "El DNI debe tener 8 dígitos") String dni,
        String plateNumber,
        EmployeeStatus status) {
}