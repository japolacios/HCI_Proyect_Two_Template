package memoria;

import processing.core.PApplet;

public class HotZone {
	int x, y, tam;

	public HotZone(int x, int y) {
		tam=150;
		this.x = x;
		this.y = y;
	}

	public void pintar(PApplet app) {
		app.noStroke();
		app.fill(231, 112, 130);
		app.rect(x, y, tam*2, tam,50);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getTam() {
		return tam;
	}

	public void setTam(int tam) {
		this.tam = tam;
	}

}
