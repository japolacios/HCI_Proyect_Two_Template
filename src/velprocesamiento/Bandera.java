package velprocesamiento;

import processing.core.PApplet;
import processing.core.PImage;

public class Bandera {

	private PApplet app;
	private PImage img;
	private int x, y;
	private int type;

	public Bandera(PApplet app, PImage imgTemp, int x, int y, int type) {
		this.app = app;
		this.img = imgTemp;
		this.x = x;
		this.y = y;
		this.type = type;
	}

	public void pintar() {
		app.imageMode(PApplet.CENTER);
		app.image(img, x, y);
		app.imageMode(PApplet.CORNER);
	}

	public int posX() {
		return x;
	}

	public int posY() {
		return y;
	}

	public boolean validarClick(int xM, int yM) {
		if (PApplet.dist(xM, yM, x, y) < 50) {
			// app.fill(255);
			// app.rect(x, y, 50, 50);
			return true;
		} else
			return false;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}
}
