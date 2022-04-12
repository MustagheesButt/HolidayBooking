package holidayBooking;

import java.io.IOException;

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
import holidayBooking.services.HolidayRequestService;

@WebServlet({"/create-holiday-request", "/delete-holiday-request", "/approve-request", "/reject-request"})
@MultipartConfig
public class HolidayRequestServlet extends HttpServlet {
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
      HolidayRequestBean.createHolidayRequest(req, holidayRequestService, (Employee)session.getAttribute("employee"));
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
