package com.sai.jschedule.bean;

public class ShiftGroup {
	private int id;
	private String code;
	private String name;

	public ShiftGroup(int id, String code, String name) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return String.format("ShiftGroup [id=%s, code=%s, name=%s]", id, code, name);
	}
}
