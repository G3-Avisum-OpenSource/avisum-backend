package pe.edu.upc.avisum.fleet.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/** Unidad de transporte monitoreada (TransportUnit del diagrama de clases). */
@Entity
@Table(name = "bus_units")
public class BusUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "plate_number", nullable = false, unique = true, length = 10)
    private String plateNumber;

    @Column(nullable = false, length = 10)
    private String route;

    @Column(name = "current_latitude")
    private Double currentLatitude;

    @Column(name = "current_longitude")
    private Double currentLongitude;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private BusUnitStatus status = BusUnitStatus.ACTIVE;

    protected BusUnit() {
    }

    public BusUnit(String plateNumber, String route, Double currentLatitude, Double currentLongitude) {
        this.plateNumber = plateNumber;
        this.route = route;
        this.currentLatitude = currentLatitude;
        this.currentLongitude = currentLongitude;
    }

    public void moveTo(Double latitude, Double longitude) {
        this.currentLatitude = latitude;
        this.currentLongitude = longitude;
    }

    public Long getId() { return id; }
    public String getPlateNumber() { return plateNumber; }
    public String getRoute() { return route; }
    public Double getCurrentLatitude() { return currentLatitude; }
    public Double getCurrentLongitude() { return currentLongitude; }
    public BusUnitStatus getStatus() { return status; }

    public void setPlateNumber(String plateNumber) { this.plateNumber = plateNumber; }
    public void setRoute(String route) { this.route = route; }
    public void setStatus(BusUnitStatus status) { this.status = status; }
}