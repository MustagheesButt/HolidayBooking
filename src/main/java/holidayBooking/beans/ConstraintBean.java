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

		List<HolidayRequest> hr_all = holidayRequest.getEmployee().getDepartment().getAllRequests();

		List<HolidayRequest> hr_approved = holidayRequest.getEmployee().getDepartment().getAprrovedRequests();

		List<HolidayRequest> hr_m = holidayRequest.getEmployee().getDepartment().getRoleRequests(3);

		List<HolidayRequest> hr_s = holidayRequest.getEmployee().getDepartment().getRoleRequests(4);

		// 1. Has consumed yearly holidays

		if (e.getHolidayBookings().size() >= e.getRemainingHolidays()) {
			reasons.add("used up all holidays.");
		}
		// 2. Request Limit Exceeds Remaining Holidays
		else if (holidayRequest.getDuration() > e.getRemainingHolidays()) {
			reasons.add("Not Enough Holidays Remaining.");
		}

		// 3. Department Head is on Holiday. Deputy head has to be on duty.

		if (holidayRequest.getEmployee().getRole().getId() == 2) {
			for (int i = 0; i < hr_approved.size(); i++) {
				HolidayRequest hr = hr_approved.get(i);
				if (hr.getEmployee().getId() != holidayRequest.getEmployee().getId()) {
					if (hr.getEmployee().getRole().getId() == 1
							&& hr.getEmployee().getDepartment().getId() == holidayRequest.getEmployee().getDepartment().getId()) {
						if (hr.getDateStart().compareTo(holidayRequest.getDateStart()) >= 0
								&& hr.getDateStart().compareTo(holidayRequest.getDateEnd()) < 0) {
							reasons.add("Deapartment Head is aleady on holiday");
						} else if (holidayRequest.getDateStart().compareTo(hr.getDateStart()) >= 0
								&& holidayRequest.getDateStart().compareTo(hr.getDateEnd()) < 0) {
							reasons.add("Deapartment Head is aleady on holiday");
						}
					}
				}
			}
		}
		// 4. Department Deputy Head is on Holiday. Head has to be on duty.
		if (holidayRequest.getEmployee().getRole().getId() == 1) {
			for (int i = 0; i < hr_approved.size(); i++) {
				HolidayRequest hr = hr_approved.get(i);
				if (hr.getEmployee().getId() != holidayRequest.getEmployee().getId()) {
					if (hr.getEmployee().getRole().getId() == 2
							&& hr.getEmployee().getDepartment().getId() == holidayRequest.getEmployee().getDepartment().getId()) {
						if (hr.getDateStart().compareTo(holidayRequest.getDateStart()) >= 0
								&& hr.getDateStart().compareTo(holidayRequest.getDateEnd()) < 0) {
							reasons.add("Department Deputy Head is aleady on holiday");
						} else if (holidayRequest.getDateStart().compareTo(hr.getDateStart()) >= 0
								&& holidayRequest.getDateStart().compareTo(hr.getDateEnd()) < 0) {
							reasons.add("Department Deputy Head is aleady on holiday");
						}
					}
				}
			}
		}

		// At least 1 Manager must be on duty
		if (holidayRequest.getEmployee().getRole().getId() == 3) {
			List<HolidayRequest> Temp_hr = new ArrayList<HolidayRequest>();

			for (HolidayRequest h : hr_m) {
				if (Temp_hr.isEmpty()) {
					Temp_hr.add(h);
				}
				for (HolidayRequest t : Temp_hr) {
					if (h.getEmployee().getId() != t.getEmployee().getId()) {
						Temp_hr.add(h);
					}
				}
			}

			for (int i = 0; i < hr_all.size(); i++) {
				HolidayRequest hr = hr_m.get(i);
				if (hr.getEmployee().getRole().getId() == 3) {
				}

			}
		}

		// remove this. not an actual constraint. just here for testing
		if (ConstraintBean.isPeakTime(holidayRequest.getDateStart()) || ConstraintBean.isPeakTime(holidayRequest.getDateEnd())) {
			reasons.add("at peak time");
		}

		return reasons;
	}

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
