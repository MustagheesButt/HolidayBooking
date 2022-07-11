package holidaysManager.entities;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;

import holidaysManager.helpers.ConstraintHelper;

@Entity
@Table
public class HRequest implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String reason;

  private LocalDateTime dateStart;

  private LocalDateTime dateEnd;

  @JsonbTransient
  @ManyToOne
  @JoinColumn(name = "employee_id")
  private Emp emp;

  // Can be one of the following values ['pending', 'approved', 'rejected']
  private String status;
  
  public Long getId() {
    return this.id;
  }
  public String getReason() {
    return this.reason;
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
  public Emp getEmp() {
    return this.emp;
  }
  public String getStatus() {
    return this.status;
  }

  public void setId(Long id) {
    this.id = id;
  }
  public void setReason(String reason) {
    this.reason = reason;
  }
  public void setDateStart(LocalDateTime dateStart) {
    this.dateStart = dateStart;
  }
  public void setDateEnd(LocalDateTime dateEnd) {
    this.dateEnd = dateEnd;
  }
  public void setEmployee(Emp employee) {
    this.emp = employee;
  }
  public void setStatus(String status) {
    this.status = status;
  }

  @JsonbTransient
  public List<String> getViolations() {
    return ConstraintHelper.brokenContraints(this);
  }

  public Long getPeakDaysCount() {
    Long c = 0L;

    for (LocalDateTime currDate = this.getDateStart(); currDate.compareTo(this.getDateEnd()) == 0; currDate.plusDays(1)) {
      if (isPeak(currDate)) {
        c++;
      }
    }

    return c;
  }

	private static boolean isPeak(LocalDateTime d) {
		if (d == null) d = LocalDateTime.now();

		List<LocalDateTime> periods = Arrays.asList(
				LocalDateTime.of(d.getYear(), 7, 15, 0, 1), LocalDateTime.of(d.getYear(), 8, 31, 23, 59),
				LocalDateTime.of(d.getYear(), 12, 15, 0, 1), LocalDateTime.of(d.getYear(), 12, 22, 23, 59),
				getEasterSundayDate(d.getYear()).minusDays(7), getEasterSundayDate(d.getYear()).plusDays(7)
			);

		for (int i = 0; i < periods.size(); i += 2) {
			if (d.compareTo(periods.get(i)) >= 0 && d.compareTo(periods.get(i + 1)) <= 0) {
				return true;
			}
		}

		return false;
	}

	private static LocalDateTime getEasterSundayDate(int year) {
		int a = year % 19,
				b = year / 100,
				c = year % 100,
				d = b / 4,
				e = b % 4,
				g = (8 * b + 13) / 25,
				h = (19 * a + b - d - g + 15) % 30,
				j = c / 4,
				k = c % 4,
				m = (a + 11 * h) / 319,
				r = (2 * e + 2 * j - k - h + m + 32) % 7,
				n = (h - m + r + 90) / 25,
				p = (h - m + r + n + 19) % 32;

		return LocalDateTime.of(year, n, p, 0, 1);
	}
}
