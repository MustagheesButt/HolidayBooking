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
// import holidayBooking.models.Employee;
import holidayBooking.services.EmployeeService;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
  @Inject
  private EmployeeService employeeService;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    RequestDispatcher view = req.getRequestDispatcher("admin_dashboard.jsp");
    HttpSession session = req.getSession();

    if (session.getAttribute("admin") == null) {
      resp.sendRedirect("/login");
      return;
    }
  
    Admin admin = (Admin)session.getAttribute("admin");
    req.setAttribute("admin", admin);
    req.setAttribute("employees", employeeService.getAll());

    view.forward(req, resp);
  }
}
