package pe.edu.upc.avisum.identity.interfaces.rest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import pe.edu.upc.avisum.identity.domain.model.EmployeeStatus;

public record EmployeeRequest(
        @NotBlank String firstName,
        @NotBlank String lastName,
        String fullName,
        @NotBlank @Pattern(regexp = "\\d{8}", message = "El DNI debe tener 8 dígitos") String dni,
        @NotBlank String plateNumber,
        EmployeeStatus status) {
}