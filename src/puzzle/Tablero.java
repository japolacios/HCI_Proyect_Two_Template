package puzzle;

import java.util.LinkedList;

import processing.core.PApplet;

public class Tablero {
	PApplet app;
	private int x, y;
	private int lugar;
	private int count;
	private float sX;
	private float sY;
	private int puntaje;
	private int color;
	private boolean isScoring, isRange;

	public Tablero(PApplet app, int x, int y, int lugar, float sX, float sY) {
		this.app = app;
		this.x = x;
		this.y = y;
		this.lugar = lugar;
		this.sX = sX;
		this.sY = sY;
		count = 0;
		puntaje = 0;
		isScoring = false;
		isRange = false;
	}

	public void pintar() {
		app.noFill();
		app.fill(240, 29, 22);
		app.stroke(360);
		app.strokeWeight(2);
		app.rect(x, y, sX, sY);
		app.fill(360);
		//app.text(lugar, x + (sX / 2), y + (sY / 2));
	}


	public void validar(LinkedList<Ficha> fichas) {
		int count = 0;
		if (count < 1) {
			color = 0;
			isScoring = false;
		}
		for (Ficha ficha : fichas) {
			if (ficha.getPosX() > x && ficha.getPosX() < (x + sX) && ficha.getPosY() > y
					&& ficha.getPosY() < (y + sY)) {
				count++;
				if (count > 1) {
					System.out.println("M�s de una ficha dentro del tablero" + lugar);
					color = 240;
					isScoring = false;
				} else if (ficha.getLugar() == lugar) {
					puntaje = 100;
					isScoring = true;
					System.out.println("De Guan");
					color = 120;
				}
				isRange = true;
			} else {
				isRange = false;
			}

		}
		count = 0;
	}

	public boolean validatePos(Ficha ficha) {
		isRange = false;

		if (ficha.getPosX() > x && ficha.getPosX() < (x + sX) && ficha.getPosY() > y && ficha.getPosY() < (y + sY)) {
			isRange = true;
		}

		return isRange;
	}

	public boolean getScore() {
		return isScoring;
	}

	public int getPuntaje() {
		return puntaje;
	}

	public int getPosX() {
		return (int) (x + (sX / 2));
	}

	public int getPosY() {
		return (int) (y + (sY / 2));
	}
}
