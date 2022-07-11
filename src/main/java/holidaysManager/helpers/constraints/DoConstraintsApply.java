package holidaysManager.helpers.constraints;

import holidaysManager.entities.HRequest;

// Constraints do not apply between December 23rd and January 3rd.
public class DoConstraintsApply extends Constraint {
  public static Boolean check(HRequest hReq) {
    if (hReq.getDateStart().getMonthValue() == 12 && hReq.getDateStart().getDayOfMonth() >= 23) {
      if (hReq.getDateEnd().getMonthValue() == 1 && hReq.getDateEnd().getDayOfMonth() <= 3) {
        return false;
      } else if ((hReq.getDateEnd().getMonthValue() == 12 && hReq.getDateEnd().getDayOfMonth() >= 23)) {
        return false;
      }
    } else if ((hReq.getDateStart().getMonthValue() == 1)
        && (hReq.getDateStart().getDayOfMonth() >= 1 && hReq.getDateEnd().getDayOfMonth() <= 4)) {
      return false;
    }

    return true;
  }
}
