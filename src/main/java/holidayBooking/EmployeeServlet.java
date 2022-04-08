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

import holidayBooking.models.Employee;
import holidayBooking.services.EmployeeService;

@WebServlet({"/dashboard", "/request-holidays", "/manage-requests", "/profile"})
public class EmployeeServlet extends HttpServlet {
  @Inject
  private EmployeeService employeeService;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    RequestDispatcher view = req.getRequestDispatcher("views/employees/dashboard.jsp");
    HttpSession session = req.getSession();

    if (session.getAttribute("employee") == null) {
      resp.sendRedirect("/login");
      return;
    }
  
    Employee u = (Employee)session.getAttribute("employee");
    req.setAttribute("user", u);

    view.forward(req, resp);
  }
}
