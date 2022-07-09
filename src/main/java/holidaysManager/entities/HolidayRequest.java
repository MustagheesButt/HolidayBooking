package holidaysManager.entities;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import holidaysManager.helpers.ConstraintBean;

@Entity
@Table
public class HolidayRequest implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  private LocalDateTime dateStart;

  private LocalDateTime dateEnd;

  @JsonbTransient
  @ManyToOne
  @JoinColumn(name = "employee_id")
  private Employee employee;

  // Can be one of the following values ['pending', 'approved', 'rejected']
  private String status;
  
  public Long getId() {
    return this.id;
  }
  public String getTitle() {
    return this.title;
  }
  public LocalDateTime getDateStart() {
    return this.dateStart;
  }
  public LocalDateTime getDateEnd() {
    return this.dateEnd;
  }
  public long getDuration() {
    return Duration.between(this.dateStart, this.dateEnd).toDays();
  }
  public Employee getEmployee() {
    return this.employee;
  }
  public String getStatus() {
    return this.status;
  }

  public void setId(Long id) {
    this.id = id;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public void setDateStart(LocalDateTime dateStart) {
    this.dateStart = dateStart;
  }
  public void setDateEnd(LocalDateTime dateEnd) {
    this.dateEnd = dateEnd;
  }
  public void setEmployee(Employee employee) {
    this.employee = employee;
  }
  public void setStatus(String status) {
    this.status = status;
  }

  /* Helpers */
  public String toString() {
    return this.title + ":" + this.status;
  }

  @JsonbTransient
  public List<String> getViolations() {
    return ConstraintBean.brokenContraints(this);
  }

  // number of days this request has in common with peak time periods
  public Long getDaysDuringPeakTime() {
    Long counter = 0L;

    for (LocalDateTime currDate = this.getDateStart(); currDate.compareTo(this.getDateEnd()) == 0; currDate.plusDays(1)) {
      if (ConstraintBean.isPeakTime(currDate)) {
        counter++;
      }
    }

    return counter;
  }
}
