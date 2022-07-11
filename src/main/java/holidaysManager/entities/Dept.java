package holidaysManager.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class Dept implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "dept")
	private List<Emp> employees;

	public Long getId() {
		return this.id;
	}

	public String getTitle() {
		return this.title;
	}

	public List<Emp> getEmployees() {
		return this.employees;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setEmployees(List<Emp> employees) {
		this.employees = employees;
	}

	/* Helpers */
	public String toString() {
		return this.title;
	}

	public List<HRequest> getAllRequests() {
		List<Emp> e = this.getEmployees();
		List<HRequest> hr = new ArrayList<HRequest>();
		for (int i = 0; i < e.size(); i++) {
			hr.addAll(e.get(i).getHolidayRequests());
		}
		return hr;
	}

	public List<HRequest> getAllRequests(LocalDateTime start, LocalDateTime end) {
		List<Emp> e = this.getEmployees();
		List<HRequest> hr = new ArrayList<HRequest>();
		for (int i = 0; i < e.size(); i++) {
			hr.addAll(e.get(i).getHoliday(start, end));
		}
		return hr;
	}

	public List<HRequest> getAprrovedRequests() {
		List<Emp> e = this.getEmployees();
		List<HRequest> hr = new ArrayList<HRequest>();
		for (int i = 0; i < e.size(); i++) {
			hr.addAll(e.get(i).getHolidayBookings());
		}
		return hr;
	}

	public List<HRequest> getAprrovedRequests(LocalDateTime start, LocalDateTime end) {
		List<Emp> e = this.getEmployees();
		List<HRequest> hr = new ArrayList<HRequest>();
		for (int i = 0; i < e.size(); i++) {
			hr.addAll(e.get(i).getApprovedHoliday(start, end));
		}
		return hr;
	}

	public List<HRequest> getRoleRequests(long id, LocalDateTime start, LocalDateTime end) {
		List<Emp> e = this.getEmployees();
		List<HRequest> hr = new ArrayList<HRequest>();
		for (int i = 0; i < e.size(); i++) {
			hr.addAll(e.get(i).getRoleBookings(id, start, end));
		}
		return hr;
	}

	public List<Emp> getRoleSpecific(long id) {
		List<Emp> Temp = new ArrayList<Emp>();

		for (Emp e : this.getEmployees()) {
			if (!Temp.contains(e)) {
				if (e.getRole().getId() == id) {
					Temp.add(e);
				}
			}
		}
		return Temp;
	}

	public List<HRequest> getSpecificRequests(LocalDateTime start, LocalDateTime end) {
		List<Emp> e = this.getEmployees();
		List<HRequest> hr = new ArrayList<HRequest>();
		for (int i = 0; i < e.size(); i++) {
			hr.addAll(e.get(i).getHoliday(start, end));
		}
		return hr;
	}

}
