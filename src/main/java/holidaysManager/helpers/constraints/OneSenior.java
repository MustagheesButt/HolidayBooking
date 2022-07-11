package holidaysManager.helpers.constraints;

import java.util.ArrayList;
import java.util.List;

import holidaysManager.entities.Emp;
import holidaysManager.entities.HRequest;

public class OneSenior extends Constraint {
  public static void check(List<String> reasons, Emp emp, HRequest hReq, List<HRequest> requestsBySeniors) {
    if (emp.isSeniorStaff() && isNoOtherSeniorStaffOnDuty(hReq, requestsBySeniors)) {
      reasons.add("Atleast one senior staff must be on duty");
    }
  }

  private static boolean isNoOtherSeniorStaffOnDuty(HRequest reqToCheck, List<HRequest> seniorStaffRequests) {
    List<HRequest> filteredReqs = new ArrayList<HRequest>();
    List<Emp> emps = reqToCheck.getEmp().getDept().getEmpsByRole(5);
    if (emps.size() > 1) {
      for (HRequest h : seniorStaffRequests) {
        if (filteredReqs.isEmpty()) {
          filteredReqs.add(h);
        }
        boolean exists = false;
        for (HRequest t : filteredReqs) {
          if (h.getEmp().getId() == t.getEmp().getId()) {
            exists = true;
          }
        }
        if (!exists) {
          filteredReqs.add(h);
        }
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
