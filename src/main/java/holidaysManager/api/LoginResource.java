package holidaysManager.api;

import javax.ws.rs.Produces;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import holidaysManager.helpers.LoginBean;
import holidaysManager.helpers.LoginResponse;
import holidaysManager.services.AdminService;
import holidaysManager.services.EmployeeService;

@Path("/auth")
public class LoginResource {
  @Inject
  EmployeeService employeeService;
  @Inject
  AdminService adminService;

  @GET
  @Path("/test")
  public String test() {
    return "Hello world!";
  }

  @POST
  @Path("/login")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public LoginResponse login(LoginJson lJson, @Context HttpServletRequest request) {
    System.out.println(lJson.getEmail() + " " + lJson.getPassword());
    HttpSession session = request.getSession();
    LoginResponse loginStatus = LoginBean.login(lJson.getEmail(), lJson.getPassword(), employeeService, adminService,
        session);
    return loginStatus;
  }
}