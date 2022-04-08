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

import holidayBooking.models.Admin;
import holidayBooking.services.DepartmentService;
// import holidayBooking.models.Employee;
import holidayBooking.services.EmployeeService;

@WebServlet({"/admin", "/admin/manage-employees", "/admin/manage-roles", "/admin/manage-departments"})
public class AdminServlet extends HttpServlet {
  @Inject
  private EmployeeService employeeService;
  @Inject
  private DepartmentService departmentService;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    RequestDispatcher view = req.getRequestDispatcher("views/admin/dashboard.jsp");
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
    } else {
      req.setAttribute("employees", employeeService.getAll());
    }

    view.forward(req, resp);
  }

  private static RequestDispatcher getManageDepartments(HttpServletRequest req, DepartmentService d) {
    req.setAttribute("departments", d.getAll());
    return req.getRequestDispatcher("/views/admin/manage_departments.jsp");
  }
}
