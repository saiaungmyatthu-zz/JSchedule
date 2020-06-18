package com.sai.jschedule.ctrls;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.sai.jschedule.Main;
import com.sai.jschedule.bean.ScheduleItem;
import com.sai.jschedule.bean.ShiftGroup;
import com.sai.jschedule.bean.ShiftType;
import com.sai.jschedule.common.AppController;
import com.sai.jschedule.common.ErrorType;
import com.sai.jschedule.service.ExcelService;
import com.sai.jschedule.service.JScheduleService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class MainController extends AppController {

    @FXML private JFXButton btnUpload;
    @FXML private JFXButton btnVerify;
    @FXML private JFXTextField txfType;
    @FXML private JFXTextField txfGroup;
    @FXML private JFXComboBox<String> cmbDays;
    @FXML private VBox main;

    private List<ScheduleItem> dataList;
    private List<ShiftGroup> shiftGroups;
    private List<ShiftType> shiftTypes;
    private final FileChooser chooser = new FileChooser();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	ObservableList<String> options = FXCollections.observableArrayList();
    	options.add(0, "=== Select ===");
    	IntStream.range(1, 29).forEach(i->{
    		options.add(i, String.format("Day-%d", i));
    	});
    	cmbDays.setItems(options);
    	cmbDays.getSelectionModel().selectFirst();
    	setupDataTable();
    }

    @FXML
	void verify(ActionEvent e) {
    	String shiftCode = txfType.getText().trim();
    	String shiftGroupCode = txfGroup.getText().trim();

    	if(shiftCode == null || shiftCode.length() == 0){
    		showDialog(ErrorType.ERROR, "Field Required", "The Shift Code value is mandatory for validation");
    		return;
    	}

    	if(shiftGroupCode == null || shiftGroupCode.length() == 0){
    		showDialog(ErrorType.ERROR, "Field Required", "The Shift Group Code value is mandatory for validation");
    		return;
    	}

    	String result = checkValidity(shiftCode, shiftGroupCode);
    	if(result != null) showDialog(ErrorType.ERROR, "Check Validity", result);

    	boolean flag = false;
    	String selectedDay = cmbDays.getValue();
    	if(selectedDay == null || selectedDay.startsWith("="))
    		flag = JScheduleService.getInstance().doesShiftBelongToGroup(shiftCode, shiftGroupCode);
    	else
    		flag = JScheduleService.getInstance().doesShiftBelongToGroup(Integer.parseInt(selectedDay.split("-")[1]), shiftCode, shiftGroupCode);

    	if(flag)
    		showDialog(ErrorType.OK, "Check Validity", String.format("This shift(%s) is belong to group(%s)", shiftCode, shiftGroupCode));
    	else
    		showDialog(ErrorType.ERROR, "Check Validity", String.format("This shift(%s) is not belong to group(%s)", shiftCode, shiftGroupCode));
	}

    private String checkValidity(String shiftCode, String shiftGroupCode) {
    	String result = null;

    	Optional<ShiftType> type = shiftTypes.stream()
    			.filter(t-> (shiftCode.equalsIgnoreCase(t.getCode()) || shiftCode.equalsIgnoreCase(t.getName()))).findAny();
    	Optional<ShiftGroup> group = shiftGroups.stream()
    			.filter(g-> (shiftGroupCode.equalsIgnoreCase(g.getCode()) || shiftGroupCode.equalsIgnoreCase(g.getName()))).findAny();

    	if(!type.isPresent())
    		return "Invalid shift code/name!";
    	if(!group.isPresent())
    		return "Invalid shift group code/name!";
    	return result;
	}

	@FXML
	void upload(ActionEvent e) {
    	String dataPath = System.getProperty("user.dir") + File.separator + "data";
    	chooser.setInitialDirectory(new File(dataPath));
    	chooser.getExtensionFilters().addAll(new ExtensionFilter("Excel Workbook (*.xlsx)", "*.xlsx"));
    	chooser.getExtensionFilters().addAll(new ExtensionFilter("Excel 97-2003 Workbook", "*.xls"));
    	File file = chooser.showOpenDialog(Main.primaryStage);
    	if(file != null) {
    		ExcelService manager = new ExcelService();
    		try {
    			dataList = manager.loadData(file.getPath());
    			if(dataList != null && !dataList.isEmpty()) {
    				int count = JScheduleService.getInstance().importData(dataList);
    				if(count > 0){
    					showDialog(ErrorType.INFOMATION, "Upload Schedule", "Upload schedule data successfully!");
    					main.getChildren().clear();
    					setupDataTable();
    				}else{
    					showDialog(ErrorType.ERROR, "Upload Schedule", "Something wrong while uploading schedule data!");
    				}
    			}
			} catch (Exception ex) {
				ex.printStackTrace();
				showDialog(ErrorType.ERROR, "Upload Schedule", "Something wrong while uploading schedule data!");
			}
    	}
	}

    private void setupDataTable() {
    	initData();
    	if(shiftGroups != null && !shiftGroups.isEmpty()) {
    		Iterator<ShiftGroup> itr = shiftGroups.iterator();
    		while(itr.hasNext()){
    			ShiftGroup item = itr.next();
    			/* add data row */
    			HBox row = new HBox();
        		row.setPrefSize(620.0, 30.0);
        		HBox.setMargin(row, new Insets(0, 10, 0, 10));
        		main.getChildren().addAll(row);

        		/* add left header */
        		Label groupName = new Label(item.getName());
        		groupName.setFont(new Font(12.0));
        		groupName.setStyle("-fx-font-weight: bold;");
        		groupName.setStyle("-fx-background-color: #EFF4FA;");
        		groupName.setPrefSize(90.0, 30.0);
        		groupName.setAlignment(Pos.CENTER);
        		row.getChildren().addAll(groupName);

        		/* filter schedule by group code */
        		List<ScheduleItem> filteredList = filterScheduleByGroupCode(dataList, item.getCode());
        		if(filteredList != null && !filteredList.isEmpty()){
        			filteredList.sort(Comparator.comparing(ScheduleItem::getDay));
        			IntStream.range(1, 29).forEach(i -> {
        				try{
        					ScheduleItem grp = filteredList.stream().filter(a -> i == a.getDay()).findFirst().get();
        					ShiftType type = grp.getTypeList().get(0);
        					Node box = getCellBlock(type.getColorCode());
            				row.getChildren().addAll(box);
        				}catch(NoSuchElementException e){
        					Node box = getCellBlock("#998686");
            				row.getChildren().addAll(box);
        				}
        			});
        		}
    		}
    	}
    }

    private HBox getCellBlock(String colorCode) {
    	Label node = new Label();
    	node.setPrefSize(20.0, 30.0);
    	node.setStyle("-fx-background-radius: 2;");

    	HBox pane = new HBox();
    	pane.setStyle(String.format("-fx-background-color: %s;", colorCode));
    	HBox.setMargin(pane, new Insets(1, 1, 1, 1));
    	pane.getChildren().add(node);
    	return pane;
    }

    /* collect schedule, type and group data */
    private void initData() {
    	if(dataList != null && !dataList.isEmpty())
    		dataList.clear();
    	dataList = JScheduleService.getInstance().getAllSchedules();
    	shiftTypes = JScheduleService.getInstance().getAllShiftType();
    	shiftGroups = JScheduleService.getInstance().getAllShiftGroups();

    	if(dataList != null && !dataList.isEmpty()){
    		List<ScheduleItem> scheduelList = new ArrayList<ScheduleItem>();
    		dataList.stream().forEach(gp->{
    			ShiftType type = shiftTypes.stream().filter(t -> gp.getShiftCode().equals(t.getCode())).findFirst().get();
    			ShiftGroup group = shiftGroups.stream().filter(g -> gp.getShiftGroupCode().equals(g.getCode())).findFirst().get();
    			scheduelList.add(new ScheduleItem(gp.getId(), gp.getDay(), gp.getShiftCode(), gp.getShiftGroupCode(), type, group));
    		});
    		dataList.clear();
    		dataList = new ArrayList<ScheduleItem>(scheduelList);
    	}
    }

    private List<ScheduleItem> filterScheduleByGroupCode(List<ScheduleItem> dataList, String shiftGroupCode){
    	List<ScheduleItem> filteredList = dataList.stream().filter(a -> (a.getGroupList().get(0).getCode().equals(shiftGroupCode)))
				.collect(Collectors.toList());
    	return filteredList;
	}
}
