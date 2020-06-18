package com.sai.jschedule;

import java.io.IOException;

import com.sai.jschedule.common.AppLock;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	public static Stage primaryStage;

	@Override
	public void start(Stage primaryStage) {
		try {
			Main.primaryStage = primaryStage;
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("/layout/main.fxml"));
			Scene scene = new Scene(root, 900, 500);
			scene.getStylesheets().add(getClass().getResource("/css/app.css").toExternalForm());

			primaryStage.setResizable(false);
			primaryStage.setTitle("Schedule Management");
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/img/ic_icon.png")));
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		AppLock lock = new AppLock();
		try {
			if(!lock.isAppActive())
				launch(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
