package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
					cells.put(new Point2D(x, y), new Cell(true));
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
	
	public void updateGame() {
		addDeadNeighbours();
		prepNextRound();
		updateCells();
		removeDead();
	}
	
	public void addDeadNeighbours() {
		List<Point2D> neighbours = new ArrayList<>();
		for (Point2D pt : cells.keySet()) {
			if (cells.get(pt).isAlive()) {
				neighbours = getNeighbours(pt);
				for (Point2D n : neighbours) {
					if (!cells.containsKey(n)) {
						cells.put(n, new Cell(false));
					}
				}
			}
		}
	}
	
	public void prepNextRound() {
		int count = 0;
		for (Point2D pt : cells.keySet()) {
			count = countLiving(getNeighbours(pt));
			if (!cells.get(pt).isAlive() && count == 3) { cells.get(pt).setNextState(true); }
			else if (cells.get(pt).isAlive() && (count == 2 || count == 3)) { cells.get(pt).setNextState(true); }
			else { cells.get(pt).setNextState(false); }
		}
	}
	
	public List<Point2D> getNeighbours(Point2D pt) {
		List<Point2D> neighbours = new ArrayList<>();
		neighbours.add(new Point2D(pt.getX() - 1, pt.getY() - 1));
		neighbours.add(new Point2D(pt.getX(), pt.getY() - 1));
		neighbours.add(new Point2D(pt.getX() + 1, pt.getY() - 1));
		neighbours.add(new Point2D(pt.getX(), pt.getY() - 1));
		neighbours.add(new Point2D(pt.getX(), pt.getY() + 1));
		neighbours.add(new Point2D(pt.getX() - 1, pt.getY() + 1));
		neighbours.add(new Point2D(pt.getX(), pt.getY() + 1));
		neighbours.add(new Point2D(pt.getX() + 1, pt.getY() + 1));
		return neighbours;
	}
	
	public int countLiving(List<Point2D> neighbours) {
		int count = 0;
		for (Point2D n : neighbours) {
			if (cells.get(n) != null && cells.get(n).isAlive()) { count++; }
		}
		return count;
	}
	
	public void updateCells() {
		for (Point2D pt : cells.keySet()) {
			cells.get(pt).update();
		}
	}
	
	public void removeDead() {
		for (Point2D pt : cells.keySet()) {
			if (!cells.get(pt).isAlive()) {
				cells.remove(pt);
			}
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
