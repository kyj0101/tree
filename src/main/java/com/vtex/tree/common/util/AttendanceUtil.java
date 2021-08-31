package com.vtex.tree.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.vtex.tree.attendance.vo.AttendanceVO;

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
	
	
	public static String updateDayFormat(String day, SimpleDateFormat simpleDateFormat){
		
		try {
			
			SimpleDateFormat originalFormat = new SimpleDateFormat("yyyyMMdd");
			Date formatDay = originalFormat.parse(day);
		
			return simpleDateFormat.format(formatDay);

		} catch (ParseException e) {
			
			e.printStackTrace();
			
			return null;
		}
	}
	
	public static String updateInTimeFormat(String time, SimpleDateFormat simpleDateFormat){
		
		try {
			
			SimpleDateFormat originalFormat = new SimpleDateFormat("HHmm");
			Date inTime = originalFormat.parse(time);
		
			return simpleDateFormat.format(inTime);
			
		} catch (ParseException e) {
			 return null;
		
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	public static String updateOutTimeFormat(String time, SimpleDateFormat simpleDateFormat){
		
		try {
			
			SimpleDateFormat originalFormat = new SimpleDateFormat("HHmm");
			Date outTime = originalFormat.parse(time);
			
			return simpleDateFormat.format(outTime);
			
		} catch (ParseException e) {
			return null;
		
		} catch (NullPointerException e) {
			return null;
		}
	}
}
