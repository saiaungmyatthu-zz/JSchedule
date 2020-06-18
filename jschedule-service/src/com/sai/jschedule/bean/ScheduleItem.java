package com.sai.jschedule.bean;

import java.util.ArrayList;
import java.util.List;

public class ScheduleItem {
	private int id;
	private int day;
	private String shiftCode;
	private String shiftGroupCode;
	private List<ShiftType> typeList = new ArrayList<ShiftType>();
	private List<ShiftGroup> groupList = new ArrayList<ShiftGroup>();

	public ScheduleItem(int id, int day) {
		super();
		this.id = id;
		this.day = day;
	}

	public ScheduleItem(String shiftCode, String shiftGroupCode){
		super();
		this.shiftCode = shiftCode;
		this.shiftGroupCode = shiftGroupCode;
	}

	public ScheduleItem(int id, int day, String shiftCode, String shiftGroupCode) {
		this(id, day);
		this.shiftCode = shiftCode;
		this.shiftGroupCode = shiftGroupCode;
	}

	public ScheduleItem(int id, int day, List<ShiftType> typeList, List<ShiftGroup> groupList){
		this(id, day);
		this.typeList = typeList;
		this.groupList = groupList;
	}

	public ScheduleItem(int id, int day, String shiftCode, String shiftGroupCode, ShiftType type, ShiftGroup group){
		this(id, day, shiftCode, shiftGroupCode);
		this.typeList.add(type);
		this.groupList.add(group);
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public List<ShiftType> getTypeList() {
		return typeList;
	}
	public void setTypeList(List<ShiftType> typeList) {
		this.typeList = typeList;
	}
	public List<ShiftGroup> getGroupList() {
		return groupList;
	}
	public void setGroupList(List<ShiftGroup> groupList) {
		this.groupList = groupList;
	}
	public String getShiftCode() {
		return shiftCode;
	}

	public void setShiftCode(String shiftCode) {
		this.shiftCode = shiftCode;
	}

	public String getShiftGroupCode() {
		return shiftGroupCode;
	}

	public void setShiftGroupCode(String shiftGroupCode) {
		this.shiftGroupCode = shiftGroupCode;
	}

	@Override
	public String toString() {
		return String.format("ScheduleItem [id=%s, day=%s, shiftCode=%s, shiftGroupCode=%s]", id, day, shiftCode,
				shiftGroupCode);
	}
}
