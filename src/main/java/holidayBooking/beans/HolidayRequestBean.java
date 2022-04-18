package holidayBooking.beans;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import holidayBooking.models.Employee;
import holidayBooking.models.HolidayRequest;
import holidayBooking.services.HolidayRequestService;

public class HolidayRequestBean {
  public static HolidayRequest createHolidayRequest(HolidayRequest hr, HolidayRequestService holidayRequestService, Employee e) {
    hr.setStatus("pending");
    hr.setEmployee(e);
    
    try {
      holidayRequestService.persist(hr);
    } catch (Exception exception) {
      exception.printStackTrace();
      return null;
    }

    return hr;
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

  public static HolidayRequest parseHolidayRequest(String jsonString) {
    Gson gson = Converters.registerLocalDateTime(new GsonBuilder()).create();
    System.out.println("DEBUG-----------------");
    System.out.println(jsonString);
    return gson.fromJson(jsonString, HolidayRequest.class);
  }

  public static List<HolidayRequest> parseHolidayRequests(String jsonString) {
    Type listType = new TypeToken<ArrayList<HolidayRequest>>(){}.getType();
    Gson gson = Converters.registerLocalDateTime(new GsonBuilder()).create();
    return gson.fromJson(jsonString, listType);
  }
}
