package holidayBooking;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import holidayBooking.models.Employee;
import holidayBooking.models.HolidayRequest;
// import holidayBooking.services.EmployeeService;
import holidayBooking.services.HolidayRequestService;

@WebServlet({"/dashboard", "/request-holidays", "/manage-requests", "/profile"})
public class EmployeeServlet extends HttpServlet {
  // @Inject
  // private EmployeeService employeeService;
  @Inject
  private HolidayRequestService holidayRequestService;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    RequestDispatcher view = req.getRequestDispatcher("views/employees/dashboard.jsp");
    HttpSession session = req.getSession();
    String uri = req.getRequestURI();

    if (session.getAttribute("employee") == null) {
      resp.sendRedirect("/login");
      return;
    }

    if (uri.contains("manage-requests")) {
      List<HolidayRequest> requests = holidayRequestService.findAllByEmploee((Employee)session.getAttribute("employee"));
      req.setAttribute("holidayRequests", requests);
      view = req.getRequestDispatcher("views/employees/manage_requests.jsp");
    }

    view.forward(req, resp);
  }
}
