package com.sai.jschedule.common;

public enum ErrorType {
	ERROR("ic_error.png"),
	WARNNING("ic_warning.png"),
	INFOMATION("ic_info.png"),
	OK("ic_ok.png");

	private String iconName;

	private ErrorType(String iconName){
		this.iconName = iconName;
	}

	public String iconName(){
		return this.iconName;
	}
}
