package pe.edu.upc.avisum.identity.application;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.avisum.identity.domain.model.Employee;
import pe.edu.upc.avisum.identity.domain.model.EmployeeStatus;
import pe.edu.upc.avisum.identity.infrastructure.persistence.EmployeeRepository;
import pe.edu.upc.avisum.shared.exception.ConflictException;
import pe.edu.upc.avisum.shared.exception.ForbiddenException;
import pe.edu.upc.avisum.shared.exception.NotFoundException;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Employee> findAll() {
        return repository.findAll(Sort.by("id"));
    }

    @Transactional(readOnly = true)
    public Employee get(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Empleado no encontrado"));
    }

    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }

    /**
     * Valida la identidad del conductor por su código (US01, US14).
     * Acepta minúsculas, espacios y el prefijo "QR-". 404 si no existe, 403 si está desactivado.
     */
    @Transactional(readOnly = true)
    public Employee validateByCode(String rawCode) {
        String code = rawCode.trim().toUpperCase().replaceFirst("^QR-", "");
        Employee employee = repository.findByEmployeeCode(code)
                .orElseThrow(() -> new NotFoundException("Empleado no encontrado"));
        if (employee.getStatus() != EmployeeStatus.ACTIVE) {
            throw new ForbiddenException("Empleado desactivado");
        }
        return employee;
    }

    /** Alta de conductor: el servidor asigna el código EMP-### a partir del id, sin reutilizar los de eliminados. */
    @Transactional
    public Employee create(String firstName, String lastName, String dni, String plateNumber, EmployeeStatus status) {
        if (repository.existsByDni(dni)) {
            throw new ConflictException("Ya existe un conductor con este DNI");
        }
        Employee employee = repository.save(
                new Employee(firstName.trim(), lastName.trim(), dni, plateNumber.trim().toUpperCase()));
        if (status != null) {
            employee.setStatus(status);
        }
        employee.setEmployeeCode(String.format("EMP-%03d", employee.getId()));
        return repository.save(employee);
    }

    @Transactional
    public Employee update(Long id, String firstName, String lastName, String dni,
                           String plateNumber, EmployeeStatus status) {
        Employee employee = get(id);

        if (firstName != null || lastName != null) {
            employee.updateName(
                    firstName != null ? firstName.trim() : employee.getFirstName(),
                    lastName != null ? lastName.trim() : employee.getLastName());
        }
        if (dni != null && !dni.equals(employee.getDni())) {
            if (repository.existsByDniAndIdNot(dni, id)) {
                throw new ConflictException("Ya existe un conductor con este DNI");
            }
            employee.setDni(dni);
        }
        if (plateNumber != null) {
            employee.setPlateNumber(plateNumber.trim().toUpperCase());
        }
        if (status != null) {
            employee.setStatus(status);
        }
        return employee;
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(get(id));
    }
}