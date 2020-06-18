package com.sai.jschedule.bean;

public class ShiftType {
	private int id;
	private String code;
	private String name;
	private String duration;
	private String colorCode;

	public ShiftType(int id, String code, String name, String duration, String colorCode) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.duration = duration;
		this.colorCode = colorCode;
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
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getColorCode() {
		return colorCode;
	}
	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}

	@Override
	public String toString() {
		return String.format("ShiftType [id=%s, code=%s, name=%s, duration=%s, colorCode=%s]", id, code, name, duration,
				colorCode);
	}
}
