package holidaysManager.entities;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import javax.persistence.UniqueConstraint;
import javax.json.bind.annotation.JsonbTransient;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "email" }) })
public class Emp implements Serializable {
  static final int HOLIDAYS_PER_YEAR = 30;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String firstName;

  private String lastName;

  @Column(name = "email")
  private String email;

  private String password;

  @OneToOne
  private Role role;

  @JsonbTransient
  @ManyToOne
  @JoinColumn(name = "department_id")
  private Dept dept;

  private LocalDateTime joiningDate;

  @JsonbTransient
  @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, mappedBy = "emp")
  private List<HRequest> holidayRequests;

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

  public Dept getDept() {
    return this.dept;
  }

  public LocalDateTime getJoiningDate() {
    return this.joiningDate;
  }

  public List<HRequest> getHolidayRequests() {
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

  public void setDepartment(Dept department) {
    this.dept = department;
  }

  public void setJoiningDate(LocalDateTime joiningDate) {
    this.joiningDate = joiningDate;
  }

  public void setHolidayRequests(List<HRequest> holidayRequests) {
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

  public boolean isManager() {
    return this.getRole().getId() == 4;
  }

  public boolean isSeniorStaff() {
    return this.getRole().getId() == 5;
  }

  public boolean isHeadOfDept() {
    return this.getRole().getId() == 1;
  }

  public boolean isDeputyHeadOfDept() {
    return this.getRole().getId() == 2;
  }

  public List<HRequest> getApprovedReqs() {
    return this.getHolidayRequests()
        .stream()
        .filter(hr -> hr.getStatus().equals("approved"))
        .collect(Collectors.toList());
  }

  public Long getApprovedReqsDayCount() {
    return this.getApprovedReqs()
        .stream()
        .reduce(0L, (sum, hr) -> {
          return sum + Duration.between(hr.getDateStart(), hr.getDateEnd()).toDays();
        }, Long::sum);
  }

  // get approved requests for a specific role, between a specific datetime period
  public List<HRequest> getRoleBookings(Long id, LocalDateTime start, LocalDateTime end) {
    return this.getHolidayRequests()
        .stream()
        .filter(hr -> hr.getStatus().equals("approved"))
        .filter(hr -> hr.getEmp().getRole().getId().equals(id))
        .filter(hr -> ((hr.getDateStart().compareTo(start) >= 0 && hr.getDateStart().compareTo(end) < 0)
            || (hr.getDateStart().compareTo(start) <= 0 && hr.getDateEnd().compareTo(start) > 0)))
        .collect(Collectors.toList());
  }

  // get pending requests between a datetime period
  public List<HRequest> getHoliday(LocalDateTime start, LocalDateTime end) {

    return this.getHolidayRequests()
        .stream()
        .filter(hr -> hr.getStatus().equals("pending"))
        .filter(hr -> ((hr.getDateStart().compareTo(start) >= 0 && hr.getDateStart().compareTo(end) < 0)
            || (hr.getDateStart().compareTo(start) <= 0 && hr.getDateEnd().compareTo(start) > 0)))
        .collect(Collectors.toList());
  }

  public List<HRequest> getApprovedHoliday(LocalDateTime start, LocalDateTime end) {

    return this.getHolidayRequests()
        .stream()
        .filter(hr -> hr.getStatus().equals("approved"))
        .filter(hr -> ((hr.getDateStart().compareTo(start) >= 0 && hr.getDateStart().compareTo(end) < 0)
            || (hr.getDateStart().compareTo(start) <= 0 && hr.getDateEnd().compareTo(start) > 0)))
        .collect(Collectors.toList());
  }

  public int getRemainingHolidays() {
    Integer bonusHolidays = (int) ChronoUnit.YEARS.between(this.joiningDate, LocalDateTime.now()) / 5;
    Long approvedHolidays = this.getApprovedReqsDayCount();
    return Emp.HOLIDAYS_PER_YEAR - approvedHolidays.intValue() + bonusHolidays;
  }

  // this is used on admin dashboard, where we can filter employees by date and
  // check whether they are on leave or on duty
  public String getHolidayBookingsSerialized() {
    return "[" +
        String.join(",", this.getApprovedReqs()
            .stream()
            .map(hr -> "{\"start\": \"" + hr.getDateStart() + "\", \"end\": \"" + hr.getDateEnd() + "\"}")
            .collect(Collectors.toList()))
        + "]";
  }
}
