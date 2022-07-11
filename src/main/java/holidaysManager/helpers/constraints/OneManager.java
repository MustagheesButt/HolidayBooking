package holidaysManager.helpers.constraints;

import java.util.ArrayList;
import java.util.List;

import holidaysManager.entities.Emp;
import holidaysManager.entities.HRequest;

public class OneManager extends Constraint {
  public static void check(List<String> reasons, Emp emp, HRequest hReq, List<HRequest> requestsByManagers) {
    if (emp.isManager() && isNoOtherManagerOnDuty(hReq, requestsByManagers)) {
      reasons.add("Atleast one manager must be on duty");
    }
  }

  public static boolean isNoOtherManagerOnDuty(HRequest reqToCheck, List<HRequest> managerRequests) {
    List<HRequest> filteredReqs = new ArrayList<HRequest>();
    List<Emp> emps = reqToCheck.getEmp().getDept().getEmpsByRole(4);
    if (emps.size() > 1) {
      for (HRequest h : managerRequests) {
        if (filteredReqs.isEmpty()) {
          filteredReqs.add(h);
        }
        boolean exists = false;
        for (HRequest t : filteredReqs) {
          if (h.getEmp().getId() == t.getEmp().getId()) {
            exists = true;
          }
        }
        if (!exists)
          filteredReqs.add(h);
      }
      if (emps.size() == filteredReqs.size() + 1) {
        return true;
      }
    } else {
      return true;
    }

    return false;
  }
}
