package com.sai.jschedule.common;

import java.io.IOException;

import com.sai.jschedule.Main;
import com.sai.jschedule.ctrls.MessageBoxController;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public abstract class AppController implements Initializable{

	protected void showDialog(ErrorType errorType, String title, String message){
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(SceneInfo.TMPL_MSG_DIALOG.getLayoutName()));
			Parent parent = loader.load();

			MessageBoxController controller = loader.getController();
			Image image = new Image(getClass().getResourceAsStream("/img/" + errorType.iconName()));
			controller.buildDialog(title, message, image);

			Scene scene = new Scene(parent);
			Stage dialogStage = new Stage();
			dialogStage.initModality(Modality.APPLICATION_MODAL);
			dialogStage.initStyle(StageStyle.UNDECORATED);
			dialogStage.setResizable(false);
			dialogStage.setScene(scene);
			dialogStage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void close(){
		Main.primaryStage.close();
	}
}
