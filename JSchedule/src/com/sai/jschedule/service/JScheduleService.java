package com.sai.jschedule.service;

import java.util.List;

import com.sai.jschedule.bean.ScheduleItem;
import com.sai.jschedule.bean.ShiftGroup;
import com.sai.jschedule.bean.ShiftType;
import com.sai.jschedule.db.service.DaoService;

public class JScheduleService {
	private JScheduleService(){}
	private DaoService dao;

	private static class ServiceBuilder{
		private static final JScheduleService INSTANCE = new JScheduleService();
	}

	public static JScheduleService getInstance(){
		return ServiceBuilder.INSTANCE;
	}

	private DaoService getDaoService(){
		if(dao == null) dao = new DaoService();
		return dao;
	}

	public int importData(List<ScheduleItem> dataList){
		return getDaoService().importData(dataList);
	}

	public List<ShiftType> getAllShiftType(){
		return getDaoService().getAllShiftType();
	}

	public List<ShiftGroup> getAllShiftGroups(){
		return getDaoService().getAllShiftGroups();
	}

	public List<ScheduleItem> getAllSchedules(){
		return getDaoService().getAllSchedules();
	}

	public boolean doesShiftBelongToGroup(String shiftCode, String shiftGroupCode){
		ScheduleItem item = new ScheduleItem(shiftCode, shiftGroupCode);
		return getDaoService().doesShiftBelongToGroup(item) > 0? true : false;
	}

	public boolean doesShiftBelongToGroup(int day, String shiftCode, String shiftGroupCode){
		ScheduleItem item = new ScheduleItem(shiftCode, shiftGroupCode);
		item.setDay(day);
		return getDaoService().doesShiftBelongToGroup(item) > 0? true : false;
	}
}
