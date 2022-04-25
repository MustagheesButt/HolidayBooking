package holidayBooking.beans;

import javax.servlet.http.HttpSession;

import holidayBooking.models.Employee;
import holidayBooking.models.Admin;
import holidayBooking.services.AdminService;
import holidayBooking.services.EmployeeService;

public class LoginBean {
  // if matching admin or employee email found, set session and redirect to dashboard
  public static LoginResponse login(String email, String password, EmployeeService employeeService, AdminService adminService, HttpSession session) {
    Employee employee = employeeService.findByEmail(email);
    if (employee != null) {
      if (!employee.getPassword().equals(password)) {
        return new LoginResponse(null, null);
      }

      session.setAttribute("employee", employee);
      return new LoginResponse(employee, null);
    }

    Admin admin = adminService.findByEmail(email);
    if (admin != null && !admin.getPassword().equals(password)) {
      return new LoginResponse(null, null);
    }

    session.setAttribute("admin", admin);
    return new LoginResponse(null, admin);
  }
}
