package application;

import java.awt.Event;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.util.Duration;

public class Controller implements Initializable {
	
	private static final int PAN_DISTANCE = 10;
	private GraphicsContext graphics;
	private Game game;
	private Affine affine;
	private boolean running = false;
	private Timeline animation;

	@FXML
	private Canvas canvas;
	@FXML
	private Button stepBtn;
	@FXML
	private Button genBtn;
	@FXML
	private Button playBtn;
	@FXML
	private Button clearBtn;
	@FXML
	private BorderPane root;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		openApp();
		
		KeyFrame frame = new KeyFrame(Duration.millis(128), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {
				nextRound();
			}

		});

		this.animation = new Timeline(frame);
		this.animation.setCycleCount(javafx.animation.Animation.INDEFINITE);
		
		EventHandler<KeyEvent> panCanvas = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				
				if (keyEvent.getCode() == KeyCode.LEFT) {
					game.pan(PAN_DISTANCE, 0);
				} else if (keyEvent.getCode() == KeyCode.UP) {
					game.pan(0, PAN_DISTANCE);
				} else if (keyEvent.getCode() == KeyCode.RIGHT) {
					game.pan(-PAN_DISTANCE, 0);
				} else if (keyEvent.getCode() == KeyCode.DOWN) {
					game.pan(0, -PAN_DISTANCE);
				}
				
				game.draw();
			}

		};
		
		EventHandler<MouseEvent> click = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				game.toggleCell(event.getX(), event.getY());
				
			}
			
		};
		
		canvas.setOnMouseClicked(click);
		root.setOnKeyPressed(panCanvas);
		
	}
	
	public void openApp() {
		this.graphics = this.canvas.getGraphicsContext2D();
		newGame();
		this.affine = new Affine();
		this.affine.appendScale(this.canvas.getWidth() / this.game.getRows(),
				this.canvas.getHeight() / this.game.getCols());
		setGraphics();
		this.game.drawGrid();
	}
	
	public void newGame() {
		this.game = new Game(50, this.canvas.getWidth(), this.graphics); // Canvas always set to square.
	}
	
	public void generate(ActionEvent event) {
		event.consume();
		pause();
		this.game.clearGrid();
		this.game.generateCells();
		this.game.draw();
		root.requestFocus();
	}

	public void setGraphics() {
		this.graphics.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		this.graphics.setStroke(Color.WHITE);
		this.graphics.setTransform(this.affine);
		this.graphics.setLineWidth(0.05);
	}

	public void step(ActionEvent event) {
		event.consume();
		pause();
		nextRound();
		root.requestFocus();
	}

	public void togglePlay(ActionEvent event) {
		event.consume();
		if (running) {
			pause();
		} else {
			animate();
		}
		root.requestFocus();
	}
	
	public void clearAndPause(ActionEvent event) {
		event.consume();
		pause();
		this.game.clearGrid();
		root.requestFocus();
	}

	public void nextRound() {
		this.game.updateGame();
		this.game.draw();
	}

	public void animate() {
		running = true;
		playBtn.setText("Pause");
		animation.play();
	}

	public void pause() {
		running = false;
		playBtn.setText("Play");
		animation.pause();
	}

}
