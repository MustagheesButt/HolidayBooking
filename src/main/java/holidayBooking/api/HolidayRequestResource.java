package holidayBooking.api;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import holidayBooking.models.Employee;
import holidayBooking.models.HolidayRequest;
import holidayBooking.services.EmployeeService;
import holidayBooking.services.HolidayRequestService;

@Path("/holiday-requests")
public class HolidayRequestResource {
  @Inject
  HolidayRequestService holidayRequestService;
  @Inject
  EmployeeService employeeService;
  
  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public List<HolidayRequest> list(@PathParam("id") Long id) {
    Employee employee = employeeService.find(id);
    return holidayRequestService.findAllByEmployee(employee);
  }
}
