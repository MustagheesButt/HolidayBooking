package holidaysManager.helpers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import holidaysManager.entities.Emp;
import holidaysManager.entities.HRequest;

public class ConstraintBean {

	public static List<String> brokenContraints(HRequest holidayRequest) {
		List<String> reasons = new ArrayList<String>();
		Emp emp = holidayRequest.getEmp();

		List<HRequest> hr_approved = emp.getDept().getAprrovedRequests();

		List<HRequest> requestsByManagers = emp.getDept().getRoleRequests(4,holidayRequest.getDateStart(),holidayRequest.getDateEnd());

		List<HRequest> requestsBySeniors = emp.getDept().getRoleRequests(5,holidayRequest.getDateStart(),holidayRequest.getDateEnd());

    // No constraints from December 23rd to January 3rd.
    if(holidayRequest.getDateStart().getMonthValue() == 12 &&  holidayRequest.getDateStart().getDayOfMonth() >= 23) {
			if (holidayRequest.getDateEnd().getMonthValue() == 1 && holidayRequest.getDateEnd().getDayOfMonth() <= 3){
				return reasons;
			}	
			else if( (holidayRequest.getDateEnd().getMonthValue() == 12 && holidayRequest.getDateEnd().getDayOfMonth() >= 23 ) ) {
				return reasons;
			}
    }
		else if((holidayRequest.getDateStart().getMonthValue() == 1) && ( holidayRequest.getDateStart().getDayOfMonth() >= 1 && holidayRequest.getDateEnd().getDayOfMonth() <= 4 ) ) {
			return reasons;
		}

		// 1. Has consumed yearly holidays
		if (emp.getHolidayBookings().size() >= emp.getRemainingHolidays()) {
			reasons.add("used up all holidays.");
		}
		// 2. Request Limit Exceeds Remaining Holidays
		else if (holidayRequest.getDuration() > emp.getRemainingHolidays()) {
			reasons.add("Not Enough Holidays Remaining.");
		}

		// 3. Department Head is on Holiday. Deputy head has to be on duty.
		if (holidayRequest.getEmp().isDeputyHeadOfDept()) {
			if (isHeadOnHoliday(holidayRequest, hr_approved)) {
				reasons.add("Department Head is already on holiday");
			}
		}

		// 4. Department Deputy Head is on Holiday. Head has to be on duty.
		if (emp.isHeadOfDept()) {
			if (isDeputyHeadOnHoliday(holidayRequest, hr_approved)) {
				reasons.add("Department Deputy Head is already on holiday");
			}
		}

		// At least 1 Manager must be on duty
		if (emp.isManager()) {
			if (isNoOtherManagerOnDuty(holidayRequest, requestsByManagers)) {
				reasons.add("Atleast one manager must be on duty");
			}
		}

		// At least one Senior Staff must be on duty
		if (emp.isSeniorStaff()) {
			if (isNoOtherSeniorStaffOnDuty(holidayRequest, requestsBySeniors)) {
				reasons.add("Atleast one senior staff must be on duty");
			}
		}
		
		/**
		 * 60% of department must be on duty (40% during certain times)
		 **/ 
		List<Emp> em = holidayRequest.getEmp().getDept().getEmployees();
		
		// to store only 1 request of an employee in a specific time period (of which HolidayRequest we are compairing against)
		List<HRequest> uniqueRequests = new ArrayList<HRequest>();
		List<HRequest> hr_temp = emp
			.getDept()
			.getAprrovedRequests(holidayRequest.getDateStart(), holidayRequest.getDateEnd());
		for (HRequest h : hr_temp) {
			if (uniqueRequests.isEmpty()) {
				uniqueRequests.add(h);
			}

			boolean exists = false;
			for (HRequest t : uniqueRequests) {
				if (h.getEmp().getId() == t.getEmp().getId()) {
					exists = true;
				}
			}

			if(!exists)
				uniqueRequests.add(h);
		}

		// to calculate percentage of employees on holiday
		int em_duty = em.size() - uniqueRequests.size() - 1;
		if (holidayRequest.getDateStart().getMonthValue() == 8 || holidayRequest.getDateEnd().getMonthValue()== 8) {
			if((float)em_duty/em.size()*100 < 40.0) {					
				reasons.add("At least 40% department must be on duty in August");	
			}
		}
		else if ((float)em_duty/em.size()*100 < 60.0) {					
			reasons.add("At least 60% department must be on duty");	
		}

		return reasons;
	}

