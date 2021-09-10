package com.vtex.tree.chat.service;

import java.util.List;
import java.util.Map;

import com.vtex.tree.member.vo.MemberVO;

public interface ChatService {

	List<MemberVO> getChatMemberList(String categoryNo) throws Exception;

	

}
