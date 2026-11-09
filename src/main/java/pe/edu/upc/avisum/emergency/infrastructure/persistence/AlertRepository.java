package pe.edu.upc.avisum.emergency.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.avisum.emergency.domain.model.Alert;

public interface AlertRepository extends JpaRepository<Alert, Long> {
}