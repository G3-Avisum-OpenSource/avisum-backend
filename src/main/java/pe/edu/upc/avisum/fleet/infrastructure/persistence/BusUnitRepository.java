package pe.edu.upc.avisum.fleet.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.avisum.fleet.domain.model.BusUnit;

public interface BusUnitRepository extends JpaRepository<BusUnit, Long> {

    boolean existsByPlateNumber(String plateNumber);

    boolean existsByPlateNumberAndIdNot(String plateNumber, Long id);
}