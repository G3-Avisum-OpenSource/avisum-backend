package pe.edu.upc.avisum.identity.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/** Turno de un conductor en una unidad (Aggregate Root Shift). */
@Entity
@Table(name = "shifts")
public class Shift {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Column(name = "bus_unit_id", nullable = false)
    private Long busUnitId;

    @Column(name = "route_name", length = 10)
    private String routeName;

    @Column(name = "route_origin", length = 80)
    private String routeOrigin;

    @Column(name = "route_destination", length = 80)
    private String routeDestination;

    @Column(name = "distance_km", nullable = false)
    private Double distanceKm = 0.0;

    @Column(name = "duration_seconds", nullable = false)
    private Integer durationSeconds = 0;

    @Column(name = "passenger_count", nullable = false)
    private Integer passengerCount = 0;

    @Column(name = "fare_collected", nullable = false)
    private Double fareCollected = 0.0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private ShiftStatus status = ShiftStatus.ACTIVE;

    @Column(name = "started_at", nullable = false)
    private Instant startedAt;

    @Column(name = "ended_at")
    private Instant endedAt;

    protected Shift() {
    }

    /** Inicia un turno nuevo: cifras en cero y estado activo. */
    public static Shift start(Long employeeId, Long busUnitId, String routeName, String origin, String destination) {
        Shift shift = new Shift();
        shift.employeeId = employeeId;
        shift.busUnitId = busUnitId;
        shift.routeName = routeName;
        shift.routeOrigin = origin;
        shift.routeDestination = destination;
        shift.startedAt = now();
        return shift;
    }

    /** Turno ya finalizado, para los datos de ejemplo. */
    public static Shift historical(Long employeeId, Long busUnitId, String routeName, String origin,
                                   String destination, double distanceKm, int durationSeconds,
                                   int passengerCount, double fareCollected, Instant startedAt, Instant endedAt) {
        Shift shift = start(employeeId, busUnitId, routeName, origin, destination);
        shift.distanceKm = distanceKm;
        shift.durationSeconds = durationSeconds;
        shift.passengerCount = passengerCount;
        shift.fareCollected = fareCollected;
        shift.status = ShiftStatus.FINISHED;
        shift.startedAt = startedAt;
        shift.endedAt = endedAt;
        return shift;
    }

    /** Cierra el turno con sus cifras finales (los valores nulos conservan lo anterior). */
    public void finish(Double distanceKm, Integer durationSeconds, Integer passengerCount, Double fareCollected) {
        if (distanceKm != null) this.distanceKm = distanceKm;
        if (durationSeconds != null) this.durationSeconds = durationSeconds;
        if (passengerCount != null) this.passengerCount = passengerCount;
        if (fareCollected != null) this.fareCollected = fareCollected;
        this.status = ShiftStatus.FINISHED;
        this.endedAt = now();
    }

    /** Cierre automático de un turno que quedó abierto (por ejemplo, al cerrar el navegador sin finalizar). */
    public void closeAutomatically() {
        this.status = ShiftStatus.FINISHED;
        this.endedAt = now();
    }

    private static Instant now() {
        return Instant.now().truncatedTo(ChronoUnit.MILLIS);
    }

    public Long getId() { return id; }
    public Long getEmployeeId() { return employeeId; }
    public Long getBusUnitId() { return busUnitId; }
    public String getRouteName() { return routeName; }
    public String getRouteOrigin() { return routeOrigin; }
    public String getRouteDestination() { return routeDestination; }
    public Double getDistanceKm() { return distanceKm; }
    public Integer getDurationSeconds() { return durationSeconds; }
    public Integer getPassengerCount() { return passengerCount; }
    public Double getFareCollected() { return fareCollected; }
    public ShiftStatus getStatus() { return status; }
    public Instant getStartedAt() { return startedAt; }
    public Instant getEndedAt() { return endedAt; }
}