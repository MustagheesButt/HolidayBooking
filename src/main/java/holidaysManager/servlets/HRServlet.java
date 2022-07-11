package holidaysManager.servlets;

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

import holidaysManager.entities.Emp;
import holidaysManager.entities.HRequest;
import holidaysManager.helpers.HReqHelper;
import holidaysManager.services.HReqService;

@WebServlet({ "/create-holiday-request", "/delete-holiday-request", "/approve-request", "/reject-request" })
@MultipartConfig
public class HRServlet extends HttpServlet {
  @Inject
  private HReqService holidayRequestService;

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
      String reason = req.getParameter("reason");

      DateTimeFormatter pattern = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
      LocalDateTime dateStart = LocalDateTime.parse(req.getParameter("dateStart"), pattern);
      LocalDateTime dateEnd = LocalDateTime.parse(req.getParameter("dateEnd"), pattern);

      HRequest hr = new HRequest();
      hr.setReason(reason);
      hr.setDateStart(dateStart);
      hr.setDateEnd(dateEnd);

      Emp e = (Emp) session.getAttribute("employee");
      HReqHelper.createHolidayRequest(hr, holidayRequestService, e);

      redirectTo = "/manage-requests";
    } else if (uri.contains("approve-request")) {
      HReqHelper.approveRequest(req, holidayRequestService);

      if (session.getAttribute("admin") == null) {
        redirectTo = "/manage-department-requests";
      } else {
        redirectTo = "/admin/manage-requests";
      }
    } else if (uri.contains("reject-request")) {
      HReqHelper.rejectRequest(req, holidayRequestService);

      if (session.getAttribute("admin") == null) {
        redirectTo = "/manage-department-requests";
      } else {
        redirectTo = "/admin/manage-requests";
      }
    }

    resp.sendRedirect(redirectTo);
  }
}
