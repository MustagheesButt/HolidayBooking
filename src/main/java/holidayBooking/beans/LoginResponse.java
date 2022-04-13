package holidayBooking.beans;

import holidayBooking.models.Admin;
import holidayBooking.models.Employee;

public class LoginResponse {
  private Employee employee;
  private Admin admin;

  public LoginResponse() {
  }

  public LoginResponse(Employee employee, Admin admin) {
    this.employee = employee;
    this.admin = admin;
  }

  public Employee getEmployee() {
    return this.employee;
  }

  public void setEmployee(Employee e) {
    this.employee = e;
  }

  public Admin getAdmin() {
    return this.admin;
  }

  public void setAdmin(Admin a) {
    this.admin = a;
  }
}
