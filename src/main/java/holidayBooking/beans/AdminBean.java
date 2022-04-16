package holidayBooking.beans;

import java.time.LocalDateTime;
import java.util.List;

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
    Employee e = employeeService.find(id);
    employeeService.delete(e);
  }

  public static String updateEmployee(
      HttpServletRequest req, EmployeeService employeeService,
      RoleService roleService, DepartmentService departmentService) {
    Long id = Long.parseLong(req.getParameter("id"));
    Employee e = employeeService.find(id);
    Long role = Long.parseLong(req.getParameter("role"));
    Role r = roleService.findRole(role);
    Long department = Long.parseLong(req.getParameter("department"));
    Department d = departmentService.find(department);
    LocalDateTime now = LocalDateTime.now();

    String str = null;
    List<Employee> e_list = d.getEmployees();
    boolean update = true;
    for (int i = 0; i < e_list.size(); i++) {
      if (e_list.get(i).getId() != e.getId()) {
        if (e_list.get(i).getDepartment().getId() == d.getId()) {
          if (e_list.get(i).getRole().getId() == 1 && r.getId() == 1) {
            update = false;
            str = "Head";
          }
          if (e_list.get(i).getRole().getId() == 2 && r.getId() == 2) {
            update = false;
            str = "Deputy";
          }
        }
        if (e_list.get(i).getEmail().equalsIgnoreCase(e.getEmail())) {
          update = false;
          str = "Email";
        }
      }

      if (update == false) {
        break;
      }
    }

    if (update == true) {
      e.setFirstName(req.getParameter("fname"));
      e.setLastName(req.getParameter("lname"));
      e.setEmail(req.getParameter("email"));
      e.setPassword(req.getParameter("password"));
      e.setRole(r);
      e.setDepartment(d);
      e.setUpdatedAt(now);
      employeeService.update(e);
    }

    return str;
  }

  public static String addEmployee(HttpServletRequest req, EmployeeService employeeService, RoleService roleService,
      DepartmentService departmentService) {

    Employee e = new Employee();
    Long role = Long.parseLong(req.getParameter("role"));
    Role r = roleService.findRole(role);
    Long department = Long.parseLong(req.getParameter("department"));
    Department d = departmentService.find(department);
    LocalDateTime now = LocalDateTime.now();
    String str = null;
    List<Employee> e_list = d.getEmployees();

    boolean create = true;
    for (int i = 0; i < e_list.size(); i++) {

      if (e_list.get(i).getDepartment().getId() == d.getId()) {
        if (e_list.get(i).getRole().getId() == 1 && r.getId() == 1) {
          create = false;
          str = "Head";
        }
        if (e_list.get(i).getRole().getId() == 2 && r.getId() == 2) {
          create = false;
          str = "Deputy";
        }
      }

      if (create == false) {
        break;
      }
    }
    if (create == true) {
      boolean ex = true;
      e.setFirstName(req.getParameter("fname"));
      e.setLastName(req.getParameter("lname"));
      e.setEmail(req.getParameter("email"));
      e.setPassword(req.getParameter("password"));
      e.setRole(r);
      e.setDepartment(d);
      e.setJoiningDate(now);
      e.setCreatedAt(now);
      e.setUpdatedAt(now);
      ex = employeeService.persist(e);
      if (ex == false) {
        str = "Email";
      }
    }
    return str;
  }

  public static RequestDispatcher getManageDepartments(HttpServletRequest req, DepartmentService d) {
    req.setAttribute("departments", d.getAll());
    return req.getRequestDispatcher("/views/admin/manage_departments.jsp");
  }
}
