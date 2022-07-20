package holidaysManager.helpers;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import holidaysManager.entities.Dept;
import holidaysManager.entities.Emp;
import holidaysManager.entities.Role;
import holidaysManager.services.DeptService;
import holidaysManager.services.EmpService;
import holidaysManager.services.RoleService;

public class AdminHelper {
  public static void deleteEmployee(HttpServletRequest req, EmpService employeeService) {
    Long id = Long.parseLong(req.getParameter("id"));
    Emp e = employeeService.find(id);
    employeeService.delete(e);
  }

  public static String updateEmployee(
      HttpServletRequest req, EmpService employeeService,
      RoleService roleService, DeptService departmentService) {
    Long id = Long.parseLong(req.getParameter("id"));
    Emp e = employeeService.find(id);
    Long role = Long.parseLong(req.getParameter("role"));
    Role r = roleService.findRole(role);
    Long department = Long.parseLong(req.getParameter("department"));

    Dept d = departmentService.find(department);
    LocalDateTime now = LocalDateTime.now();

    boolean update = true;
    String str = null;
    List<Emp> e_list = d.getEmployees();
    for (int i = 0; i < e_list.size(); i++) {
      if (e_list.get(i).getId() != e.getId()) {
        if (e_list.get(i).getDept().getId() == d.getId()) {
          if (e_list.get(i).isHeadOfDept() && r.getId() == 1) {
            update = false;
            str = "Head";
          }
          if (e_list.get(i).isDeputyHeadOfDept() && r.getId() == 2) {
            update = false;
            str = "Deputy";
          }
        }
        if (e_list.get(i).getEmail().equalsIgnoreCase(e.getEmail())) {
          update = false;
          str = "Email";
        }
      }
      if (!update) {
        break;
      }
    }
    if (update) {
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

  public static String addEmployee(HttpServletRequest req, EmpService employeeService, RoleService roleService,
      DeptService departmentService) {

    Emp e = new Emp();
    Long role = Long.parseLong(req.getParameter("role"));
    Role r = roleService.findRole(role);
    Long department = Long.parseLong(req.getParameter("department"));

    Dept d = departmentService.find(department);
    LocalDateTime now = LocalDateTime.now();
    boolean flag = true;
    String str = null;
    List<Emp> e_list = d.getEmployees();
    for (int i = 0; i < e_list.size(); i++) {
      if (e_list.get(i).getDept().getId() == d.getId()) {
        if (e_list.get(i).getRole().getId() == 1 && r.getId() == 1) {
          flag = false;
          str = "Head";
        }
        if (e_list.get(i).getRole().getId() == 2 && r.getId() == 2) {
          flag = false;
          str = "Deputy";
        }
      }

      if (!flag) {
        break;
      }
    }

    if (flag) {
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

  public static RequestDispatcher getManageDepartments(HttpServletRequest req, DeptService d) {
    req.setAttribute("departments", d.getAll());
    return req.getRequestDispatcher("/views/admin/departments.jsp");
  }

  public static RequestDispatcher getManageRoles(HttpServletRequest req, RoleService r) {
    req.setAttribute("roles", r.getAll());
    return req.getRequestDispatcher("/views/admin/roles.jsp");
  }
}
