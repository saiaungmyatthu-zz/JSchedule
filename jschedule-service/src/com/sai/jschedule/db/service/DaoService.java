package com.sai.jschedule.db.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;

import com.sai.jschedule.bean.ShiftType;
import com.sai.jschedule.bean.ShiftGroup;
import com.sai.jschedule.bean.ScheduleItem;
import com.sai.jschedule.common.ConnectionFactory;
import com.sai.jschedule.db.mapper.ScheduleMapper;

public class DaoService {

	public List<ShiftType> getAllShiftType(){
		SqlSession session = ConnectionFactory.getInstance().openSession();
		try{
			ScheduleMapper dao = session.getMapper(ScheduleMapper.class);
			return dao.getAllShit();
		}finally{
			session.close();
		}
	}

	public List<ShiftGroup> getAllShiftGroups(){
		SqlSession session = ConnectionFactory.getInstance().openSession();
		try{
			ScheduleMapper dao = session.getMapper(ScheduleMapper.class);
			return dao.getAllGroups();
		}finally{
			session.close();
		}
	}

	public List<ScheduleItem> getAllSchedules(){
		SqlSession session = ConnectionFactory.getInstance().openSession();
		try{
			ScheduleMapper dao = session.getMapper(ScheduleMapper.class);
			return dao.getAllSchedules();
		}finally{
			session.close();
		}
	}

	public int doesShiftBelongToGroup(ScheduleItem item){
		SqlSession session = ConnectionFactory.getInstance().openSession();
		try{
			ScheduleMapper dao = session.getMapper(ScheduleMapper.class);
			return dao.doesShiftBelongToGroup(item);
		}finally{
			session.close();
		}
	}

	private boolean clearTables() {
		SqlSession session = ConnectionFactory.getInstance().openSession(ExecutorType.BATCH);
		boolean flag = false;
		try {
			ScheduleMapper dao = session.getMapper(ScheduleMapper.class);
			dao.clearSchedule();
			dao.clearGroups();
			dao.clearTypes();
			session.commit();
			flag = true;
		}catch(Exception e){
			e.printStackTrace();
			session.rollback();
		}finally {
			session.close();
		}
		return flag;
	}

	private int importShiftTypes(List<ShiftType> typeList) {
		SqlSession session = ConnectionFactory.getInstance().openSession();
		int count = 0;
		try {
			if(typeList != null && !typeList.isEmpty()) {
				ScheduleMapper dao = session.getMapper(ScheduleMapper.class);
				count = dao.batchInsertTypes(typeList);
				if(count > 0) session.commit();
				else session.rollback();
			}
		}catch(Exception e){
			e.printStackTrace();
			session.rollback();
		}finally {
			session.close();
		}
		return count;
	}

	private int importShiftGroups(List<ShiftGroup> groupList) {
		SqlSession session = ConnectionFactory.getInstance().openSession();
		int count = 0;
		try {
			if(groupList != null && !groupList.isEmpty()) {
				ScheduleMapper dao = session.getMapper(ScheduleMapper.class);
				count = dao.batchInsertGroups(groupList);
			}
		}catch(Exception e){
			e.printStackTrace();
			session.rollback();
		}finally{
			if(count > 0) session.commit();
			else session.rollback();
			session.close();
		}
		return count;
	}

	public int importData(List<ScheduleItem> data){
		SqlSession session = ConnectionFactory.getInstance().openSession();
		int count = 0;
		try{
			if(data != null && !data.isEmpty()){
				HashSet<ShiftType> shiftTypes = new LinkedHashSet<>();
				HashSet<ShiftGroup> shiftGroups = new LinkedHashSet<>();
				data.stream().forEach(d -> {
					shiftTypes.addAll(d.getTypeList());
					shiftGroups.addAll(d.getGroupList());
				});
				boolean isCleared = clearTables();
				if(isCleared && shiftTypes != null && !shiftTypes.isEmpty()) {
					int typeCount = importShiftTypes(new ArrayList<ShiftType>(shiftTypes));

					if(typeCount > 0 && shiftGroups != null && !shiftGroups.isEmpty()) {
						int groupCount = importShiftGroups(new ArrayList<ShiftGroup>(shiftGroups));

						if(groupCount > 0) {
							ScheduleMapper dao = session.getMapper(ScheduleMapper.class);
							count = dao.batchInsertSchedule(data);
							if(count > 0) session.commit();
							else session.rollback();
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			session.rollback();
		}finally{
			session.close();
		}
		return count;
	}
}
