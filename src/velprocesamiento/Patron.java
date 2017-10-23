package velprocesamiento;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Patron {

	private int contador;
	private int r, g, b;
	private PApplet app;
	private PVector pos;
	private PVector vel;
	private PVector ace;
	private float topS = 5f;
	private boolean izq = false, der = false;
	private PImage img;
	private int type;

	public Patron(PApplet app, PImage img, int x, int y, int type) {
		this.app = app;
		this.img = img;
		this.type = type;
		contador = (int) app.random(6);
		pos = new PVector(x, y);
		vel = new PVector(0, 0);
		ace = new PVector(0, 0.01f);
		topS = 3;
		colores();
	}

	public void pintar() {
		app.imageMode(PApplet.CENTER);
		app.image(img, pos.x, pos.y);
		app.imageMode(PApplet.CORNER);
		
//		app.textSize(20);
//		app.textAlign(PApplet.CENTER, PApplet.CENTER);
//		app.rectMode(PApplet.CENTER);
//		app.fill(255);
//		app.rect(pos.x, pos.y, 40, 40);
//		app.rectMode(PApplet.CORNER);
//		app.fill(0);
//		app.text(type, pos.x, pos.y);
//		app.noFill();
	}

	public void mover() {
		if (izq) {
			vel.add(new PVector(-0.05f, 0));
			der = false;
		} else if (der) {
			vel.add(new PVector(0.05f, 0));
			izq = false;
		}

		vel.add(ace);
		vel.limit(topS);
		pos.add(vel);
	}

	private void colores() {
		switch (contador) {
		case 0: // RED
			r = 255;
			g = 120;
			b = 132;

			break;

		case 1: // GREEN
			r = 242;
			g = 242;
			b = 242;

			break;

		case 2: // BLUE
			r = 0;
			g = 177;
			b = 180;

			break;

		case 3: // YELLOW
			r = 253;
			g = 195;
			b = 12;

			break;

		case 4: // ORANGE
			r = 209;
			g = 244;
			b = 255;

			break;

		case 5: // PURPLE
			r = 70;
			g = 57;
			b = 89;

			break;
		}
	}

	public int getR() {
		return r;
	}

	public int getG() {
		return g;
	}

	public int getB() {
		return b;
	}

	public int getX() {
		return (int) pos.x;
	}

	public int getY() {
		return (int) pos.y;
	}

	public int getSpeed() {
		return (int) topS;
	}

	public void setSpeed() {
		this.topS += 1;
	}

	public boolean isIzq() {
		return izq;
	}

	public boolean isDer() {
		return der;
	}

	public void setIzq(boolean izq) {
		this.izq = izq;
	}

	public void setDer(boolean der) {
		this.der = der;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
