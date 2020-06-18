package com.sai.jschedule.db.mapper;

import java.util.List;

import com.sai.jschedule.bean.ShiftType;
import com.sai.jschedule.bean.ShiftGroup;
import com.sai.jschedule.bean.ScheduleItem;

public interface ScheduleMapper {
	public List<ShiftType> getAllShit();
	public List<ShiftGroup> getAllGroups();
	public List<ScheduleItem> getAllSchedules();
	public List<ShiftType> getTypeByCode(String shift_code);
	public List<ShiftGroup> getGroupByCode(String shift_group_code);

	public int clearSchedule();
	public int clearTypes();
	public int clearGroups();

	public int batchInsertTypes(List<ShiftType> typeList);
	public int batchInsertGroups(List<ShiftGroup> groupList);
	public int batchInsertSchedule(List<ScheduleItem> scheduleList);

	public int doesShiftBelongToGroup(ScheduleItem item);
}
