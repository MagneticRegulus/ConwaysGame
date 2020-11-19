package application;

import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class Main extends Application {

	public static final int WIDTH = 520;
	public static final int HEIGHT = 570;
	
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("GUI.fxml"));
		Scene scene = new Scene(root, WIDTH, HEIGHT); 
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Conway's Game of Life");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
