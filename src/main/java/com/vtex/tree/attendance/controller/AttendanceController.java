package com.vtex.tree.attendance.controller;

import static com.vtex.tree.common.util.PageBar.getPageBar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vtex.tree.attendance.service.AttendanceService;
import com.vtex.tree.attendance.vo.AttendanceVO;
import com.vtex.tree.common.util.AttendanceUtil;
import com.vtex.tree.member.vo.MemberVO;


@RequestMapping("/attendance")
@Controller
public class AttendanceController {
	
	private final int NUMPERPAGE = 10;
	
	@Value("${working.time}")
	private String workingTime;
	
	@Autowired
	private AttendanceService attendanceService;
	
	@RequestMapping("/list")
	public String getAttendanceList(@RequestParam(required = false)String startDay,
									@RequestParam(required = false)String endDay,
									@RequestParam(required = false)String name,
									@RequestParam(required = false)String lateness,
									@RequestParam(defaultValue = "1")int cPage,
									HttpServletRequest request,
									Model model) throws Exception {
		
		Map<String, Object> param = new HashMap<>();

		param.put("numPerPage", NUMPERPAGE);
		param.put("cPage", cPage);
		
		int offset = (cPage - 1) * NUMPERPAGE;		
		RowBounds rowBounds = new RowBounds(offset, NUMPERPAGE);
		
		int totalContents = attendanceService.getAttendanceListCnt();
		String url = request.getRequestURI();
		String pageBar = getPageBar(totalContents, cPage, NUMPERPAGE, url);
		
		List<AttendanceVO> attendanceList = attendanceService.getAttendanceList(param, rowBounds);
		model.addAttribute("attendanceList", attendanceList);
		model.addAttribute("pageBar", pageBar);
		
		return "manager/attendance";
	}
	
	@ResponseBody
	@RequestMapping("/in")
	public String insertIn(HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession();
		MemberVO member = (MemberVO)session.getAttribute("loginMember");
		
		SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		Calendar calendar = Calendar.getInstance();
		Date now = new Date(calendar.getTimeInMillis());
		
		String day = dayFormat.format(now);
		String time = timeFormat.format(now);
		String latenessAt = AttendanceUtil.getTimeDifference(time, workingTime) > 0 ? "N" : "Y";

		AttendanceVO attendance = new AttendanceVO(member.getEmail(), day, time, null, latenessAt, null, member.getEmail(), null, member.getEmail(), "N", null, null);
		int resultCnt = attendanceService.insertIn(attendance);
		
		if(resultCnt > 0) {
			return "ok";
		}else {
			return "fail";
		}
	}
	
	@ResponseBody
	@RequestMapping("/out")
	public String updateOut(HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession();
		MemberVO member = (MemberVO)session.getAttribute("loginMember");
		
		SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		Calendar calendar = Calendar.getInstance();
		Date now = new Date(calendar.getTimeInMillis());
		
		String day = dayFormat.format(now);
		String outTime = timeFormat.format(now);
		
		Map<String, Object> param = new HashMap<>();
		
		param.put("day", day);
		param.put("outTime", outTime);
		param.put("email", member.getEmail());
		
		int resultCnt = attendanceService.updateOut(param);
		
		if(resultCnt > 0) {
			return "ok";
		}else {
			return "fail";
		}
	}

}
