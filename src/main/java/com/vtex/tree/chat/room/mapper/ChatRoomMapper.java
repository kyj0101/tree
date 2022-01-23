package com.vtex.tree.chat.room.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.vtex.tree.member.vo.Member;

@Mapper
public interface ChatRoomMapper {

	int insertChatRoom(Map<String, Object> param) throws Exception;
	
	List<Map<String, Object>> getChatRoomList(String email) throws Exception;
	
	Map<String, Object> getChatRoom(int category) throws Exception;

	int outChatRoom(Map<String, Object> param) throws Exception;

	int deleteChatRoom(Map<String, Object> param) throws Exception;

	List<Member> getMemberListToInvite(String categoryNo) throws Exception;

	int insertChatRoomMember(Map<String, Object> param) throws Exception;
}
