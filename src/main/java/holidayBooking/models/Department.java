package holidayBooking.models;

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
@Table(name = "departments")
public class Department implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "department")
  private List<Employee> employees;
  
  public Long getId() {
    return this.id;
  }
  public String getTitle() {
    return this.title;
  }
  public List<Employee> getEmployees() {
    return this.employees;
  }

  public void setId(Long id) {
    this.id = id;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public void setEmployees(List<Employee> employees) {
    this.employees = employees;
  }

  /* Helpers */
  public String toString() {
    return this.title;
  }
  public List<HolidayRequest> getAllRequests(){
		 List<Employee> e = this.getEmployees();
		 List<HolidayRequest> hr = new ArrayList<HolidayRequest>();
		 for(int i=0; i<e.size();i++) {		
			 hr.addAll(e.get(i).getHolidayRequests());
		 }
		 return hr;
	  }
  public List<HolidayRequest> getAllRequests(LocalDateTime start, LocalDateTime end){
		 List<Employee> e = this.getEmployees();
		 List<HolidayRequest> hr = new ArrayList<HolidayRequest>();
		 for(int i=0; i<e.size();i++) {		
			 hr.addAll(e.get(i).getHoliday(start, end));
		 }
		 return hr;
	  }
  
  public List<HolidayRequest> getAprrovedRequests(){
	 List<Employee> e = this.getEmployees();
	 List<HolidayRequest> hr = new ArrayList<HolidayRequest>();
	 for(int i=0; i<e.size();i++) {		
		 hr.addAll(e.get(i).getHolidayBookings());
	 }
	 return hr;
  }
  public List<HolidayRequest> getAprrovedRequests(LocalDateTime start, LocalDateTime end){
		 List<Employee> e = this.getEmployees();
		 List<HolidayRequest> hr = new ArrayList<HolidayRequest>();
		 for(int i=0; i<e.size();i++) {		
			 hr.addAll(e.get(i).getApprovedHoliday(start ,end));
		 }
		 return hr;
	  }
  
  public List<HolidayRequest> getRoleRequests(long id, LocalDateTime start, LocalDateTime end){
		 List<Employee> e = this.getEmployees();
		 List<HolidayRequest> hr = new ArrayList<HolidayRequest>();
		 for(int i=0; i<e.size();i++) {
			 hr.addAll(e.get(i).getRoleBookings(id,start, end));
		 }
		 return hr;
	  }
  public List<Employee> getRoleSpecific(long id){
	  List<Employee> Temp = new ArrayList<Employee>();
			  
	  for (Employee e : this.getEmployees()) {    		
    			if(!Temp.contains(e)) {
	    			if(e.getRole().getId()==id) {
	    				Temp.add(e);
	    			}
    			}
    	}
	  return Temp;
  }
  
  public List<HolidayRequest> getSpecificRequests(LocalDateTime start, LocalDateTime end){
		 List<Employee> e = this.getEmployees();
		 List<HolidayRequest> hr = new ArrayList<HolidayRequest>();
		 for(int i=0; i<e.size();i++) {		
			 hr.addAll(e.get(i).getHoliday(start, end));
		 }
		 return hr;
	  }
  
}
