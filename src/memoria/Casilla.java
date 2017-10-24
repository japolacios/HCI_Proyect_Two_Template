package memoria;

import processing.core.PApplet;

public class Casilla {
	private int x, y, tam;
	private boolean mostrar;
	private boolean mostrado;
	private int tam2;

	public Casilla(int x, int y, int tam) {
		this.x = x;
		this.y = y;
		this.tam = tam;
		tam2 = tam;
		mostrado = false;
	}

	public void pintar(PApplet app) {
		app.stroke(255, 200);
		if (mostrar) {
			app.fill(231, 112, 130);
			mostrado = true;
		} else {
			app.fill(100, 100);
		}
		app.rect(x, y, tam, tam2, 20);
		app.noFill();
	}

	public int getTam2() {
		return tam2;
	}

	public void setTam2(int tam2) {
		this.tam2 = tam2;
	}

	public boolean isMostrado() {
		return mostrado;
	}

	public void setMostrado(boolean mostrado) {
		this.mostrado = mostrado;
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

	public boolean isMostrar() {
		return mostrar;
	}

	public void setMostrar(boolean mostrar) {
		this.mostrar = mostrar;
	}

}
