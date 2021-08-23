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
	
	
	public static void updateDayFormat(AttendanceVO attendance, SimpleDateFormat simpleDateFormat){
		
		try {
			SimpleDateFormat originalFormat = new SimpleDateFormat("yyyyMMdd");
			Date day = originalFormat.parse(attendance.getDay());
		
			attendance.setDay(simpleDateFormat.format(day));

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public static void updateInTimeFormat(AttendanceVO attendance, SimpleDateFormat simpleDateFormat){
		
		try {
			SimpleDateFormat originalFormat = new SimpleDateFormat("HHmm");
			Date inTime = originalFormat.parse(attendance.getInTime());
		
			attendance.setInTime(simpleDateFormat.format(inTime));
			
		} catch (ParseException e) {
			 
		
		} catch (NullPointerException e) {
			
		}
	}
	
	public static void updateOutTimeFormat(AttendanceVO attendance, SimpleDateFormat simpleDateFormat){
		
		try {
			SimpleDateFormat originalFormat = new SimpleDateFormat("HHmm");
			Date outTime = originalFormat.parse(attendance.getOutTime());
			
			
			attendance.setOutTime(simpleDateFormat.format(outTime));
			
		} catch (ParseException e) {
			
		
		} catch (NullPointerException e) {
			
		}
	}
}
