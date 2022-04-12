package holidayBooking.beans;

import java.time.LocalDateTime;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import holidayBooking.models.Department;
import holidayBooking.models.Employee;
import holidayBooking.models.Role;
import holidayBooking.services.DepartmentService;
import holidayBooking.services.EmployeeService;
import holidayBooking.services.RoleService;

public class AdminBean {
  public static void deleteEmployee(HttpServletRequest req, EmployeeService employeeService) {
    Long id = Long.parseLong(req.getParameter("id"));
    Employee e = employeeService.findUser(id);
    employeeService.delete(e);
  }

  public static void updateEmployee(
      HttpServletRequest req, EmployeeService employeeService,
      RoleService roleService, DepartmentService departmentService) {
    Long id = Long.parseLong(req.getParameter("id"));
    Employee e = employeeService.findUser(id);
    Long role = Long.parseLong(req.getParameter("role"));
    Role r = roleService.findRole(role);
    Long department = Long.parseLong(req.getParameter("department"));
    Department d = departmentService.find(department);
    LocalDateTime now = LocalDateTime.now();

    e.setFirstName(req.getParameter("fname"));
    e.setLastName(req.getParameter("lname"));
    e.setEmail(req.getParameter("email"));
    e.setPassword(req.getParameter("password"));
    e.setRole(r);
    e.setDepartment(d);

    e.setUpdatedAt(now);

    employeeService.update(e);
  }

  public static void addEmployee(HttpServletRequest req, EmployeeService employeeService, RoleService roleService,
      DepartmentService departmentService) {

    Employee e = new Employee();
    Long role = Long.parseLong(req.getParameter("role"));
    Role r = roleService.findRole(role);
    Long department = Long.parseLong(req.getParameter("department"));
    Department d = departmentService.find(department);
    LocalDateTime now = LocalDateTime.now();

    e.setFirstName(req.getParameter("fname"));
    e.setLastName(req.getParameter("lname"));
    e.setEmail(req.getParameter("email"));
    e.setPassword(req.getParameter("password"));
    e.setRole(r);
    e.setDepartment(d);
    e.setJoiningDate(now);
    e.setCreatedAt(now);
    e.setUpdatedAt(now);

    employeeService.persist(e);
    ;
  }

  public static RequestDispatcher getManageDepartments(HttpServletRequest req, DepartmentService d) {
    req.setAttribute("departments", d.getAll());
    return req.getRequestDispatcher("/views/admin/manage_departments.jsp");
  }
}
