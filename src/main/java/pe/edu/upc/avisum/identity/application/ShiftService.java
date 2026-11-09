package pe.edu.upc.avisum.identity.application;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.avisum.fleet.application.BusUnitService;
import pe.edu.upc.avisum.fleet.domain.model.BusUnit;
import pe.edu.upc.avisum.identity.domain.model.Employee;
import pe.edu.upc.avisum.identity.domain.model.EmployeeStatus;
import pe.edu.upc.avisum.identity.domain.model.Shift;
import pe.edu.upc.avisum.identity.domain.model.ShiftStatus;
import pe.edu.upc.avisum.identity.infrastructure.persistence.ShiftRepository;
import pe.edu.upc.avisum.shared.exception.ForbiddenException;
import pe.edu.upc.avisum.shared.exception.NotFoundException;

import java.util.List;

@Service
public class ShiftService {

    private final ShiftRepository repository;
    private final EmployeeService employees;
    private final BusUnitService units;

    public ShiftService(ShiftRepository repository, EmployeeService employees, BusUnitService units) {
        this.repository = repository;
        this.employees = employees;
        this.units = units;
    }

    @Transactional(readOnly = true)
    public List<Shift> findAll() {
        return repository.findAll(Sort.by("id"));
    }

    @Transactional(readOnly = true)
    public List<Shift> findByEmployee(Long employeeId) {
        return repository.findByEmployeeIdOrderByIdAsc(employeeId);
    }

    @Transactional(readOnly = true)
    public Shift findActiveByBusUnit(Long busUnitId) {
        return repository.findFirstByBusUnitIdAndStatusOrderByIdDesc(busUnitId, ShiftStatus.ACTIVE)
                .orElseThrow(() -> new NotFoundException("Sin turno activo"));
    }

    @Transactional(readOnly = true)
    public boolean hasActiveShift(Long employeeId) {
        return repository.existsByEmployeeIdAndStatus(employeeId, ShiftStatus.ACTIVE);
    }

    /**
     * Inicia el turno de un conductor autorizado (US02).
     * US39: un conductor no puede tener dos turnos activos; si había uno abierto, se cierra antes.
     */
    @Transactional
    public Shift start(Long employeeId, Long busUnitId, String origin, String destination) {
        Employee employee = employees.get(employeeId);
        if (employee.getStatus() != EmployeeStatus.ACTIVE) {
            throw new ForbiddenException("Empleado desactivado");
        }
        BusUnit unit = units.get(busUnitId);

        repository.findByEmployeeIdAndStatus(employeeId, ShiftStatus.ACTIVE)
                .forEach(Shift::closeAutomatically);

        return repository.save(Shift.start(employeeId, busUnitId, unit.getRoute(), origin, destination));
    }

    /** Cierra el turno con sus cifras finales (US25). Si ya estaba finalizado, lo devuelve tal cual. */
    @Transactional
    public Shift end(Long id, Double distanceKm, Integer durationSeconds, Integer passengerCount, Double fareCollected) {
        Shift shift = repository.findById(id).orElseThrow(() -> new NotFoundException("Turno no encontrado"));
        if (shift.getStatus() == ShiftStatus.FINISHED) {
            return shift;
        }
        shift.finish(distanceKm, durationSeconds, passengerCount, fareCollected);
        return shift;
    }
}