package holidayBooking.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.time.LocalDateTime;

import holidayBooking.models.Employee;
import holidayBooking.models.HolidayRequest;


import holidayBooking.services.HolidayRequestService;

// TODO add constrain business logic here

public class ConstraintBean {
	
	
  public static  List<String> brokenContraints(HolidayRequest holidayRequest) {		
	  List<String> reasons = new ArrayList<String>();
	  Employee e = holidayRequest.getEmployee();
    // 1. Has consumed yearly holidays
   
	  if (e.getHolidayBookings().size() >= e.getRemainingHolidays()) {
      reasons.add("used up all holidays.");
    }
    else if (holidayRequest.getDuration() > e.getRemainingHolidays()) {
    	reasons.add("Not Enough Holidays Remaining.");
    }
   
    
    List<HolidayRequest> hr_list = holidayRequest.getEmployee().getDepartment().getAllRequests();
    if(holidayRequest.getEmployee().getRole().getId() == 2 ) {    	
    	for (int i=0 ; i<hr_list.size() ;i++) {
    		HolidayRequest hr = hr_list.get(i);
    		if(hr.getEmployee().getId() != holidayRequest.getEmployee().getId()) {
	    		if(hr.getEmployee().getRole().getId() == 1 && hr.getEmployee().getDepartment().getId() == holidayRequest.getEmployee().getDepartment().getId()) {
	    			if(hr.getDateStart().compareTo(holidayRequest.getDateStart()) >= 0 && hr.getDateStart().compareTo(holidayRequest.getDateEnd()) < 0 ) {
	    			reasons.add("Deapartment Head is aleady on holiday");
	    			}
	    			else if(holidayRequest.getDateStart().compareTo(hr.getDateStart()) >= 0 && holidayRequest.getDateStart().compareTo(hr.getDateEnd()) < 0 ) {
	        			reasons.add("Deapartment Head is aleady on holiday");
	        		}
	    		}
    		}
    	}
    }
    if(holidayRequest.getEmployee().getRole().getId() == 1) {    	
    	for (int i=0 ; i<hr_list.size() ;i++) {
    		HolidayRequest hr = hr_list.get(i);
    		if(hr.getEmployee().getId() != holidayRequest.getEmployee().getId()) {
    			if(hr.getEmployee().getRole().getId() == 2  && hr.getEmployee().getDepartment().getId() == holidayRequest.getEmployee().getDepartment().getId()) {
	    			if(hr.getDateStart().compareTo(holidayRequest.getDateStart()) >= 0 && hr.getDateStart().compareTo(holidayRequest.getDateEnd()) < 0 ) {
	    				reasons.add("Deapartment Deputy Head is aleady on holiday");
	    			}
	    			else if(holidayRequest.getDateStart().compareTo(hr.getDateStart()) >= 0 && holidayRequest.getDateStart().compareTo(hr.getDateEnd()) < 0 ) {
	        			reasons.add("Deapartment Deputy Head is aleady on holiday");
	        		}
	    		}
    		}
    	}
    }
    
    
    
   // for (LocalDateTime date = holidayRequest.getDateStart(); date.isBefore(holidayRequest.getDateEnd()); date = date.plusDays(1))
   // {}
    return reasons;
  }
  
  
}

