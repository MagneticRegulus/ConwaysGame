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
	private boolean checked;
	
	public Cell(boolean state) {
		this.checked = false;
		this.alive = false;
		setAlive(state);
	}

	public Color getColor() {
		return color;
	}

	public void setColor() {
		if (isAlive() && wasAlive()) {
			color = STATIC;
		} else if (isAlive()) {
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
		toggleChecked();
	}

	public void toggleChecked() {
		this.checked = !this.checked;
	}

	public void update() {
		setAlive(willBeAlive());
		toggleChecked();
	}
	
	public void toggle() {
		setAlive(!alive);
	}

}
