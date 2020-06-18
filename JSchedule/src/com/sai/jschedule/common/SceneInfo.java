package com.sai.jschedule.common;

public enum SceneInfo {
	MAIN("/layout/main.fxml"),
	TMPL_MSG_DIALOG("/layout/messagebox.fxml");

	private SceneInfo(String layoutName){
		this.layoutName = layoutName;
	}

	private String layoutName;
	public String getLayoutName(){
		return this.layoutName;
	}
}
