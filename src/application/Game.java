package application;

public class Game {
	
	private int rows;
	private int cols;
	private double pix;
	private double cellSize;
	
	public Game(int dim, double size) {
		this.rows = dim;
		this.cols = dim;
		this.pix = size;
		this.cellSize = size / dim;
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
