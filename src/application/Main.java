package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	
	private static final int WIDTH = 520; 
	private static final int HEIGHT = 570;
	private GraphicsContext graphics;
	private Game game;
	private Affine affine;
	
	@FXML
	private Canvas canvas; 
	@FXML
	private Button stepBtn;
	@FXML
	private Button genBtn;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("GUI.fxml"));			
			Scene scene = new Scene(root, WIDTH, HEIGHT);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Conway's Game of Life");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void generate(ActionEvent event) {
		event.consume();
		this.game = new Game(50, this.canvas.getWidth()); //Canvas always set to square.
		this.affine = new Affine();
		this.affine.appendScale(this.canvas.getWidth() / this.game.getRows(), this.canvas.getHeight() / this.game.getCols());
		this.graphics = this.canvas.getGraphicsContext2D();
		setGraphics();
		this.game.generateCells();
		//this.game.generateSpecial();
		this.game.draw(this.graphics);
	}
	
	public void setGraphics() {
		this.graphics.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		this.graphics.setStroke(Color.WHITE);
		//this.graphics.strokeRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
		this.graphics.setTransform(this.affine);
		this.graphics.setLineWidth(0.05);
	}
	
	public void step(ActionEvent event) {
		event.consume();
		nextRound();
	}
	
	public void nextRound() {
		this.game.updateGame();
		this.game.draw(this.graphics);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
