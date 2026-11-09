package pe.edu.upc.avisum.identity.interfaces.rest;

import pe.edu.upc.avisum.identity.domain.model.Employee;
import pe.edu.upc.avisum.identity.domain.model.EmployeeStatus;

public record EmployeeResource(
        Long id,
        String employeeCode,
        String firstName,
        String lastName,
        String fullName,
        String dni,
        String plateNumber,
        EmployeeStatus status) {

    public static EmployeeResource from(Employee e) {
        return new EmployeeResource(
                e.getId(), e.getEmployeeCode(), e.getFirstName(), e.getLastName(),
                e.getFullName(), e.getDni(), e.getPlateNumber(), e.getStatus());
    }
}