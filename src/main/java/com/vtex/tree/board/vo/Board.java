package com.vtex.tree.board.vo;

import java.sql.Date;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Board {
	
	private int boardNo;
	private int categoryNo;
	private String noticeAt;
	private String email;
	private int boardView;
	private Date boardDate;
	private String boardContent;
	private Date frstRegistPnttm;
	private String frstRegisterId;
	private Date lastUpdtPnttm;
	private String lastUpdusrId;
	private String deleteAt;
	
}
