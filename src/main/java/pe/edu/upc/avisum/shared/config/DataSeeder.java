package pe.edu.upc.avisum.shared.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pe.edu.upc.avisum.fleet.application.BusUnitService;
import pe.edu.upc.avisum.fleet.domain.model.BusUnitStatus;
import pe.edu.upc.avisum.identity.application.EmployeeService;
import pe.edu.upc.avisum.identity.domain.model.EmployeeStatus;
import pe.edu.upc.avisum.identity.domain.model.Shift;
import pe.edu.upc.avisum.identity.infrastructure.persistence.ShiftRepository;

import java.time.Instant;
import java.util.List;

/** Datos iniciales de la demostración. Solo se cargan si las tablas están vacías. */
@Component
public class DataSeeder implements CommandLineRunner {

    private static final String ORIGIN = "Terminal Norte";
    private static final String DESTINATION = "Estación Central";

    private final EmployeeService employees;
    private final BusUnitService units;
    private final ShiftRepository shifts;

    public DataSeeder(EmployeeService employees, BusUnitService units, ShiftRepository shifts) {
        this.employees = employees;
        this.units = units;
        this.shifts = shifts;
    }

    @Override
    public void run(String... args) {
        if (units.count() == 0) {
            units.create("ABC-1234", "R-42", -12.0464, -77.0428, BusUnitStatus.ACTIVE);
            units.create("DEF-5678", "R-15", -12.0600, -77.0300, BusUnitStatus.ACTIVE);
            units.create("GHI-9012", "R-07", -12.0700, -77.0500, BusUnitStatus.ACTIVE);
            units.create("JKL-3456", "R-22", -12.0900, -77.0600, BusUnitStatus.ACTIVE);
            units.create("MNO-7890", "R-33", -12.0300, -77.0100, BusUnitStatus.ACTIVE);
        }

        // Se crean en orden: reciben EMP-001 a EMP-005
        if (employees.count() == 0) {
            employees.create("Carlos Franco", "Blancas Chavez", "00000001", "ABC-1234", EmployeeStatus.ACTIVE);
            employees.create("Rodrigo Jesus", "Miraval Pomalaya", "00000002", "DEF-5678", EmployeeStatus.ACTIVE);
            employees.create("Waldo Alonso", "Portal Inga", "00000003", "GHI-9012", EmployeeStatus.ACTIVE);
            employees.create("Joaquin Leonardo", "Reyes Muñoz", "00000004", "JKL-3456", EmployeeStatus.ACTIVE);
            employees.create("Edson Diego", "Llamozas Diaz", "00000005", "MNO-7890", EmployeeStatus.ACTIVE);
        }

        if (shifts.count() == 0) {
            shifts.saveAll(List.of(
                    Shift.historical(1L, 1L, "R-42", ORIGIN, DESTINATION, 14.2, 12300, 96, 182.5,
                            Instant.parse("2026-10-02T14:00:00Z"), Instant.parse("2026-10-02T17:25:00Z")),
                    Shift.historical(2L, 2L, "R-15", ORIGIN, DESTINATION, 11.8, 10800, 74, 139.0,
                            Instant.parse("2026-10-02T16:00:00Z"), Instant.parse("2026-10-02T19:00:00Z")),
                    Shift.historical(3L, 3L, "R-07", ORIGIN, DESTINATION, 9.6, 9000, 58, 104.5,
                            Instant.parse("2026-10-03T13:00:00Z"), Instant.parse("2026-10-03T15:30:00Z")),
                    Shift.historical(4L, 4L, "R-22", ORIGIN, DESTINATION, 15.3, 13200, 102, 198.0,
                            Instant.parse("2026-10-03T15:00:00Z"), Instant.parse("2026-10-03T18:40:00Z"))
            ));
        }
    }
}