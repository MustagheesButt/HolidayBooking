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

import holidaysManager.entities.Emp;
import holidaysManager.entities.HRequest;
import holidaysManager.services.EmpService;
import holidaysManager.services.HReqService;

@WebServlet({"/dashboard", "/request-holidays", "/manage-requests", "/profile", "/manage-department-requests"})
public class EmployeeServlet extends HttpServlet {
  @Inject
  private EmpService employeeService;
  @Inject
  private HReqService holidayRequestService;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    RequestDispatcher view = req.getRequestDispatcher("views/employees/dashboard.jsp");
    HttpSession session = req.getSession();
    String uri = req.getRequestURI();

    Emp currentEmp = (Emp)session.getAttribute("employee");

    if (currentEmp == null) {
      resp.sendRedirect("/login");
      return;
    } else {
      session.setAttribute("employee", employeeService.find(currentEmp.getId()));
    }

    if (uri.contains("manage-requests")) {
      List<HRequest> requests = holidayRequestService.findAllByEmployee((Emp)session.getAttribute("employee"));
      req.setAttribute("holidayRequests", requests);
      view = req.getRequestDispatcher("views/employees/requests.jsp");
    } else if (uri.contains("manage-department-requests")) {
      List<HRequest> pendingRequests = holidayRequestService
        .getPending()
        .stream()
        .filter(hr -> hr.getEmp().getDept().getId() == currentEmp.getDept().getId())
        .collect(Collectors.toList());

			pendingRequests.sort(new Comparator<HRequest>() {
				@Override
				public int compare(HRequest hr1, HRequest hr2) {
					Long total1 = hr1.getPeakDaysCount() + hr1.getEmp().getApprovedReqsDayCount();
					Long total2 = hr2.getPeakDaysCount() + hr2.getEmp().getApprovedReqsDayCount();
					return total1.compareTo(total2);
				}
			});

			req.setAttribute("holidayRequests", pendingRequests);
      view = req.getRequestDispatcher("views/employees/department_requests.jsp");
    }

    view.forward(req, resp);
  }
}
