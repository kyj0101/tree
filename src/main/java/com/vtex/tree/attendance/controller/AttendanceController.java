package com.vtex.tree.attendance.controller;

import static com.vtex.tree.common.util.PageBar.getPageBar;
import static com.vtex.tree.common.util.PageBar.getOffset;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vtex.tree.attendance.service.AttendanceService;
import com.vtex.tree.attendance.vo.AttendanceVO;
import com.vtex.tree.common.util.AttendanceUtil;
import com.vtex.tree.member.vo.Member;
import com.vtex.tree.security.annotation.LoginUser;


@RequestMapping("/attendance")
@Controller
@RequiredArgsConstructor
public class AttendanceController {
	
	private final int NUMPERPAGE = 10;
	
	@Value("${working.time}")
	private String workingTime;
	
	@Value("${empty.msg}")
	private String emptyMsg;

	private final AttendanceService attendanceService;
	
	/**
	 * 검색 리스트 
	 * @param startDay : 날짜 검색할 때 시작 날짜
	 * @param endDay : 날짜 검색할 때 종료 날짜
	 * @param name : 이름 또는 이메일
	 * @param latenessAt : 지각 여부
	 * @param cPage : 현재 페이지
	 * @param request 
	 * @param model 
	 * @return
	 * @throws Exception
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/list")
	public String getAttendanceList(@RequestParam(required = false)String startDay,
									@RequestParam(required = false)String endDay,
									@RequestParam(required = false)String name,
									@RequestParam(required = false)String latenessAt,
									@RequestParam(defaultValue = "1")int cPage,
									HttpServletRequest request,
									Model model) throws Exception {
		
		Map<String, Object> param = new HashMap<>();

		param.put("startDay", startDay);
		param.put("endDay", endDay);
		param.put("name", name);
		param.put("latenessAt", latenessAt);

		RowBounds rowBounds = new RowBounds(getOffset(cPage, NUMPERPAGE), NUMPERPAGE);
		
		String setParams = "?startDay=" + startDay + "&endDay=" + endDay + "&name=" + name + "&latenessAt=" + latenessAt;
		int totalContents = attendanceService.getAttendanceListCnt(param);
		String url = request.getRequestURI() + setParams;
		
		String pageBar = getPageBar(totalContents, cPage, NUMPERPAGE, url);
		
		List<AttendanceVO> attendanceList = attendanceService.getAttendanceList(param, rowBounds);
		
		for(AttendanceVO attendance : attendanceList) {
			
			attendance.setDay(AttendanceUtil.updateDayFormat(attendance.getDay(), new SimpleDateFormat("yyyy-MM-dd")));
			attendance.setInTime(AttendanceUtil.updateInTimeFormat(attendance.getInTime(), new SimpleDateFormat("HH:mm")));
			attendance.setOutTime(AttendanceUtil.updateOutTimeFormat(attendance.getOutTime(), new SimpleDateFormat("HH:mm")));
		}
		
		model.addAttribute("attendanceList", attendanceList);
		model.addAttribute("pageBar", pageBar);
		model.addAttribute("param", param);
		model.addAttribute("emptyMsg", emptyMsg);
		
		return "manager/attendance";
	}
	
	
	/**
	 * 근태 출근 처리 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@ResponseBody
	@RequestMapping("/in")
	public String insertIn(HttpServletRequest request, @LoginUser Member member) throws Exception {
		
		HttpSession session = request.getSession();
		
		//출퇴근 여부
		boolean isIn = attendanceService.isIn(member.getEmail()) > 0;
		
		if(isIn) {
			return "fail";
		
		}else {
			
			SimpleDateFormat dayFormat = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat timeFormat = new SimpleDateFormat("HHmm");
			Calendar calendar = Calendar.getInstance();
			Date now = new Date(calendar.getTimeInMillis());
			
			String day = dayFormat.format(now);
			String time = timeFormat.format(now);
			String latenessAt = AttendanceUtil.getTimeDifference(time, workingTime) > 0 ? "N" : "Y";

			AttendanceVO attendance = new AttendanceVO(0,member.getEmail(), day, time, null, latenessAt, null, member.getEmail(), null, member.getEmail(), "N", null, null);
			int resultCnt = attendanceService.insertIn(attendance);
			session.setAttribute("attendanceNo", attendance.getAttendanceNo());
			 
			return "ok";
		}
	}
	
	/**
	 * 근태 퇴근 처리
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@ResponseBody
	@RequestMapping("/out")
	public String updateOut(HttpServletRequest request, @LoginUser Member member) throws Exception {
		
		HttpSession session = request.getSession();
	
		boolean isOut = attendanceService.isOut(member.getEmail()) > 0;

		if(isOut){
			return "fail";
		
		}else {
			
			SimpleDateFormat dayFormat = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat timeFormat = new SimpleDateFormat("HHmm");
			Calendar calendar = Calendar.getInstance();
			Date now = new Date(calendar.getTimeInMillis());
			
			String day = dayFormat.format(now);
			String outTime = timeFormat.format(now);
			int attendanceNo = Integer.parseInt(session.getAttribute("attendanceNo") + "");
			
			Map<String, Object> param = new HashMap<>();
			
			param.put("day", day);
			param.put("outTime", outTime);
			param.put("email", member.getEmail());
			param.put("attendanceNo", attendanceNo);
			
			int resultCnt = attendanceService.updateOut(param);

			return "ok";
		}
	}
	
	/**
	 * 근태 기록 수정
	 * @param attendanceVO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ResponseBody
	@RequestMapping("/update")
	public String updateAttendance(AttendanceVO attendanceVO, @LoginUser Member member) throws Exception {
		
		Map<String, Object> param = new HashMap<>();
		
		param.put("attendanceNo", attendanceVO.getAttendanceNo());
		param.put("day", attendanceVO.getDay().replaceAll("-", ""));
		param.put("inTime", attendanceVO.getInTime().replaceAll(":", ""));
		param.put("outTime", attendanceVO.getOutTime().replaceAll(":", ""));
		param.put("latenessAt", attendanceVO.getLatenessAt());
		param.put("latenessReason", attendanceVO.getLatenessReason());
		param.put("email", attendanceVO.getEmail());
		param.put("adminEmail", member.getEmail());
		
		int resultCnt = attendanceService.updateAttendance(param);
		
		if(resultCnt > 0) {
			return "ok";			

		}else {
			return "fail";
		}
	}
	
	/**
	 * 근태 등록시 이름 자동완성 
	 * @param searchName
	 * @throws Exception
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping("/auto/name")
	public ResponseEntity<List<Member>> autoName(String searchName) throws Exception {

		List<Member> memberList = attendanceService.autoName(searchName);

		return ResponseEntity.ok()
							.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
							.body(memberList);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ResponseBody
	@RequestMapping("/insert")
	public String insertAttendance(AttendanceVO attendanceVO, @LoginUser Member member) throws Exception {
		
		Map<String, Object> param = new HashMap<>();
		
		param.put("attendanceNo", attendanceVO.getAttendanceNo());
		param.put("day", attendanceVO.getDay().replaceAll("-", ""));
		param.put("inTime", attendanceVO.getInTime().replaceAll(":", ""));
		param.put("outTime", attendanceVO.getOutTime().replaceAll(":", ""));
		param.put("latenessAt", attendanceVO.getLatenessAt());
		param.put("latenessReason", attendanceVO.getLatenessReason());
		param.put("email", attendanceVO.getEmail());
		param.put("adminEmail", member.getEmail());
		
		int resultCnt = attendanceService.insertAttendance(param);
		
		if(resultCnt > 0) {
			return "ok";

		}else {
			return "fail";
		}	
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ResponseBody
	@RequestMapping("/delete")
	public String deleteAttendance(int attendanceNo, @LoginUser Member member) throws Exception {
		
		Map<String, Object> param = new HashMap<>();
		
		param.put("email", member.getEmail());
		param.put("attendanceNo", attendanceNo);
		
		int resultCnt = attendanceService.deleteAttendance(param);
		
		if(resultCnt > 0) {
			return "ok";
		
		}else {
			return "fail";
		}
	}

}
