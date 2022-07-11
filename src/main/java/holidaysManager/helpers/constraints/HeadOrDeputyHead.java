package holidaysManager.helpers.constraints;

import java.util.List;

import holidaysManager.entities.Emp;
import holidaysManager.entities.HRequest;

public class HeadOrDeputyHead extends Constraint {
  public static void check(List<String> reasons, Emp emp, HRequest hReq, List<HRequest> approvedRequests) {
    if (emp.isDeputyHeadOfDept() && isHeadOnHoliday(hReq, approvedRequests)) {
      reasons.add("Head is already on holiday");
    }

    if (emp.isHeadOfDept() && isDeputyHeadOnHoliday(hReq, approvedRequests)) {
      reasons.add("Deputy Head is already on holiday");
    }
  }

  private static boolean isHeadOnHoliday(HRequest reqToCheck, List<HRequest> approvedRequests) {
    for (int i = 0; i < approvedRequests.size(); i++) {
      HRequest hr = approvedRequests.get(i);
      if (hr.getEmp().getId() != reqToCheck.getEmp().getId()) {
        if (hr.getEmp().isHeadOfDept()
            && hr.getEmp().getDept().getId() == reqToCheck.getEmp().getDept().getId()) {
          if (hr.getDateStart().compareTo(reqToCheck.getDateStart()) >= 0
              && hr.getDateStart().compareTo(reqToCheck.getDateEnd()) < 0) {
            return true;
          } else if (reqToCheck.getDateStart().compareTo(hr.getDateStart()) >= 0
              && reqToCheck.getDateStart().compareTo(hr.getDateEnd()) < 0) {
            return true;
          }
        }
      }
    }

    return false;
  }

  private static boolean isDeputyHeadOnHoliday(HRequest reqToCheck, List<HRequest> approvedRequests) {
    for (int i = 0; i < approvedRequests.size(); i++) {
      HRequest hr = approvedRequests.get(i);
      if (hr.getEmp().getId() != reqToCheck.getEmp().getId()) {
        if (hr.getEmp().isDeputyHeadOfDept()
            && hr.getEmp().getDept().getId() == reqToCheck.getEmp().getDept().getId()) {
          if (hr.getDateStart().compareTo(reqToCheck.getDateStart()) >= 0
              && hr.getDateStart().compareTo(reqToCheck.getDateEnd()) < 0) {
            return true;
          } else if (reqToCheck.getDateStart().compareTo(hr.getDateStart()) >= 0
              && reqToCheck.getDateStart().compareTo(hr.getDateEnd()) < 0) {
            return true;
          }
        }
      }
    }

    return false;
  }
}
