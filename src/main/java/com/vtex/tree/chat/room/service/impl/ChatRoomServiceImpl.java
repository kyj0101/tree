package com.vtex.tree.chat.room.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vtex.tree.chat.room.mapper.ChatRoomMapper;
import com.vtex.tree.chat.room.service.ChatRoomService;
import com.vtex.tree.member.vo.MemberVO;

@Service
public class ChatRoomServiceImpl implements ChatRoomService{

	@Autowired
	private ChatRoomMapper chatRoomMapper;
	
	@Override
	public int insertCategoryChat(Map<String, Object> param) throws Exception {
		return chatRoomMapper.insertChatRoom(param);
	}

	@Override
	public int insertCategoryChatUser(Map<String, Object> param) throws Exception {
		return chatRoomMapper.insertChatRoomMember(param);
	}
	
	@Override
	public List<Map<String, Object>> getChatRoomList(String email) throws Exception {
		return chatRoomMapper.getChatRoomList(email);
	}

	@Override
	public Map<String, Object> getChatRoom(int category) throws Exception {
		return chatRoomMapper.getChatRoom(category);
	}
	
	@Override
	public int outChatRoom(Map<String, Object> param) throws Exception {
		return chatRoomMapper.outChatRoom(param);
	}
	
	@Override
	public int deleteChatRoom(Map<String, Object> param) throws Exception {
		return chatRoomMapper.deleteChatRoom(param);
	}
	
	@Override
	public List<MemberVO> getMemberListToInvite(String categoryNo) throws Exception {
		return chatRoomMapper.getMemberListToInvite(categoryNo);
	}
	
	@Override
	public int insertCategoryChatMember(Map<String, Object> param) throws Exception {
		return chatRoomMapper.insertChatRoomMember(param);
	}
}
