package com.sai.jschedule.ctrls;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MessageBoxController {

	@FXML private JFXButton btnOk;
	@FXML private ImageView icon;
	@FXML private Label message, title;
	@FXML private HBox root;

	public void buildDialog(String titleText, String messageText, Image image){
		title.setText(titleText);
		message.setText(messageText);
		icon.setImage(image);
		btnOk.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				((Stage)((Node)event.getSource()).getScene().getWindow()).close();
			}
		});
	}
}
