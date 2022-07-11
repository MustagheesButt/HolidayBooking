package holidaysManager.helpers.constraints;

import java.util.List;

import holidaysManager.entities.Emp;
import holidaysManager.entities.HRequest;

public class MaxHolidays extends Constraint {
  public static void check(List<String> reasons, Emp emp, HRequest hReq) {
    if (emp.getApprovedReqs().size() >= emp.getRemainingHolidays()
       || hReq.getDuration() > emp.getRemainingHolidays()) {
      reasons.add("Max holidays used.");
		}
  }
}
