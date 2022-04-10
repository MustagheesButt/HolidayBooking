package holidayBooking;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import holidayBooking.models.Admin;
import holidayBooking.models.Employee;
import holidayBooking.services.DepartmentService;
import holidayBooking.models.Role;
// import holidayBooking.models.Employee;
import holidayBooking.services.EmployeeService;
import holidayBooking.services.HolidayRequestService;
import holidayBooking.services.RoleService;
import holidayBooking.models.Department;

@WebServlet({"/admin", "/admin/manage-employees", "/delete-employee", "/edit-employee",  "/update-employee", "/admin/manage-roles", "/admin/manage-departments", "/admin/manage-requests", "/admin/edit-employee" })
public class AdminServlet extends HttpServlet {
  @Inject
  private EmployeeService employeeService;
  @Inject
  private DepartmentService departmentService;
  @Inject
  private RoleService roleService;
  @Inject
  private HolidayRequestService holidayRequestService;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    RequestDispatcher view = req.getRequestDispatcher("/views/admin/dashboard.jsp");
    HttpSession session = req.getSession();
    String uri = req.getRequestURI();

    if (session.getAttribute("admin") == null) {
      resp.sendRedirect("/login");
      return;
    }

    Admin admin = (Admin)session.getAttribute("admin");
    req.setAttribute("admin", admin);

    if (uri.contains("manage-departments")) {
      view = AdminServlet.getManageDepartments(req, departmentService);
    } else if (uri.contains("manage-requests")) {
      req.setAttribute("holidayRequests", holidayRequestService.getAll());
      view = req.getRequestDispatcher("/views/admin/manage_requests.jsp");
    }
    else if (uri.contains("edit-employee")) { 
    	req.setAttribute("employee", employeeService.findUser(Long.parseLong(req.getParameter("id"))));
    	req.setAttribute("roles", roleService.getAll());
    	req.setAttribute("departments", departmentService.getAll());
    	view = req.getRequestDispatcher("/views/admin/edit_employee.jsp");
    }
    else {
      req.setAttribute("employees", employeeService.getAll());
    }

    view.forward(req, resp);
  }

  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    HttpSession session = req.getSession();
	    String uri = req.getRequestURI();

	    if (session.getAttribute("admin") == null) {
	      resp.sendRedirect("/login");
	      return;
	    }

	    String redirectTo = "/admin";
	    if (uri.contains("delete-employee")) {
	    	AdminServlet.deleteEmployee(req, employeeService);
	    }
	    else if (uri.contains("update-employee")) {
	    	AdminServlet.updateEmployee(req, employeeService, roleService, departmentService);
	    }

	    resp.sendRedirect(redirectTo);
	  }
  private static void deleteEmployee(HttpServletRequest req, EmployeeService employeeService) {
	  Long id = Long.parseLong(req.getParameter("id"));
	  Employee e = employeeService.findUser(id);
	  employeeService.delete(e);
  }
  
  private static void updateEmployee(HttpServletRequest req, EmployeeService employeeService, RoleService roleService, DepartmentService departmentService) {
	  Long id = Long.parseLong(req.getParameter("id"));
	  Employee e = employeeService.findUser(id);
	  Long role = Long.parseLong(req.getParameter("role"));
	  Role r =  roleService.findRole(role);
	  Long department = Long.parseLong(req.getParameter("department"));
	  Department d =  departmentService.find(department);
	  e.setFirstName(req.getParameter("fname"));
	  e.setLastName(req.getParameter("lname"));
	  e.setEmail(req.getParameter("email"));
	  e.setPassword(req.getParameter("password"));
	  e.setRole(r);
	  e.setDepartment(d);
	  
	  employeeService.update(e);
  }
  private static RequestDispatcher getManageDepartments(HttpServletRequest req, DepartmentService d) {
    req.setAttribute("departments", d.getAll());
    return req.getRequestDispatcher("/views/admin/manage_departments.jsp");
  }
}
