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
    
    if(holidayRequest.getEmployee().getRole().getId() == 2 ) {    	
    	for (int i=0 ; i<hr_approved.size() ;i++) {
    		HolidayRequest hr = hr_approved.get(i);
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
    // 4. Department Deputy Head is on Holiday. Head has to be on duty.
    if(holidayRequest.getEmployee().getRole().getId() == 1) {    	
    	for (int i=0 ; i<hr_approved.size() ;i++) {
    		HolidayRequest hr = hr_approved.get(i);
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
    
    //At least 1 Manager must be on duty
    if(holidayRequest.getEmployee().getRole().getId() == 3 ) {
    	List<HolidayRequest> Temp_hr = new ArrayList<HolidayRequest>();
    	
    	for (HolidayRequest h : hr_m) {
    		if(Temp_hr.isEmpty()) {
    			Temp_hr.add(h);
    		}
    		for(HolidayRequest t : Temp_hr ) {
    			if(h.getEmployee().getId()!=t.getEmployee().getId()) {
    				Temp_hr.add(h);
    			}
    		}
    	}
    	
   	
    	
    	for(int i=0;i<hr_all.size();i++) {
    		HolidayRequest hr = hr_m.get(i);
    		if(hr.getEmployee().getRole().getId()==3) {    			
    			}
    		
    		}
    	
    	
    }
    return reasons;
  }
  
  
}

