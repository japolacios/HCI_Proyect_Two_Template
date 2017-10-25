package puzzle;

import processing.core.PApplet;
import processing.core.PImage;

public class FichaOpcion {

	private PApplet app;
	private PImage img;
	private int x;
	private int y;
	private int mapX, mapY;
	private boolean isRight;

	public FichaOpcion(PApplet app, PImage img, int x, int y, boolean isRight, int mapX, int mapY) {
		this.app = app;
		this.img = img;
		this.x = x;
		this.y = y;
		this.isRight = isRight;
		this.mapX = mapX;
		this.mapY = mapY;
	}

	public void pintar() {

		if (validar(app.mouseX, app.mouseY)) {
		} else {
		}
		// System.out.println(an);
		app.pushStyle();
		app.imageMode(PApplet.CENTER);
		app.image(img, x, y);
		app.popStyle();
		cubrirFicha();
	}

	private void cubrirFicha() {
		int cutX = img.width / 2;
		int cutY = img.height / 2;
		if (getIsRight()) {
			app.pushStyle();
			app.fill(240, 29, 22);
			app.noStroke();
			app.rect(getMapX(), getMapY(), img.width, img.height);
			app.fill(360);
			app.textAlign(PApplet.CENTER, PApplet.CENTER);
			// app.text("V", x, y);
			// app.text("x: " + getMapX() + " y: " + getMapY(), getMapX() +
			// cutX, getMapY() + cutY);
			app.popStyle();
		} else {
			// app.text("F", x, y);
		}
	}

	public boolean validar(int posX, int posY) {
		float cutX = getxS() / 2;
		float cutY = getyS() / 2 / 2;
		boolean validar = posX > (x - cutX) && posX < img.width + (x - cutX) && posY > (y - cutY)
				&& posY < img.height + (y - cutY);
		return validar;
	}

	public void setMover(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public boolean getIsRight() {
		return isRight;
	}

	public void setIsRight(boolean isRight) {
		this.isRight = isRight;
	}

	public int getMapX() {
		return mapX;
	}

	public int getMapY() {
		return mapY;
	}

	public int getxS() {
		return img.width;
	}

	public int getyS() {
		return img.height;
	}
}
