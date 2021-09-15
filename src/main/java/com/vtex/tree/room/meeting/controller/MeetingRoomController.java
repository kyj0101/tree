package com.vtex.tree.room.meeting.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vtex.tree.room.meeting.vo.MeetingRoomVO;


@ResponseBody
@RequestMapping("/room/meeting")
@PreAuthorize("hasRole('ROLE_USER')")
@Controller
public class MeetingRoomController {
	
	
	@RequestMapping("/add")
	public String addMeetingRoom(MeetingRoomVO meetingRoom) {
		System.out.println(meetingRoom.toString());
		int result = 0;
		return result > 0 ? "ok" : "fail"; 
	} 
}
