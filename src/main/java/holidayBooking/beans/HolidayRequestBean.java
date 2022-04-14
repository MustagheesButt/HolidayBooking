package holidayBooking.beans;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;

import holidayBooking.models.Employee;
import holidayBooking.models.HolidayRequest;
import holidayBooking.services.HolidayRequestService;
import holidayBooking.services.EmployeeService;

public class HolidayRequestBean {
  public static void createHolidayRequest(HttpServletRequest req, HolidayRequestService holidayRequestService, Employee e) {
    String title = req.getParameter("title");
    DateTimeFormatter pattern = DateTimeFormatter.ofPattern("EEE MMM dd yyyy HH:mm:ss 'GMT'xx '('zzzz')'");
    long duration =Long.parseLong(req.getParameter("duration"));
    		
    LocalDateTime dateStart = LocalDateTime.parse(req.getParameter("date_start"), pattern);
    LocalDateTime dateEnd = LocalDateTime.parse(req.getParameter("date_end"), pattern);

    HolidayRequest hr = new HolidayRequest();
    hr.setTitle(title);
    hr.setDateStart(dateStart);
    hr.setDateEnd(dateEnd);
    hr.setStatus("pending");
    hr.setDuration(duration);
    hr.setEmployee(e);
    
    holidayRequestService.persist(hr);
  }
  public static void approveRequest(HttpServletRequest req, HolidayRequestService holidayRequestService ) {
    Long id = Long.parseLong(req.getParameter("id"));
    HolidayRequest hr = holidayRequestService.find(id);
    hr.setStatus("approved");   
    holidayRequestService.update(hr);
  }

  public static void rejectRequest(HttpServletRequest req, HolidayRequestService holidayRequestService) {
    Long id = Long.parseLong(req.getParameter("id"));

    HolidayRequest hr = holidayRequestService.find(id);
    hr.setStatus("rejected");

    holidayRequestService.update(hr);
  }
}
