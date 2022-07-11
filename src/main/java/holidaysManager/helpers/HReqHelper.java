package holidaysManager.helpers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import holidaysManager.entities.Emp;
import holidaysManager.entities.HRequest;
import holidaysManager.services.HReqService;

public class HReqHelper {
  public static HRequest createHolidayRequest(HRequest hr, HReqService holidayRequestService, Emp e) {
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
  public static void approveRequest(HttpServletRequest req, HReqService holidayRequestService ) {
    Long id = Long.parseLong(req.getParameter("id"));
    HRequest hr = holidayRequestService.find(id);
    hr.setStatus("approved");   
    holidayRequestService.update(hr);
  }

  public static void rejectRequest(HttpServletRequest req, HReqService holidayRequestService) {
    Long id = Long.parseLong(req.getParameter("id"));

    HRequest hr = holidayRequestService.find(id);
    hr.setStatus("rejected");

    holidayRequestService.update(hr);
  }

  public static HRequest hReqFromJson(String jsonString) {
    Gson gson = Converters.registerLocalDateTime(new GsonBuilder()).create();
    return gson.fromJson(jsonString, HRequest.class);
  }

  public static List<HRequest> hReqsFromJson(String jsonString) {
    Type listType = new TypeToken<ArrayList<HRequest>>(){}.getType();
    Gson gson = Converters.registerLocalDateTime(new GsonBuilder()).create();
    return gson.fromJson(jsonString, listType);
  }
}
