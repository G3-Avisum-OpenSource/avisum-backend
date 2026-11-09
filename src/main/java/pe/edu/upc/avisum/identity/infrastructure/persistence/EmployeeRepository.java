package pe.edu.upc.avisum.identity.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.avisum.identity.domain.model.Employee;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmployeeCode(String employeeCode);

    boolean existsByDni(String dni);

    boolean existsByDniAndIdNot(String dni, Long id);
}