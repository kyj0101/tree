package com.vtex.tree.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AttendanceUtil {
	/**
	 * endStr - starStr
	 * @param startStr : 과거 
	 * @param endStr : 미래
	 * @return
	 */
	public static double getTimeDifference(String startStr, String endStr) {		
		
		try {
			
			SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
			Date startDate = timeFormat.parse(startStr);
			Date endDate = timeFormat.parse(endStr);
			
			long start = startDate.getTime();
			long end  = endDate.getTime();
			
			return end - start;
			
		} catch (ParseException e) {
			
			e.printStackTrace();
			
			return 0;
		}
		
	}
}
