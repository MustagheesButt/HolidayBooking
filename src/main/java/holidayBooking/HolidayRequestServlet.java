package holidayBooking;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import holidayBooking.models.Employee;
import holidayBooking.models.HolidayRequest;
import holidayBooking.services.EmployeeService;
import holidayBooking.services.HolidayRequestService;

@WebServlet({"/create-holiday-request", "/delete-holiday-request", "/approve-request", "/reject-request"})
@MultipartConfig
public class HolidayRequestServlet extends HttpServlet {
  @Inject
  private EmployeeService employeeService;
  @Inject
  private HolidayRequestService holidayRequestService;

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    HttpSession session = req.getSession();
    String uri = req.getRequestURI();

    if (session.getAttribute("employee") == null && session.getAttribute("admin") == null) {
      resp.sendRedirect("/login");
      return;
    }

    String redirectTo = "/dashboard";
    if (uri.contains("create-holiday-request")) {
      HolidayRequestServlet.createHolidayRequest(req, holidayRequestService, (Employee)session.getAttribute("employee"));
    } else if (uri.contains("approve-request")) {
      HolidayRequestServlet.approveRequest(req, holidayRequestService);
      redirectTo = "/admin/manage-requests";
    } else if (uri.contains("reject-request")) {
      HolidayRequestServlet.rejectRequest(req, holidayRequestService);
      redirectTo = "/admin/manage-requests";
    }

    resp.sendRedirect(redirectTo);
  }

  private static void createHolidayRequest(HttpServletRequest req, HolidayRequestService holidayRequestService, Employee e) {
    String title = req.getParameter("title");
    DateTimeFormatter pattern = DateTimeFormatter.ofPattern("EEE MMM dd yyyy HH:mm:ss 'GMT'xx '('zzzz')'");
  
    LocalDateTime dateStart = LocalDateTime.parse(req.getParameter("date_start"), pattern);
    LocalDateTime dateEnd = LocalDateTime.parse(req.getParameter("date_end"), pattern);

    HolidayRequest hr = new HolidayRequest();
    hr.setTitle(title);
    hr.setDateStart(dateStart);
    hr.setDateEnd(dateEnd);
    hr.setStatus("pending");
    hr.setEmployee(e);
    
    holidayRequestService.persist(hr);
  }

  private static void approveRequest(HttpServletRequest req, HolidayRequestService holidayRequestService) {
    Long id = Long.parseLong(req.getParameter("id"));

    HolidayRequest hr = holidayRequestService.find(id);
    hr.setStatus("approved");

    holidayRequestService.update(hr);
  }

  private static void rejectRequest(HttpServletRequest req, HolidayRequestService holidayRequestService) {
    Long id = Long.parseLong(req.getParameter("id"));

    HolidayRequest hr = holidayRequestService.find(id);
    hr.setStatus("rejected");

    holidayRequestService.update(hr);
  }
}
