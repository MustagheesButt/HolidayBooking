package holidayBooking;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import holidayBooking.beans.HolidayRequestBean;
import holidayBooking.models.Employee;
import holidayBooking.models.HolidayRequest;
import holidayBooking.services.HolidayRequestService;

@WebServlet({ "/create-holiday-request", "/delete-holiday-request", "/approve-request", "/reject-request" })
@MultipartConfig
public class HolidayRequestServlet extends HttpServlet {
  @Inject
  private HolidayRequestService holidayRequestService;

  // @Resource(mappedName = "java:/ConnectionFactory")
  // private ConnectionFactory connectionFactory;

  // @Resource(mappedName = "java:/jms/myQueue")
  // private Queue myQueue;

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
      String title = req.getParameter("title");

      DateTimeFormatter pattern = DateTimeFormatter.ofPattern("EEE MMM dd yyyy HH:mm:ss 'GMT'xx '('zzzz')'");
      LocalDateTime dateStart = LocalDateTime.parse(req.getParameter("dateStart"), pattern);
      LocalDateTime dateEnd = LocalDateTime.parse(req.getParameter("dateEnd"), pattern);
      Long duration = Long.parseLong(req.getParameter("duration"));

      HolidayRequest hr = new HolidayRequest();
      hr.setTitle(title);
      hr.setDateStart(dateStart);
      hr.setDateEnd(dateEnd);
      hr.setDuration(duration);

      Employee e = (Employee) session.getAttribute("employee");
      HolidayRequestBean.createHolidayRequest(hr, holidayRequestService, e);
      // MessageSender.sendMessage(String.format("Received holiday request from %s", e.getFullName()), connectionFactory, myQueue);
    } else if (uri.contains("approve-request")) {
      HolidayRequestBean.approveRequest(req, holidayRequestService);
      redirectTo = "/admin/manage-requests";
    } else if (uri.contains("reject-request")) {
      HolidayRequestBean.rejectRequest(req, holidayRequestService);
      redirectTo = "/admin/manage-requests";
    }

    resp.sendRedirect(redirectTo);
  }
}
