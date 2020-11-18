package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class Game {
	
	private int rows;
	private int cols;
	private double pix;
	private double canSize;
	private Map<Point2D, Cell> cells = new HashMap<>();
	
	public Game(int dim, double size) {
		this.rows = dim;
		this.cols = dim;
		this.pix = size;
		this.canSize = size;
	}
	
	public void generateCells() {
		double rand;
		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < cols; y++) {
				rand = Math.random();
				if (rand <= 0.1) {
					cells.put(new Point2D(x, y), new Cell(true));
				}
			}
		}
	}
	
	public void generateSpecial() {
		cells.put(new Point2D(24, 23), new Cell(true));
		cells.put(new Point2D(24, 24), new Cell(true));
		cells.put(new Point2D(24, 25), new Cell(true));
	}
	
	public void draw(GraphicsContext graphics) {
		graphics.clearRect(0, 0, canSize, canSize);
		for (Point2D pt : cells.keySet()) {
			graphics.setFill(cells.get(pt).getColor());
			graphics.fillRect(pt.getX(), pt.getY(), 1, 1);
		}
		
		for (int x = 0; x <= rows; x++) {
			graphics.strokeLine(x, 0, x, cols);
		}
		for (int y = 0; y <= cols; y++) {
			graphics.strokeLine(0, y, rows, y);
		}
	}
	
	public void pan(int x, int y) {
		//if moving to the left, x is positive, y is 0
		//if moving to the up, x is 0, y is positive
		//if moving right, x is negative, y is 0
		//if moving down, x is 0, y is negative
		
		Iterator<Map.Entry<Point2D, Cell>> it = cells.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Point2D, Cell> pt = it.next();
			Point2D point = pt.getKey();
			Cell cell = cells.get(point);
			cells.put(new Point2D(point.getX() + x, point.getY() + y), cell);
			cells.remove(point);
		}
		
	}
	
	public void updateGame() {
		addDeadNeighbours();
		//System.out.println("Added the relevant dead");
		prepNextRound();
		//System.out.println("Set the next round");
		updateCells();
		//System.out.println("Updated the cells");
		//removeDead();
		//System.out.println("Removed the dead");
	}
	
	public void addDeadNeighbours() {
		Set<Point2D> neighbours = new HashSet<>();
		Set<Point2D> dead = new HashSet<>();
		Iterator<Map.Entry<Point2D, Cell>> it = cells.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Point2D, Cell> pt = it.next();
			if (pt.getValue().isAlive()) {
				//System.out.println("Point " + pt.getKey().getX() + ", " + pt.getKey().getY() + " is alive.");
				neighbours = getNeighbours(pt.getKey());
				for (Point2D n : neighbours) {
					if (!cells.containsKey(n)) {
						dead.add(n);
					}
				}
			}
		}
		//int countDead = 0;
		for (Point2D d : dead) {
			cells.put(d, new Cell(false));
			//countDead++;
		}
		//System.out.println("Added " + countDead + " dead.");
	}
	
	public void prepNextRound() {
		int count = 0;
		for (Point2D pt : cells.keySet()) {
			count = countLiving(getNeighbours(pt));
			if (!cells.get(pt).isAlive() && count == 3) { 
				cells.get(pt).setNextState(true); 
				//System.out.println(" is dead but will be alive.");
			}
			else if (cells.get(pt).isAlive() && (count == 2 || count == 3)) { 
				cells.get(pt).setNextState(true);
				//System.out.println(" is alive and will stay that way.");
			}
			else { 
				cells.get(pt).setNextState(false);
				//System.out.println(" will be dead.");
			}
		}
	}
	
	public Set<Point2D> getNeighbours(Point2D pt) {
		Set<Point2D> neighbours = new HashSet<>();
		neighbours.add(new Point2D(pt.getX() - 1, pt.getY() - 1));
		neighbours.add(new Point2D(pt.getX(), pt.getY() - 1));
		neighbours.add(new Point2D(pt.getX() + 1, pt.getY() - 1));
		neighbours.add(new Point2D(pt.getX() - 1, pt.getY()));
		neighbours.add(new Point2D(pt.getX() + 1, pt.getY()));
		neighbours.add(new Point2D(pt.getX() - 1, pt.getY() + 1));
		neighbours.add(new Point2D(pt.getX(), pt.getY() + 1));
		neighbours.add(new Point2D(pt.getX() + 1, pt.getY() + 1));
		//System.out.print("Point " + pt.getX() + ", " + pt.getY() + " Alive: " + cells.get(pt).isAlive() + " ");
		return neighbours;
	}
	
	public int countLiving(Set<Point2D> neighbours) {
		int count = 0;
		for (Point2D n : neighbours) {
			if (cells.get(n) != null && cells.get(n).isAlive()) { count++; }
		}
		//System.out.print("count: "+ count);
		return count;
	}
	
	public void updateCells() {
		for (Point2D pt : cells.keySet()) {
			cells.get(pt).update();
		}
	}
	
	public void removeDead() {
		Iterator<Map.Entry<Point2D, Cell>> it = cells.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Point2D, Cell> pt = it.next();
			if (!pt.getValue().isAlive()) { it.remove(); }
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
	
}
