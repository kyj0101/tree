package com.vtex.tree.category.vo;

import java.sql.Date;

import com.vtex.tree.space.vo.SpaceVO;

import groovy.transform.ToString;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@ToString
@Getter
@Setter
public class CategoryVO extends SpaceVO{
	
	private long categoryNo;
	private String categoryName;

}
