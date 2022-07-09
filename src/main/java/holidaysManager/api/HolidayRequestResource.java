package holidaysManager.api;

import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import holidaysManager.entities.Employee;
import holidaysManager.entities.HolidayRequest;
import holidaysManager.helpers.HolidayRequestBean;
import holidaysManager.helpers.MessageSender;
import holidaysManager.services.EmployeeService;
import holidaysManager.services.HolidayRequestService;

@Path("/holiday-requests")
public class HolidayRequestResource {
  @Inject
  HolidayRequestService holidayRequestService;
  @Inject
  EmployeeService employeeService;

  @Resource(mappedName = "java:/ConnectionFactory")
  private ConnectionFactory connectionFactory;

  @Resource(mappedName = "java:/jms/holidayQueue")
  private Queue holidayQueue;

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

    HolidayRequest rv = HolidayRequestBean.createHolidayRequest(hr, holidayRequestService, e);

    if (rv != null) {
      MessageSender.sendMessage(String.format("Received holiday request from %s", e.getFullName()), connectionFactory, holidayQueue);
    }

    return rv;
  }
}