package com.vtex.tree.common.enums;

public enum ViewName {
	
	BOARD("board/board"),
	CHAT("chat/chat");
	
	public final String viewName;
	
	ViewName(String viewName) {
		this.viewName = viewName;
	}
	
	public String getViewName() {
		return viewName;
	}

}
