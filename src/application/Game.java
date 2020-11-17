package application;

import java.util.HashMap;
import java.util.Map;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class Game {
	
	private int rows;
	private int cols;
	private double pix;
	private double cellSize;
	private Map<Point2D, Cell> cells = new HashMap<>();
	
	public Game(int dim, double size) {
		this.rows = dim;
		this.cols = dim;
		this.pix = size;
		this.cellSize = size / dim;
	}
	
	public void generateCells(GraphicsContext graphics) {
		double rand;
		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < cols; y++) {
				rand = Math.random();
				if (rand <= 0.05) {
					cells.put(new Point2D(x, y), new Cell());
				}
			}
		}
		draw(graphics);
	}
	
	public void draw(GraphicsContext graphics) {
		for (Point2D pt : cells.keySet()) {
			graphics.setFill(cells.get(pt).getColor());
			graphics.fillRect(pt.getX(), pt.getY(), 1, 1);
		}
	}
	
	public double getPix() {
		return pix;
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}

	public double getCellSize() {
		return cellSize;
	}
	
}
