package holidayBooking.beans;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import holidayBooking.models.Employee;
import holidayBooking.models.HolidayRequest;

public class ConstraintBean {

	public static List<String> brokenContraints(HolidayRequest holidayRequest) {
		List<String> reasons = new ArrayList<String>();
		Employee e = holidayRequest.getEmployee();

		List<HolidayRequest> hr_approved = holidayRequest.getEmployee().getDepartment().getAprrovedRequests();

		List<HolidayRequest> hr_m = holidayRequest.getEmployee().getDepartment().getRoleRequests(3,holidayRequest.getDateStart(),holidayRequest.getDateEnd());

		List<HolidayRequest> hr_s = holidayRequest.getEmployee().getDepartment().getRoleRequests(4,holidayRequest.getDateStart(),holidayRequest.getDateEnd());

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
		if (e.getHolidayBookings().size() >= e.getRemainingHolidays()) {
			reasons.add("used up all holidays.");
		}
		// 2. Request Limit Exceeds Remaining Holidays
		else if (holidayRequest.getDuration() > e.getRemainingHolidays()) {
			reasons.add("Not Enough Holidays Remaining.");
		}

		// 3. Department Head is on Holiday. Deputy head has to be on duty.
		if (holidayRequest.getEmployee().isDeputyHeadOfDept()) {
			if (isHeadOnHoliday(holidayRequest, hr_approved)) {
				reasons.add("Department Head is already on holiday");
			}
		}

		// 4. Department Deputy Head is on Holiday. Head has to be on duty.
		if (holidayRequest.getEmployee().isHeadOfDept()) {
			if (isDeputyHeadOnHoliday(holidayRequest, hr_approved)) {
				reasons.add("Department Deputy Head is already on holiday");
			}
		}

		// At least 1 Manager must be on duty
		if (holidayRequest.getEmployee().isManager()) {
			if (isNoOtherManagerOnDuty(holidayRequest, hr_m)) {
				reasons.add("Atleast one manager must be on duty");
			}
		}

		// At least one Senior Staff must be on duty
		if (holidayRequest.getEmployee().isSeniorStaff()) {
			if (isNoOtherSeniorStaffOnDuty(holidayRequest, hr_s)) {
				reasons.add("Atleast one senior staff must be on duty");
			}
		}
		
		/**
		 * 60% of department must be on duty (40% during certain times)
		 **/ 
		List<Employee> em = holidayRequest.getEmployee().getDepartment().getEmployees();
		
		// to store only 1 request of an employee in a specific time period (of which HolidayRequest we are compairing against)
		List<HolidayRequest> uniqueRequests = new ArrayList<HolidayRequest>();
		List<HolidayRequest> hr_temp = holidayRequest.getEmployee()
			.getDepartment()
			.getAprrovedRequests(holidayRequest.getDateStart(), holidayRequest.getDateEnd());
		for (HolidayRequest h : hr_temp) {
			if (uniqueRequests.isEmpty()) {
				uniqueRequests.add(h);
			}

			boolean exists = false;
			for (HolidayRequest t : uniqueRequests) {
				if (h.getEmployee().getId() == t.getEmployee().getId()) {
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

	public static boolean isHeadOnHoliday(HolidayRequest reqToCheck, List<HolidayRequest> approvedRequests) {
		for (int i = 0; i < approvedRequests.size(); i++) {
			HolidayRequest hr = approvedRequests.get(i);
			if (hr.getEmployee().getId() != reqToCheck.getEmployee().getId()) {
				if (hr.getEmployee().isHeadOfDept()
						&& hr.getEmployee().getDepartment().getId() == reqToCheck.getEmployee().getDepartment().getId()) {
					if (hr.getDateStart().compareTo(reqToCheck.getDateStart()) >= 0
							&& hr.getDateStart().compareTo(reqToCheck.getDateEnd()) < 0) {
						// reasons.add("Department Head is already on holiday");
						return true;
					} else if (reqToCheck.getDateStart().compareTo(hr.getDateStart()) >= 0
							&& reqToCheck.getDateStart().compareTo(hr.getDateEnd()) < 0) {
						// reasons.add("Department Head is already on holiday");
						return true;
					}
				}
			}
		}

		return false;
	}

	public static boolean isDeputyHeadOnHoliday(HolidayRequest reqToCheck, List<HolidayRequest> approvedRequests) {
		for (int i = 0; i < approvedRequests.size(); i++) {
			HolidayRequest hr = approvedRequests.get(i);
			if (hr.getEmployee().getId() != reqToCheck.getEmployee().getId()) {
				if (hr.getEmployee().isDeputyHeadOfDept()
						&& hr.getEmployee().getDepartment().getId() == reqToCheck.getEmployee().getDepartment().getId()) {
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

	public static boolean isNoOtherManagerOnDuty(HolidayRequest reqToCheck, List<HolidayRequest> managerRequests) {
		List<HolidayRequest> Temp_hr = new ArrayList<HolidayRequest>();
		List<Employee> em = reqToCheck.getEmployee().getDepartment().getRoleSpecific(3);
		if(em.size()>1) {
			for (HolidayRequest h : managerRequests) {
				if (Temp_hr.isEmpty()) {						
					Temp_hr.add(h);
				}
				boolean exists = false;
				for (HolidayRequest t : Temp_hr) {
					if (h.getEmployee().getId() == t.getEmployee().getId()) {
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

	public static boolean isNoOtherSeniorStaffOnDuty(HolidayRequest reqToCheck, List<HolidayRequest> seniorStaffRequests) {
		List<HolidayRequest> Temp_hr = new ArrayList<HolidayRequest>();
		List<Employee> em = reqToCheck.getEmployee().getDepartment().getRoleSpecific(4);
		if(em.size()>1) {
			for (HolidayRequest h : seniorStaffRequests) {
				if (Temp_hr.isEmpty()) {
					Temp_hr.add(h);
				}
				boolean exists = false;
				for (HolidayRequest t : Temp_hr) {
					if (h.getEmployee().getId() == t.getEmployee().getId()) {
						exists = true;
					}
				}
				if(exists==false) {
					Temp_hr.add(h);
				}
			}								
				if(em.size() == Temp_hr.size()+1 ) 
				{									
					// reasons.add("Atleast One Senior Staff must be on duty");
					return true;
				}
		}
		else{
			// reasons.add("Atleast One Senior Staff must be on duty");
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
