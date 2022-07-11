package holidaysManager.helpers;

import java.util.ArrayList;
import java.util.List;

import holidaysManager.entities.Emp;
import holidaysManager.entities.HRequest;
import holidaysManager.helpers.constraints.DoConstraintsApply;
import holidaysManager.helpers.constraints.HeadOrDeputyHead;
import holidaysManager.helpers.constraints.OneSenior;
import holidaysManager.helpers.constraints.PercentageOnDuty;
import holidaysManager.helpers.constraints.MaxHolidays;
import holidaysManager.helpers.constraints.OneManager;

public class ConstraintHelper {
	// private static List<Constraint> constraints = List.of(, e2, e3, e4);

	public static List<String> brokenContraints(HRequest holidayRequest) {
		List<String> r = new ArrayList<String>();

		Emp emp = holidayRequest.getEmp();

		List<HRequest> requestsByManagers = emp.getDept().getRoleRequests(4,holidayRequest.getDateStart(),holidayRequest.getDateEnd());
		List<HRequest> requestsBySeniors = emp.getDept().getRoleRequests(5,holidayRequest.getDateStart(),holidayRequest.getDateEnd());
		List<HRequest> approvedRequests = emp.getDept().getApprovedRequests();

		if (!DoConstraintsApply.check(holidayRequest)) {
			return r;
		}

		MaxHolidays.check(r, emp, holidayRequest);

		HeadOrDeputyHead.check(r, emp, holidayRequest, approvedRequests);

		OneManager.check(r, emp, holidayRequest, requestsByManagers);

		OneSenior.check(r, emp, holidayRequest, requestsBySeniors);

		PercentageOnDuty.check(r, emp, holidayRequest);

		return r;
	}
}
