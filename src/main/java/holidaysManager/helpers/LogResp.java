package holidaysManager.helpers;

import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import holidaysManager.entities.Admin;
import holidaysManager.entities.Emp;

public class LogResp {
  private Emp employee;
  private Admin admin;

  public static LogResp pLogResp(String jsonString) {
    Gson gson = Converters.registerLocalDateTime(new GsonBuilder()).create();
    return gson.fromJson(jsonString, LogResp.class);
  }

  public LogResp() {
  }

  public LogResp(Emp employee, Admin admin) {
    this.employee = employee;
    this.admin = admin;
  }

  public Emp getEmployee() {
    return this.employee;
  }

  public void setEmployee(Emp e) {
    this.employee = e;
  }

  public Admin getAdmin() {
    return this.admin;
  }

  public void setAdmin(Admin a) {
    this.admin = a;
  }
}
