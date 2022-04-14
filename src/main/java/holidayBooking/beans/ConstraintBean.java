package holidayBooking.beans;

import java.util.ArrayList;
import java.util.List;

import holidayBooking.models.Employee;
import holidayBooking.models.HolidayRequest;

// TODO add constrain business logic here
public class ConstraintBean {
	
  public static List<String> brokenContraints(HolidayRequest holidayRequest) {
	  List<String> reasons = new ArrayList<String>();
	  Employee e = holidayRequest.getEmployee();
    // 1. Has consumed yearly holidays
    if (e.getHolidayBookings().size() >= e.getRemainingHolidays()) {
      reasons.add("used up all holidays.");
    }
    else if (holidayRequest.getDuration() > e.getRemainingHolidays()) {
    	reasons.add("Not Enough Holidays Remaining.");
    }
    return reasons;
  }
  
  
}

