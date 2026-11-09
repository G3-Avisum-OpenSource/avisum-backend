package pe.edu.upc.avisum.identity.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/** Conductor (Driver del diagrama de clases). Su unidad se enlaza por la placa. */
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** EMP-001, EMP-002... Se asigna después de insertar, a partir del id. */
    @Column(name = "employee_code", unique = true, length = 20)
    private String employeeCode;

    @Column(name = "first_name", nullable = false, length = 80)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 80)
    private String lastName;

    @Column(name = "full_name", nullable = false, length = 170)
    private String fullName;

    @Column(nullable = false, unique = true, length = 8)
    private String dni;

    /** Sin restricción de unicidad: al reasignar unidades, dos conductores intercambian de placa. */
    @Column(name = "plate_number", nullable = false, length = 10)
    private String plateNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private EmployeeStatus status = EmployeeStatus.ACTIVE;

    protected Employee() {
    }

    public Employee(String firstName, String lastName, String dni, String plateNumber) {
        updateName(firstName, lastName);
        this.dni = dni;
        this.plateNumber = plateNumber;
    }

    public void updateName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = (firstName + " " + lastName).trim();
    }

    public Long getId() { return id; }
    public String getEmployeeCode() { return employeeCode; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getFullName() { return fullName; }
    public String getDni() { return dni; }
    public String getPlateNumber() { return plateNumber; }
    public EmployeeStatus getStatus() { return status; }

    public void setEmployeeCode(String employeeCode) { this.employeeCode = employeeCode; }
    public void setDni(String dni) { this.dni = dni; }
    public void setPlateNumber(String plateNumber) { this.plateNumber = plateNumber; }
    public void setStatus(EmployeeStatus status) { this.status = status; }
}