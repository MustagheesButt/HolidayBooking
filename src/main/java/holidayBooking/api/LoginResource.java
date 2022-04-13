package holidayBooking.api;

import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("/login")
public class LoginResource {

  @GET
  @Path("/test")
  public String test() {
    return "Hello world!";
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Object login(String email, String password) {
    return new Object();
  }
}
