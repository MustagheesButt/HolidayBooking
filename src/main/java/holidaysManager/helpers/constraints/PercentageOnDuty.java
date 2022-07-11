package holidaysManager.helpers.constraints;

import java.util.ArrayList;
import java.util.List;

import holidaysManager.entities.Emp;
import holidaysManager.entities.HRequest;

// 60% of department staff must be on duty, and sometimes 40%
public class PercentageOnDuty {
  public static void check(List<String> reasons, Emp emp, HRequest hReq) {
    List<Emp> emps = hReq.getEmp().getDept().getEmployees();

    int em_duty = emps.size() - getUniqueReqs(emp, hReq).size() - 1;
    if (hReq.getDateStart().getMonthValue() == 8 || hReq.getDateEnd().getMonthValue() == 8) {
      if ((float) em_duty / emps.size() * 100 < 40.0) {
        reasons.add("At least 40% department must be on duty in August");
      }
    } else if ((float) em_duty / emps.size() * 100 < 60.0) {
      reasons.add("At least 60% department must be on duty");
    }
  }

  private static List<HRequest> getUniqueReqs(Emp emp, HRequest hr) {
    List<HRequest> uniqueRequests = new ArrayList<HRequest>();
    List<HRequest> filteredReqs = emp
        .getDept()
        .getApprovedRequestsBetween(hr.getDateStart(), hr.getDateEnd());
    for (HRequest x : filteredReqs) {
      if (uniqueRequests.isEmpty()) {
        uniqueRequests.add(x);
      }

      boolean exists = false;
      for (HRequest y : uniqueRequests) {
        if (x.getEmp().getId() == y.getEmp().getId()) {
          exists = true;
        }
      }

      if (!exists)
        uniqueRequests.add(x);
    }

    return uniqueRequests;
  }
}
