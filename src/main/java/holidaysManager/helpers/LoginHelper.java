package holidaysManager.helpers;

import javax.servlet.http.HttpSession;

import holidaysManager.entities.Admin;
import holidaysManager.entities.Emp;
import holidaysManager.services.AdminService;
import holidaysManager.services.EmpService;

public class LoginHelper {
  public static LoginResponse login(String email, String password, EmpService employeeService, AdminService adminService, HttpSession session) {
    Emp employee = employeeService.findByEmail(email);
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
