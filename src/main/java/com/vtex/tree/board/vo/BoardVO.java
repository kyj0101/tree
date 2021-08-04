package com.vtex.tree.board.vo;

import java.sql.Date;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardVO {
	private int boardNo;
	private int categoryNo;
	private String noticeAt;
	private String email;
	private int boardView;
	private Date boardDate;
	private String boardTitle;
	private String boardContent;
	private Date frstRegistPnttm;
	private String frstRegisterId;
	private Date lastUpdtPnttm;
	private String lastUpdusrId;
	private String deleteAt;
	private int fileId;
	private String name;
	private String topAt;
	private int totcnt;
	private int rank;
}