	public static boolean isHeadOnHoliday(HRequest reqToCheck, List<HRequest> approvedRequests) {
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

	public static boolean isDeputyHeadOnHoliday(HRequest reqToCheck, List<HRequest> approvedRequests) {
		for (int i = 0; i < approvedRequests.size(); i++) {
			HRequest hr = approvedRequests.get(i);
			if (hr.getEmp().getId() != reqToCheck.getEmp().getId()) {
				if (hr.getEmp().isDeputyHeadOfDept()
						&& hr.getEmp().getDept().getId() == reqToCheck.getEmp().getDept().getId()) {
					if (hr.getDateStart().compareTo(reqToCheck.getDateStart()) >= 0
							&& hr.getDateStart().compareTo(reqToCheck.getDateEnd()) < 0) {
						// reasons.add("Department Deputy Head is aleady on holiday");
						return true;
					} else if (reqToCheck.getDateStart().compareTo(hr.getDateStart()) >= 0
							&& reqToCheck.getDateStart().compareTo(hr.getDateEnd()) < 0) {
						// reasons.add("Department Deputy Head is aleady on holiday");
						return true;
					}
				}
			}
		}

		return false;
	}

	public static boolean isNoOtherManagerOnDuty(HRequest reqToCheck, List<HRequest> managerRequests) {
		List<HRequest> Temp_hr = new ArrayList<HRequest>();
		List<Emp> em = reqToCheck.getEmp().getDept().getRoleSpecific(3);
		if(em.size()>1) {
			for (HRequest h : managerRequests) {
				if (Temp_hr.isEmpty()) {						
					Temp_hr.add(h);
				}
				boolean exists = false;
				for (HRequest t : Temp_hr) {
					if (h.getEmp().getId() == t.getEmp().getId()) {
						exists = true;
					}
				}
				if(exists==false) {
					Temp_hr.add(h);
				}
			}	
				if(em.size() == Temp_hr.size()+1 ) 
				{									
					// reasons.add("Atleast One manager must be on duty");
					return true;
				}							
		}
		else{
			// reasons.add("Atleast One manager must be on duty");
			return true;
		}

		return false;
	}

	public static boolean isNoOtherSeniorStaffOnDuty(HRequest reqToCheck, List<HRequest> seniorStaffRequests) {
		List<HRequest> Temp_hr = new ArrayList<HRequest>();
		List<Emp> emps = reqToCheck.getEmp().getDept().getRoleSpecific(4);
		if (emps.size() > 1) {
			for (HRequest h : seniorStaffRequests) {
				if (Temp_hr.isEmpty()) {
					Temp_hr.add(h);
				}
				boolean exists = false;
				for (HRequest t : Temp_hr) {
					if (h.getEmp().getId() == t.getEmp().getId()) {
						exists = true;
					}
				}
				if (!exists) {
					Temp_hr.add(h);
				}
			}								
				if(emps.size() == Temp_hr.size()+1 ) 
				{
					return true;
				}
		}
		else {
			return true;
		}

		return false;
	}

	// check if a date overlaps with peak time periods
	public static boolean isPeakTime(LocalDateTime dateToTest) {
		if (dateToTest == null)
			dateToTest = LocalDateTime.now();

		// process entries as a pair to read a period. i.e first period is 0 and 1 entry
		// of array
		List<LocalDateTime> periods = Arrays.asList(
				LocalDateTime.of(dateToTest.getYear(), 7, 15, 0, 1), LocalDateTime.of(dateToTest.getYear(), 8, 31, 23, 59),
				LocalDateTime.of(dateToTest.getYear(), 12, 15, 0, 1), LocalDateTime.of(dateToTest.getYear(), 12, 22, 23, 59),
				ConstraintBean.getEasterSundayDate(dateToTest.getYear()).minusDays(7), ConstraintBean.getEasterSundayDate(dateToTest.getYear()).plusDays(7)
			);

		for (int i = 0; i < periods.size(); i += 2) {
			if (dateToTest.compareTo(periods.get(i)) >= 0 && dateToTest.compareTo(periods.get(i + 1)) <= 0) {
				return true;
			}
		}

		return false;
	}

	// Courtesy of https://stackoverflow.com/questions/26022233/calculate-the-date-of-easter-sunday
	public static LocalDateTime getEasterSundayDate(int year) {
		int a = year % 19,
				b = year / 100,
				c = year % 100,
				d = b / 4,
				e = b % 4,
				g = (8 * b + 13) / 25,
				h = (19 * a + b - d - g + 15) % 30,
				j = c / 4,
				k = c % 4,
				m = (a + 11 * h) / 319,
				r = (2 * e + 2 * j - k - h + m + 32) % 7,
				n = (h - m + r + 90) / 25,
				p = (h - m + r + n + 19) % 32;

		return LocalDateTime.of(year, n, p, 0, 1);
	}
}
