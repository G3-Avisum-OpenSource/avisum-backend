package pe.edu.upc.avisum.emergency.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.avisum.emergency.domain.model.Alert;
import pe.edu.upc.avisum.emergency.infrastructure.persistence.AlertRepository;
import pe.edu.upc.avisum.fleet.application.BusUnitService;
import pe.edu.upc.avisum.identity.application.EmployeeService;
import pe.edu.upc.avisum.identity.application.ShiftService;
import pe.edu.upc.avisum.shared.exception.ConflictException;
import pe.edu.upc.avisum.shared.exception.NotFoundException;

import java.util.List;

@Service
public class AlertService {

    private final AlertRepository repository;
    private final EmployeeService employees;
    private final BusUnitService units;
    private final ShiftService shifts;
    private final boolean requireActiveShift;

    public AlertService(AlertRepository repository, EmployeeService employees, BusUnitService units,
                        ShiftService shifts,
                        @Value("${avisum.rules.alert-requires-active-shift:true}") boolean requireActiveShift) {
        this.repository = repository;
        this.employees = employees;
        this.units = units;
        this.shifts = shifts;
        this.requireActiveShift = requireActiveShift;
    }

    @Transactional(readOnly = true)
    public List<Alert> findAll() {
        return repository.findAll(Sort.by("id"));
    }

    /** Registra una alerta (US03, US05, US12, US42). Exige conductor y unidad existentes y un turno activo. */
    @Transactional
    public Alert create(Long employeeId, Long busUnitId, String alertType, String description,
                        Double latitude, Double longitude) {
        employees.get(employeeId);
        units.get(busUnitId);
        if (requireActiveShift && !shifts.hasActiveShift(employeeId)) {
            throw new ConflictException("El conductor no tiene un turno activo");
        }
        return repository.save(Alert.create(employeeId, busUnitId, alertType, description, latitude, longitude));
    }

    /** Marca la alerta como resuelta y registra la hora (US34, US54, US78). */
    @Transactional
    public Alert resolve(Long id) {
        Alert alert = repository.findById(id).orElseThrow(() -> new NotFoundException("Alerta no encontrada"));
        alert.resolve();
        return alert;
    }
}