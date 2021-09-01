package com.vtex.tree.project.vo;

import java.sql.Date;
import java.util.List;

import com.vtex.tree.category.board.vo.CategoryBoardVO;
import com.vtex.tree.chat.vo.ChatRoomVO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Setter
@Getter
public class ProjectVO {
	
	private String projectId;
	private String projectNm;
	private String projectManager;
	private Date frstRegistPnttm;
	private String frstRegisterId;
	private Date lastUpdtPnttm;
	private String lastUpdusrId;
	private String useAt;
	private String startDate;
	private String endDate;
	private String note;
	private String projectManagerNm;
	private List<CategoryBoardVO> categoryBoardList;
	private List<ChatRoomVO> chatRoomList;
}
