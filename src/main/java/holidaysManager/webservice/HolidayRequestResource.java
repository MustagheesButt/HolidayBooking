package holidaysManager.webservice;

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

import holidaysManager.entities.Emp;
import holidaysManager.entities.HRequest;
import holidaysManager.helpers.HReqHelper;
import holidaysManager.helpers.MessageSender;
import holidaysManager.services.EmpService;
import holidaysManager.services.HReqService;

@Path("/holiday-requests")
public class HolidayRequestResource {
  @Inject
  HReqService holidayRequestService;
  @Inject
  EmpService employeeService;

  @Resource(mappedName = "java:/ConnectionFactory")
  private ConnectionFactory connectionFactory;

  @Resource(mappedName = "java:/jms/holidayQueue")
  private Queue holidayQueue;

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public List<HRequest> list(@PathParam("id") Long id) {
    Emp employee = employeeService.find(id);
    return holidayRequestService.findAllByEmployee(employee);
  }

  @GET
  @Path("/approved/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public List<HRequest> listApproved(@PathParam("id") Long id) {
    Emp employee = employeeService.find(id);
    return holidayRequestService.findAllByEmployee(employee)
        .stream()
        .filter(hr -> hr.getStatus().equals("approved"))
        .toList();
  }

  @POST
  @Path("/create/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public HRequest create(HRequest hr, @Context HttpServletRequest request, @PathParam("id") Long id) {
    Emp e = employeeService.find(id);

    HRequest rv = HReqHelper.createHolidayRequest(hr, holidayRequestService, e);

    if (rv != null) {
      MessageSender.sendMessage(String.format("Received holiday request from %s", e.getFullName()), connectionFactory, holidayQueue);
    }

    return rv;
  }
}
