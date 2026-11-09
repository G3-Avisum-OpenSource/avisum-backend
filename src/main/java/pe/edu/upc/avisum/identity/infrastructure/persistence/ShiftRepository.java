package pe.edu.upc.avisum.identity.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.avisum.identity.domain.model.Shift;
import pe.edu.upc.avisum.identity.domain.model.ShiftStatus;

import java.util.List;
import java.util.Optional;

public interface ShiftRepository extends JpaRepository<Shift, Long> {

    List<Shift> findByEmployeeIdOrderByIdAsc(Long employeeId);

    List<Shift> findByEmployeeIdAndStatus(Long employeeId, ShiftStatus status);

    boolean existsByEmployeeIdAndStatus(Long employeeId, ShiftStatus status);

    Optional<Shift> findFirstByBusUnitIdAndStatusOrderByIdDesc(Long busUnitId, ShiftStatus status);
}