package holidaysManager.servlets;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import holidaysManager.entities.Employee;
import holidaysManager.entities.HolidayRequest;
import holidaysManager.services.EmployeeService;
import holidaysManager.services.HolidayRequestService;

@WebServlet({"/dashboard", "/request-holidays", "/manage-requests", "/profile", "/manage-department-requests"})
public class EmployeeServlet extends HttpServlet {
  @Inject
  private EmployeeService employeeService;
  @Inject
  private HolidayRequestService holidayRequestService;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    RequestDispatcher view = req.getRequestDispatcher("views/employees/dashboard.jsp");
    HttpSession session = req.getSession();
    String uri = req.getRequestURI();

    Employee currEmployee = (Employee)session.getAttribute("employee");
    // require login
    if (currEmployee == null) {
      resp.sendRedirect("/login");
      return;
    } else {
      // to update session in case employee was updated by admin
      session.setAttribute("employee", employeeService.find(currEmployee.getId()));
    }

    // match requested URL and load appropriate view/jsp
    if (uri.contains("manage-requests")) {
      List<HolidayRequest> requests = holidayRequestService.findAllByEmployee((Employee)session.getAttribute("employee"));
      req.setAttribute("holidayRequests", requests);
      view = req.getRequestDispatcher("views/employees/manage_requests.jsp");
    } else if (uri.contains("manage-department-requests")) {
      List<HolidayRequest> pendingRequests = holidayRequestService
        .getPending()
        .stream()
        .filter(hr -> hr.getEmployee().getDepartment().getId() == currEmployee.getDepartment().getId())
        .collect(Collectors.toList());

			// Functionality G - Prioritize by # of holidays already approved, and days
			// requested during peak 
			pendingRequests.sort(new Comparator<HolidayRequest>() {
				@Override
				public int compare(HolidayRequest hr1, HolidayRequest hr2) {
					Long total1 = hr1.getDaysDuringPeakTime() + hr1.getEmployee().getHolidayBookingsDayCount();
					Long total2 = hr2.getDaysDuringPeakTime() + hr2.getEmployee().getHolidayBookingsDayCount();
					return total1.compareTo(total2);
				}
			});

			req.setAttribute("holidayRequests", pendingRequests);
      view = req.getRequestDispatcher("views/employees/manage_department_requests.jsp");
    }

    view.forward(req, resp);
  }
}
