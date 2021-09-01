package com.vtex.tree.category.board.vo;

import java.sql.Date;

import groovy.transform.ToString;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@ToString
@Getter
@Setter
public class CategoryBoardVO {
	
	private long categoryNo;
	private String categoryName;
	private Date frstRegistPnttm;
	private String frstRegisterId;
	private Date lastUpdtPnttm;
	private String lastUpdusrId;
	private String deleteAt;
	private String projectId;
}
