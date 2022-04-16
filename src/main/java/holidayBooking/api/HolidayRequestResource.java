package holidayBooking.api;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import holidayBooking.beans.HolidayRequestBean;
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

  @GET
  @Path("/approved/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public List<HolidayRequest> listApproved(@PathParam("id") Long id) {
    Employee employee = employeeService.find(id);
    return holidayRequestService.findAllByEmployee(employee)
        .stream()
        .filter(hr -> hr.getStatus().equals("approved"))
        .toList();
  }

  @POST
  @Path("/create/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public HolidayRequest create(HolidayRequest hr, @Context HttpServletRequest request, @PathParam("id") Long id) {
    Employee e = employeeService.find(id);
    return HolidayRequestBean.createHolidayRequest(hr, holidayRequestService, e);
  }
}
