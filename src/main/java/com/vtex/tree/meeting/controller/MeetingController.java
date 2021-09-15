package com.vtex.tree.meeting.controller;

import static com.vtex.tree.common.util.PageBar.getPageBar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vtex.tree.attendance.service.AttendanceService;
import com.vtex.tree.board.vo.BoardVO;
import com.vtex.tree.category.board.service.CategoryBoardService;
import com.vtex.tree.category.board.vo.CategoryBoardVO;
import com.vtex.tree.category.chat.vo.ChatRoomVO;
import com.vtex.tree.member.vo.MemberVO;
import com.vtex.tree.project.service.ProjectService;
import com.vtex.tree.project.vo.ProjectVO;

@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/meeting")
@Controller
public class MeetingController {
	


	@RequestMapping("/room")
	public String getMeetingRoom() throws Exception {
		

		
		return "meeting/meeting";
	}
}
