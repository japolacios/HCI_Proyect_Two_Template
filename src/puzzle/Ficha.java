package puzzle;

import processing.core.PApplet;
import processing.core.PImage;

public class Ficha {
	PApplet app;
	PImage img;
	private int posX, posY, lugar;

	// 486 76
	// 194 255
	// 790 299
	public Ficha(PApplet app, PImage img, int lugar) {
		this.app = app;
		this.img = img;

		int ran = (int) app.random(0, 4);
		if (ran == 0) {
			posY = (int) app.random((img.height / 2), 76);
			posX = (int) app.random((img.width / 2), app.width - (img.width / 2));
		} else if (ran == 1) {
			posY = (int) app.random((img.height / 2), 574);
			posX = (int) app.random((img.width / 2), 200);
		} else {
			posY = (int) app.random((img.height / 2), 574);
			posX = (int) app.random(800, app.width - (img.width / 2));
		}

		this.lugar = lugar;
	}
	
	public void setRandomPos(){
		int ran = (int) app.random(0, 4);
		if (ran == 0) {
			posY = (int) app.random((img.height / 2), 76);
			posX = (int) app.random((img.width / 2), app.width - (img.width / 2));
		} else if (ran == 1) {
			posY = (int) app.random((img.height / 2), 574);
			posX = (int) app.random((img.width / 2), 200);
		} else {
			posY = (int) app.random((img.height / 2), 574);
			posX = (int) app.random(800, app.width - (img.width / 2));
		}
	}

	public Ficha(PApplet app, PImage img, int lugar, int posX, int posY) {
		this.app = app;
		this.img = img;
		this.posY = posY;
		this.posX = posX;
		this.lugar = lugar;
	}

	public void pintar() {
		app.pushStyle();
		app.imageMode(PApplet.CENTER);
		app.image(img, posX, posY);
		app.fill(255);
		// app.text(lugar, posX, posY);
		app.popStyle();
	}

	public boolean validar(int x, int y) {
		float cutX = img.width / 2;
		float cutY = img.height / 2;
		boolean validar = x > (posX - cutX) && x < img.width + (posX - cutX) && y > (posY - cutY)
				&& y < img.height + (posY - cutY);
		return validar;
	}

	public void mover(int x, int y) {
		this.posX = x;
		this.posY = y;
	}

	public int getLugar() {
		return lugar;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}
}
