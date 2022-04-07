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
import holidayBooking.models.Employee;
import holidayBooking.services.AdminService;
import holidayBooking.services.EmployeeService;

@WebServlet({"", "/login"})
public class LoginServlet extends HttpServlet {
  @Inject
  private EmployeeService employeeService;
  @Inject
  private AdminService adminService;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    RequestDispatcher view = req.getRequestDispatcher("login.jsp");
    view.forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String email = req.getParameter("email");
    String password = req.getParameter("password");
    HttpSession session = req.getSession();
    // session.invalidate(); // clear session

    Employee employee = employeeService.findByEmail(email);
    if (employee != null) {
      if (!employee.getPassword().equals(password)) {
        resp.sendRedirect("/login?error=email");
        return;
      }

      session.setAttribute("employee", employee);
      resp.sendRedirect("/dashboard");
      return;
    }

    Admin admin = adminService.findByEmail(email);
    if (admin != null && !admin.getPassword().equals(password)) {
      resp.sendRedirect("/login?error=email");
      return;
    }

    session.setAttribute("admin", admin);
    resp.sendRedirect("/admin");
    return;
    
  }
}
