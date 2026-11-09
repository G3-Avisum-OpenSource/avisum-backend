package pe.edu.upc.avisum.fleet.application;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.avisum.fleet.domain.model.BusUnit;
import pe.edu.upc.avisum.fleet.domain.model.BusUnitStatus;
import pe.edu.upc.avisum.fleet.infrastructure.persistence.BusUnitRepository;
import pe.edu.upc.avisum.shared.exception.ConflictException;
import pe.edu.upc.avisum.shared.exception.NotFoundException;

import java.util.List;

@Service
public class BusUnitService {

    private static final double DEFAULT_LATITUDE = -12.0464;
    private static final double DEFAULT_LONGITUDE = -77.0428;

    private final BusUnitRepository repository;

    public BusUnitService(BusUnitRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<BusUnit> findAll() {
        return repository.findAll(Sort.by("id"));
    }

    @Transactional(readOnly = true)
    public BusUnit get(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Unidad no encontrada"));
    }

    @Transactional(readOnly = true)
    public long count() {
        return repository.count();
    }

    @Transactional
    public BusUnit create(String plateNumber, String route, Double latitude, Double longitude, BusUnitStatus status) {
        String plate = plateNumber.trim().toUpperCase();
        if (repository.existsByPlateNumber(plate)) {
            throw new ConflictException("Ya existe una unidad con esta placa");
        }
        BusUnit unit = new BusUnit(
                plate,
                route.trim(),
                latitude != null ? latitude : DEFAULT_LATITUDE,
                longitude != null ? longitude : DEFAULT_LONGITUDE);
        if (status != null) {
            unit.setStatus(status);
        }
        return repository.save(unit);
    }

    @Transactional
    public BusUnit update(Long id, String plateNumber, String route, BusUnitStatus status) {
        BusUnit unit = get(id);
        if (plateNumber != null) {
            String plate = plateNumber.trim().toUpperCase();
            if (repository.existsByPlateNumberAndIdNot(plate, id)) {
                throw new ConflictException("Ya existe una unidad con esta placa");
            }
            unit.setPlateNumber(plate);
        }
        if (route != null) {
            unit.setRoute(route.trim());
        }
        if (status != null) {
            unit.setStatus(status);
        }
        return unit;
    }

    @Transactional
    public BusUnit updateLocation(Long id, Double latitude, Double longitude) {
        BusUnit unit = get(id);
        unit.moveTo(latitude, longitude);
        return unit;
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(get(id));
    }
}