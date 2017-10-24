package memoria;

import processing.core.PApplet;

public class Respuesta {
	private String texto;
	private int x, y;

	public Respuesta(String texto, int x, int y) {
		this.texto = texto;
		this.x = x;
		this.y = y;
	}

	public void pintar(PApplet app) {
		app.stroke(153, 75, 90);
		app.strokeWeight((int) 0.1);
		app.fill(231, 112, 130);
		app.ellipse(x, y - 10, 100, 80);
		app.fill(255);
		app.textAlign(PApplet.CENTER);
		app.text(texto, x, y);
		app.textAlign(app.LEFT);

	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
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

}
