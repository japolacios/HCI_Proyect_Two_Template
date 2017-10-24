package memoria;

import java.util.ArrayList;
import java.util.StringTokenizer;

import processing.core.PApplet;
import processing.core.PConstants;

public class Pregunta {
	private String[] partes;
	private String opciones;
	private String[] opcionesArray;
	private ArrayList<Respuesta> respuestas;
	private String enunciado;
	private int x, y;
	private String[] enunciadoArray;
	private String resCorrecta;

	Pregunta(String linea, int x, int y) {
		this.x = x;
		this.y = y;
		partes = linea.split(":");
		enunciado = partes[0];
		opciones = partes[1];
		resCorrecta = partes[2];
		respuestas = new ArrayList<Respuesta>();
		opcionesArray = opciones.split("_");
		enunciadoArray = enunciado.split("#");

		for (int i = 0; i < opcionesArray.length; i++) {
			respuestas.add(new Respuesta(opcionesArray[i], 300 + i * 150, 350));
		}

	}

	public void pintar(PApplet app) {
		app.noStroke();
		app.fill(231, 112, 130);
		app.rect(x, 150, 800, 150, 50);
		app.textSize(20);

		if (enunciadoArray.length > 1) {
			app.fill(231, 112, 130);
			for (int i = 0; i < enunciadoArray.length; i++) {
				app.fill(255);
				app.textAlign(app.CENTER, app.BOTTOM);
				app.text(enunciadoArray[i], x, y-20 + i * 30);
			}
		} else {
			
			app.fill(255);
			app.text(enunciado, x, y);
		}

		for (int i = 0; i < respuestas.size(); i++) {
			respuestas.get(i).pintar(app);
		}
	}

	public ArrayList<Respuesta> getRespuestas() {
		return respuestas;
	}

	public void setRespuestas(ArrayList<Respuesta> respuestas) {
		this.respuestas = respuestas;
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
	
	public String getResCorrecta() {
		return resCorrecta;
	}

	public void setResCorrecta(String resCorrecta) {
		this.resCorrecta = resCorrecta;
	}

}
