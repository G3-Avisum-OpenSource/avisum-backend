package pe.edu.upc.avisum.emergency.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/** Alerta de emergencia (PanicAlert del diagrama de clases). La ubicación va embebida. */
@Entity
@Table(name = "alerts")
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Column(name = "bus_unit_id", nullable = false)
    private Long busUnitId;

    @Column(name = "alert_type", nullable = false, length = 30)
    private String alertType;

    @Column(length = 255)
    private String description;

    private Double latitude;

    private Double longitude;

    @Column(nullable = false)
    private boolean resolved = false;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    /** Permite calcular el tiempo de respuesta (US34). */
    @Column(name = "resolved_at")
    private Instant resolvedAt;

    protected Alert() {
    }

    public static Alert create(Long employeeId, Long busUnitId, String alertType, String description,
                               Double latitude, Double longitude) {
        Alert alert = new Alert();
        alert.employeeId = employeeId;
        alert.busUnitId = busUnitId;
        alert.alertType = alertType;
        alert.description = description;
        alert.latitude = latitude;
        alert.longitude = longitude;
        alert.createdAt = Instant.now().truncatedTo(ChronoUnit.MILLIS);
        return alert;
    }

    public void resolve() {
        if (!resolved) {
            this.resolved = true;
            this.resolvedAt = Instant.now().truncatedTo(ChronoUnit.MILLIS);
        }
    }

    public Long getId() { return id; }
    public Long getEmployeeId() { return employeeId; }
    public Long getBusUnitId() { return busUnitId; }
    public String getAlertType() { return alertType; }
    public String getDescription() { return description; }
    public Double getLatitude() { return latitude; }
    public Double getLongitude() { return longitude; }
    public boolean isResolved() { return resolved; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getResolvedAt() { return resolvedAt; }
}