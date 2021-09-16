package com.vtex.tree.project.vo;

import java.sql.Date;
import java.util.List;

import com.vtex.tree.category.vo.CategoryVO;
import com.vtex.tree.chat.room.vo.ChatRoomVO;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
	private List<CategoryVO> categoryBoardList;
	private List<ChatRoomVO> chatRoomList;
}
