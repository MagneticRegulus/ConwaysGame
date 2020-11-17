package application;

import javafx.scene.paint.Color;

public class Cell {
	
	private static final Color ALIVE = Color.WHITE;
	private static final Color STATIC = Color.GREY;
	private static final Color DEAD = Color.TRANSPARENT;
	private boolean alive;
	private boolean lastState;
	private boolean nextState;
	private Color color;
	private boolean updated;
	
	public Cell() {
		this.alive = false;
		setAlive(true);
	}

	public Color getColor() {
		return color;
	}

	public void setColor() {
		if (this.alive && this.lastState) {
			color = STATIC;
		} else if (this.alive) {
			color = ALIVE;
		} else {
			color = DEAD;
		}
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		setLastState(this.alive);
		this.alive = alive;
		setColor();
	}

	public boolean wasAlive() {
		return lastState;
	}

	public void setLastState(boolean lastState) {
		this.lastState = lastState;
	}

	public boolean willBeAlive() {
		return nextState;
	}

	public void setNextState(boolean nextState) {
		this.nextState = nextState;
	}
	
	public boolean isUpdated() {
		return updated;
	}

	public void setUpdated() {
		this.updated = true;
	}

	public void update() {
		if (isUpdated()) { return; }
		setAlive(willBeAlive());
		setUpdated();
	}

}
