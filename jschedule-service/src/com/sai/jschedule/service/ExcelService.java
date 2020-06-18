package com.sai.jschedule.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sai.jschedule.bean.ShiftType;
import com.sai.jschedule.bean.ShiftGroup;
import com.sai.jschedule.bean.ScheduleItem;

public class ExcelService {

	private List<ShiftType> loadShiftTypes(Workbook book){
		List<ShiftType> list = new ArrayList<ShiftType>();
		Sheet sheet = book.getSheet("types");
		Iterator<Row> rowItr = sheet.iterator();
		while(rowItr.hasNext()){
			Row row = rowItr.next();
			if(row.getRowNum() == 0) continue;
			ShiftType shift = new ShiftType((int) row.getCell(0).getNumericCellValue(), row.getCell(1).getStringCellValue(),
					row.getCell(2).getStringCellValue(), row.getCell(3).getStringCellValue(), row.getCell(4).getStringCellValue());
			list.add(shift);
		}
		return list;
	}

	private List<ShiftGroup> loadShiftGroups(Workbook book){
		List<ShiftGroup> list = new ArrayList<ShiftGroup>();
		Sheet sheet = book.getSheet("groups");
		Iterator<Row> rowItr = sheet.iterator();
		while(rowItr.hasNext()){
			Row row = rowItr.next();
			if(row.getRowNum() == 0) continue;
			ShiftGroup shiftGroup = new ShiftGroup((int)(row.getCell(0).getNumericCellValue()),
					row.getCell(1).getStringCellValue(), row.getCell(2).getStringCellValue());
			list.add(shiftGroup);
		}
		return list;
	}

	public List<ScheduleItem> loadData(String fileLocation) throws FileNotFoundException, IOException, Exception{
		List<ScheduleItem> dataList  = null;
		File excelFile = new File(fileLocation);
		if(excelFile != null){
			FileInputStream fis = new FileInputStream(excelFile);
			Workbook book = null;
			try{
				String fileType = getFileExtension(excelFile.getName()).get();
				List<ShiftType> typeList = null;
				List<ShiftGroup> groupList = null;
				book = fileType.equalsIgnoreCase("xls")? new HSSFWorkbook(fis) : new XSSFWorkbook(fis);
				typeList = loadShiftTypes(book);
				if(typeList != null && !typeList.isEmpty()) {
					groupList = loadShiftGroups(book);
					if(groupList != null && !groupList.isEmpty()) {
						dataList = new ArrayList<ScheduleItem>();
						Sheet sheet = book.getSheet("data");
						Iterator<Row> rowItr = sheet.iterator();

						while(rowItr.hasNext()){
							Row row = rowItr.next();
							if(row.getRowNum() == 0) continue;
							Iterator<Cell> cellItr = row.cellIterator();
							int id = 0;
							while(cellItr.hasNext()){
								Cell cell = cellItr.next();
								if(cell.getColumnIndex() == 0) {
									id = (int) cell.getNumericCellValue();
								}else {
									String group = sheet.getRow(0).getCell(cell.getColumnIndex()).getStringCellValue();
									String type = cell.getStringCellValue();
									if(!type.equals("") && type.length() > 0) {
										ShiftType shiftType = typeList.stream().filter(t -> t.getName().toLowerCase().equals(type.toLowerCase())).findFirst().get();
										ShiftGroup shiftGroup = groupList.stream().filter(g -> g.getName().toLowerCase().equals(group.toLowerCase())).findFirst().get();
										dataList.add(new ScheduleItem(id, id, shiftType.getCode(), shiftGroup.getCode(), shiftType, shiftGroup));
									}
								}
							}
						}
					}else {
						throw new Exception("Failed! while loading group data");
					}
				}else {
					throw new Exception("Failed! while loading type data");
				}
			}finally{
				if(book != null) book.close();
				fis.close();
			}
		}
		return dataList;
	}

	private Optional<String> getFileExtension(String fileName){
		return Optional.of(fileName).filter(s -> s.contains(".")).map(s -> s.substring(fileName.lastIndexOf(".") + 1));
	}
}
