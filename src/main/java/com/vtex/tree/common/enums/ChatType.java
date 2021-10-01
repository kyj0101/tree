package com.vtex.tree.common.enums;

public enum ChatType {
	
	NOTICE("notice"),
	CHAT("chat");
	
	public final String type;
	
	ChatType(String type){
		this.type = type;
	}
	
	public String getType() {
		return type;
	}

}
