package holidayBooking.models;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "employees")
public class Employee implements Serializable {
  static final int HOLIDAYS_PER_YEAR = 30;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String firstName;

  private String lastName;

  private String email;

  private String password;

  @OneToOne
  private Role role;

  @ManyToOne
  @JoinColumn(name = "department_id")
  private Department department;

  private LocalDateTime joiningDate;

  @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, mappedBy = "employee")
  private List<HolidayRequest> holidayRequests;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  public Long getId() {
    return this.id;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public String getEmail() {
    return this.email;
  }

  public String getPassword() {
    return this.password;
  }

  public Role getRole() {
    return this.role;
  }

  public Department getDepartment() {
    return this.department;
  }

  public LocalDateTime getJoiningDate() {
    return this.joiningDate;
  }

  public List<HolidayRequest> getHolidayRequests() {
    return this.holidayRequests;
  }

  public LocalDateTime getCreatedAt() {
    return this.createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return this.updatedAt;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }

  public void setJoiningDate(LocalDateTime joiningDate) {
    this.joiningDate = joiningDate;
  }

  public void setHolidayRequests(List<HolidayRequest> holidayRequests) {
    this.holidayRequests = holidayRequests;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  /* Other helper methods */
  public String getFullName() {
    return this.firstName + " " + this.lastName;
  }

  public List<HolidayRequest> getHolidayBookings() {
    return this.getHolidayRequests()
        .stream()
        .filter(hr -> hr.getStatus().equals("approved"))
        .collect(Collectors.toList());
  }

  public int getRemainingHolidays() {
    Long approvedHolidays = this.getHolidayBookings()
        .stream()
        .reduce(0L, (sum, hr) -> {
            // System.out.println(Duration.between(hr.getDateStart(), hr.getDateEnd()).toDays());
            return sum + Duration.between(hr.getDateStart(), hr.getDateEnd()).toDays();
          }, Long::sum);
    return Employee.HOLIDAYS_PER_YEAR - approvedHolidays.intValue();
  }

  public String getHolidayBookingsSerialized() {
    return "[" +
        String.join(",", this.getHolidayBookings()
            .stream()
            // .map(hr -> "{\\\"start\\\": \\\"" + hr.getDateStart() + "\\\", \\\"end\\\":
            // \\\"" + hr.getDateEnd() + "\\\"}")
            .map(hr -> "{\"start\": \"" + hr.getDateStart() + "\", \"end\": \"" + hr.getDateEnd() + "\"}")
            .collect(Collectors.toList()))
        + "]";
  }
}
