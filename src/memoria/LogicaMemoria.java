package memoria;

import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.data.Table;
import processing.data.TableRow;

public class LogicaMemoria {
	private PApplet app;
	private int modoJuego;
	private int puntaje;
	private long t;
	private long t_actualizado;
	private long t_inicial;
	private long t_intento;
	private PImage bg;
	private PImage bg2;
	private boolean entrada, dataSaved, gameOver;
	private PImage entradaImg;
	private PImage btIniciarBase, btIniciarOver, btIniciarPress;
	private Table table;
	private String tipoInteligencia = "Memoria";
	public Ui ui;

	// --------------------------------------------------CASO 0 - PREGUNTAS ABCD
	private String[] txt0;
	private String[] txt1;
	private String[] txt2;
	private String[] txt3;
	private String[] txt4;
	private Pregunta[] preguntas;
	private int xP, yP;
	private int noPregunta;
	private HotZone hotZone;
	private Pregunta p;
	private Respuesta selRespuesta = null;
	private Casilla[][] navegacion;
	private Casilla[] navegacionFlechas;

	// -----------------------------------------------CASO 1 - MATRIZ DE MEMORIA
	private Casilla[][] grid;
	private int dificultad;
	private ArrayList<Casilla> casillas;
	private boolean crearMatriz;
	private int estado;
	private boolean contando;
	private int matrixPosX, matrixPosY;
	private int tamCasilla;
	private boolean btJugar;
	private int destapados;
	private int prendidos;
	private int acertadas;

	// -------------------------------------- CRONOMETRO
	private int segundos;
	private int minutos;
	private boolean btStart;

	public LogicaMemoria(PApplet _app, Table _table) {
		app = _app;
		table = _table;
		gameOver = false;
		dataSaved = false;
		Ui ui = new Ui(app);
		modoJuego = 0;
		noPregunta = 0;
		bg = app.loadImage("../data/memoria/fondo.png");
		bg2 = app.loadImage("../data/memoria/minijuego.png");
		entradaImg = app.loadImage("../data/memoria/entrada.png");
		btIniciarBase = app.loadImage("../data/memoria/1.png");
		btIniciarOver = app.loadImage("../data/memoria/2.png");
		btIniciarPress = app.loadImage("../data/memoria/3.png");

		entrada = true;
		btStart = true;
		// ----------------------------------------------CASO 0 - PREGUNTAS ABCD
		hotZone = new HotZone(app.width / 2, 550);
		preguntas = new Pregunta[16];
		xP = app.width / 2;
		yP = 150;
		p = preguntas[noPregunta];
		txt0 = app.loadStrings("../data/memoria/preg0.txt");
		txt1 = app.loadStrings("../data/memoria/preg1.txt");
		txt2 = app.loadStrings("../data/memoria/preg2.txt");
		txt3 = app.loadStrings("../data/memoria/preg3.txt");
		txt4 = app.loadStrings("../data/memoria/preg4.txt");

		for (int j = 0; j < 3; j++) {
			preguntas[j] = new Pregunta(txt0[j], xP, yP);
		}
		for (int j = 4; j < 7; j++) {
			preguntas[j] = new Pregunta(txt1[(int) app.random(0, txt1.length)], xP, yP);
		}
		for (int j = 7; j < 10; j++) {
			preguntas[j] = new Pregunta(txt2[(int) app.random(0, txt2.length)], xP, yP);
		}
		for (int j = 10; j < 13; j++) {
			preguntas[j] = new Pregunta(txt3[(int) app.random(0, txt3.length)], xP, yP);
		}
		for (int j = 13; j < 16; j++) {
			preguntas[j] = new Pregunta(txt4[(int) app.random(0, txt4.length)], xP, yP);
		}

		// ---------------------------------------TIEMPO CASO 0 - PREGUNTAS ABCD
		minutos = 6;

		// -------------------------------------------CASO 1 - MATRIZ DE MEMORIA
		dificultad = 0;
		btJugar = true;
		crearMatriz = true;
		casillas = new ArrayList<Casilla>();
		estado = 0;
		tamCasilla = 100;
	}

	public void paint() {
		
		if(gameOver == true) {
			ui.paint();
		}
		else {
		
		app.fill(0);
		t = app.millis();

		if (entrada) {
			btJugar = false;
			app.image(entradaImg, 0, 0);
			if (btStart) {

				app.imageMode(PConstants.CENTER);
				if (app.mouseX > app.width / 2 - btIniciarBase.width / 2
						&& app.mouseX < app.width / 2 + btIniciarBase.width / 2
						&& app.mouseY > (app.height - 100) - (btIniciarBase.height / 2)
						&& app.mouseY < (app.height - 100) + (btIniciarBase.height / 2)) {
					app.image(btIniciarOver, app.width / 2, app.height - 100);
				} else {
					app.image(btIniciarBase, app.width / 2, app.height - 100);
				}
				app.imageMode(PConstants.CORNER);

			}

		} else {

			switch (modoJuego) {
			// ----------------------------------------------CASO 0 - PREGUNTAS
			// ABCD
			case 0:

				if (noPregunta <= 3) {
					app.image(bg, 0, 0);
				} else {
					app.image(bg2, 0, 0);
				}

				if (noPregunta >= 4) {
					app.fill(231, 112, 130);
					app.textSize(20);
					int noPreg = noPregunta - 3;
					app.text("Pregunta: " + noPreg + "/12", 120, 50);
					hotZone.pintar(app);
					pintarNavegacion();
					pintarCronometro(app.width / 2, app.height - 50);

				} else if (noPregunta < 3) {

					app.fill(231, 112, 130);
					app.textSize(20);
					app.text("Tutorial", app.width / 2, 60);
					hotZone.pintar(app);

				}

				p = preguntas[noPregunta];
				switch (noPregunta) {
				case 0:
					p.pintar(app);
					app.textAlign(PConstants.LEFT);
					app.fill(255);
					app.textSize(40);
					app.text("Pregunta--->", 10, 160);
					app.text("Opciones--->", 10, 350);
					app.text("Zona de Respuesta--->", 10, 560);
					app.fill(231, 112, 130);
					app.textAlign(PConstants.CENTER);
					app.textSize(20);
					app.text("PARA RESPONDER:\nArrastra una Opci�n a la Zona de Respuesta", app.width / 2, 410);
					break;
				case 1:
					p.pintar(app);
					break;
				case 2:
					p.pintar(app);
					break;
				case 3:
					app.fill(255, 200);
					app.textSize(150);
					app.text("�Bien Hecho!", app.width / 2, 220);
					app.textSize(20);
					app.text("A partir del momento en que presiones el siguiente bot�n \n comenzar�n los " + minutos
							+ " minutos para terminar el test", app.width / 2, 300);
					if (btJugar) {
						app.fill(231, 112, 130);
						app.rect(app.width / 2, app.height - 100, 120, 50, 20);
						app.fill(255);
						app.text("JUGAR", app.width / 2, app.height - 90);
						app.noFill();
					}

					break;
				case 4:
					p.pintar(app);
					break;
				case 5:
					p.pintar(app);
					break;
				case 6:
					p.pintar(app);
					break;
				case 7:
					p.pintar(app);
					break;
				case 8:
					p.pintar(app);
					break;
				case 9:
					p.pintar(app);
					break;
				case 10:
					p.pintar(app);
					break;
				case 11:
					p.pintar(app);
					break;
				case 12:
					p.pintar(app);
					break;
				case 13:
					p.pintar(app);
					break;
				case 14:
					p.pintar(app);
					break;
				case 15:
					p.pintar(app);
					break;
				}
				break;
			// ---------------------------------------CASO 1 - MATRIZ DE MEMORIA
			case 1:
				app.image(bg, 0, 0);
				if (dificultad < 5) {
					if (btJugar) {
						app.fill(231, 112, 130);
						app.rect(app.width / 2, app.height - 100, 120, 50, 20);
						app.fill(255);
						app.text("JUGAR", app.width / 2, app.height - 90);
						app.noFill();
					}
				}

				switch (dificultad) {
				case 0:
					crearMatrizMemoria(2, 2, 2);
					break;
				case 1:
					crearMatrizMemoria(2, 3, 3);
					break;
				case 2:
					crearMatrizMemoria(3, 3, 4);
					break;
				case 3:
					btJugar = true;
					app.fill(255, 200);
					app.textSize(150);
					app.text("�Bien Hecho!", 500, 220);
					app.textSize(20);
					app.text("A partir del momento en que presiones el siguiente bot�n \n comenzar� el juego", 500,
							300);
					if (btJugar) {
						app.fill(231, 112, 130);
						app.rect(app.width / 2, app.height - 100, 120, 50, 20);
						app.fill(255);
						app.text("JUGAR", app.width / 2, app.height - 90);
						app.noFill();
					}
					break;
				case 4:
					crearMatrizMemoria(4, 3, 3);
					break;
				case 5:
					crearMatrizMemoria(4, 4, 4);
					break;
				case 6:
					crearMatrizMemoria(5, 4, 5);
					break;
				case 7:
					crearMatrizMemoria(5, 5, 6);
					break;
				case 8:
					crearMatrizMemoria(6, 5, 7);
					break;
				case 9:
					crearMatrizMemoria(6, 6, 8);
					break;
				case 10:
					crearMatrizMemoria(6, 6, 9);
					break;
				case 11:
					crearMatrizMemoria(6, 6, 10);
					break;
				case 12:
					crearMatrizMemoria(6, 6, 11);
					break;
				case 13:
					crearMatrizMemoria(6, 6, 12);
					break;
				case 14:
					btJugar = true;
					app.fill(255, 200);
					app.textSize(50);
					app.text("�Haz terminado el \n Componente de Memoria!", app.width / 2, 220);
					app.textSize(20);
					app.text("Acontinuaci�n haz clic para continuar.", app.width / 2, 380);
					if (btJugar) {
						app.fill(231, 112, 130);
						app.rect(app.width / 2, app.height - 100, 120, 50, 20);
						app.fill(255);
						app.text("SEGUIR", app.width / 2, app.height - 90);
						app.noFill();
					}
					break;
				}

				if (dificultad < 3) {

					app.fill(231, 112, 130);
					app.textSize(20);
					app.text("Tutorial", app.width / 2, 60);

					app.fill(255);
					app.textSize(20);
					app.text("Antes de seguir con el pr�ximo ejercicio,\ndebemos aprender como Jugarlo.", app.width / 2,
							120);
					app.fill(231, 112, 130);
					if (btJugar) {
						app.text("Haz clic en JUGAR.", app.width / 2, 220);
					} else {
						app.text("Haz clic sobre las celdas \n que se han encendido.", app.width / 2, 580);
					}
				}

				if (dificultad > 3 && dificultad < 14) {
					if (prendidos - destapados < prendidos) {
						app.fill(231, 112, 130);
						app.textSize(20);
						app.text("A�N FALTAN:" + (prendidos - destapados), 800, 50);
					}

					int intento = dificultad - 3;
					app.text("MATRIZ: " + intento + "/10", 120, 50);
					pintarCronometro(app.width / 2, app.height - 50);
				}
				break;
			}
		}
		}
	}

	private void pintarCronometro(int x, int y) {
		t_intento = t - t_inicial;

		if (segundos == 0) {
			if (minutos == 0) {
				// -----------------------TIEMPO CASO 1 - MATRIZ MEMORIA
				minutos = 1;
			} else {
				minutos--;
			}
			segundos = 59;
		}

		if (t_intento % 1000 <= 15) {
			segundos--;
		}

		app.textSize(20);
		app.fill(255);
		if (segundos >= 10) {
			app.text("0" + minutos + ":" + segundos, x, y);
		} else {
			app.text("0" + minutos + ":" + "0" + segundos, x, y);
		}

		if (minutos == 0 && segundos == 0) {
			if (modoJuego == 0) {
				if (noPregunta < 15) {
					crearMatriz = true;
					modoJuego = 1;
					btJugar = true;
				}
			}
		}
	}

	private void pintarNavegacion() {
		navegacion = new Casilla[4][3];
		navegacionFlechas = new Casilla[2];

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 3; j++) {
				navegacion[i][j] = (new Casilla(912 + i * 25, 646 + j * 25, 25));
				if (PApplet.dist(app.mouseX, app.mouseY, navegacion[i][j].getX(),
						navegacion[i][j].getY()) < navegacion[i][j].getTam() / 2) {
					navegacion[i][j].pintar(app);
				}
				navegacion[i][j].setMostrar(true);
			}
		}

		for (int i = 0; i < 2; i++) {
			navegacionFlechas[i] = (new Casilla(924 + i * 50, 720, 50));
			navegacionFlechas[i].setTam2(25);
			if (PApplet.dist(app.mouseX, app.mouseY, navegacionFlechas[i].getX(),
					navegacionFlechas[i].getY()) < navegacionFlechas[i].getTam2() / 2) {
				navegacionFlechas[i].pintar(app);
			}
			navegacionFlechas[i].setMostrar(true);
		}
	}

	public void press() {
		
		if(gameOver == true) {
			ui.click();
		}else {
		
		System.out.println(puntaje);
		if (entrada) {
			if (btStart) {
				if (app.mouseX > app.width / 2 - btIniciarBase.width / 2
						&& app.mouseX < app.width / 2 + btIniciarBase.width / 2
						&& app.mouseY > (app.height - 100) - (btIniciarBase.height / 2)
						&& app.mouseY < (app.height - 100) + (btIniciarBase.height / 2)) {
					app.imageMode(PConstants.CENTER);
					app.image(btIniciarPress, app.width / 2, app.height - 100);
					app.imageMode(PConstants.CORNER);
					btStart = false;
					btJugar = true;
					entrada = false;

				}
			}
		}

		// ----------------------------------------------CASO 0 - PREGUNTAS ABCD
		if (modoJuego == 0) {
			if (noPregunta > 2 && noPregunta < 14) {
				if (btJugar) {
					if (app.mouseX > app.width / 2 - 50 && app.mouseX < app.width / 2 + 50
							&& app.mouseY > app.height - 125 && app.mouseY < app.height - 75) {
						t_actualizado = t;
						btJugar = false;
						t_inicial = t;
						noPregunta++;
					}
				}
			}

			if (noPregunta == 14) {
				if (btJugar) {
					if (app.mouseX > app.width / 2 - 50 && app.mouseX < app.width / 2 + 50
							&& app.mouseY > app.height - 125 && app.mouseY < app.height - 75) {
						System.out.println(puntaje);
						/*
						 * 
						 * AQU� TERMINA EL MINIJUEGO, AL PRESIONAR EN ESTE BOT�N
						 * DEBER� PASAR A LA ENCUESTA DE AUTOEVALUACI�N.
						 * 
						 * ENTONCES AQU� VA LA LOGICA DE LA AUTOEVALUACI�N
						 * 
						 */
						gameOver = true;
					}
				}
			}

			if (p != null) {
				for (int i = 0; i < p.getRespuestas().size(); i++) {
					Respuesta r = p.getRespuestas().get(i);
					if (PApplet.dist(app.mouseX, app.mouseY, r.getX(), r.getY()) < 35) {
						selRespuesta = r;
					}
				}
			}

			if (navegacion != null) {
				for (int i = 0; i < 4; i++) {
					for (int j = 0; j < 3; j++) {
						if (PApplet.dist(app.mouseX, app.mouseY, navegacion[i][j].getX(),
								navegacion[i][j].getY()) < navegacion[i][j].getTam() / 2) {
							noPregunta = (i + (j * 4)) + 4;

						}
					}
				}

				for (int i = 0; i < 2; i++) {
					if (PApplet.dist(app.mouseX, app.mouseY, navegacionFlechas[i].getX(),
							navegacionFlechas[i].getY()) < navegacionFlechas[i].getTam2() / 2) {
						if (i == 0) {
							if (noPregunta > 4)
								noPregunta--;
						} else {
							if (noPregunta < 15)
								noPregunta++;
						}
					}
				}
			}
		}

		// -------------------------------------------CASO 1 - MATRIZ DE MEMORIA

		if (modoJuego == 1) {
			if (btJugar) {
				if (dificultad != 3) {
					if (app.mouseX > app.width / 2 - 50 && app.mouseX < app.width / 2 + 50
							&& app.mouseY > app.height - 125 && app.mouseY < app.height - 75) {
						contando = true;
						t_actualizado = t;
						btJugar = false;
					}
				} else {
					t_actualizado = t;
					btJugar = false;
					t_inicial = t;
					contando = true;
					dificultad++;
				}
			}
			if (estado == 0) {
				if (casillas != null) {
					for (int i = 0; i < casillas.size(); i++) {
						Casilla c = casillas.get(i);

						if (PApplet.dist(c.getX(), c.getY(), app.mouseX, app.mouseY) < tamCasilla / 2) {
							c.setMostrar(true);
							if (c.isMostrado()) {
								acertadas++;
							}

							destapados++;

						}
					}
				}
			}
		}
		}
	}

	public void dragged() {

		if (selRespuesta != null) {
			selRespuesta.setX(app.mouseX);
			selRespuesta.setY(app.mouseY);
		}
	}

	public void soltar() {
		if (modoJuego == 0) {
			if (selRespuesta != null) {
				if (PApplet.dist(selRespuesta.getX(), selRespuesta.getY(), hotZone.getX(),
						hotZone.getY()) < hotZone.getTam() / 2) {
					if (noPregunta > 2) {
						if (selRespuesta.getTexto().equals(p.getResCorrecta())) {
							if (noPregunta < 7)
								puntaje += 6;
							if (noPregunta == 7 && noPregunta < 10)
								puntaje += 12;
							if (noPregunta == 10 && noPregunta < 13)
								puntaje += 18;
							if (noPregunta == 13 && noPregunta < 16)
								puntaje += 24;
						}
					}
					noPregunta++;
					if (noPregunta > 14) {
						crearMatriz = true;
						modoJuego = 1;
						btJugar = true;
					}
				}
			}
			selRespuesta = null;
		}
	}

	private void crearMatrizMemoria(int columnas, int filas, int prendidos) {
		this.prendidos = prendidos;
		matrixPosX = app.width / 2 - (((columnas * tamCasilla) / 2) - 50);
		matrixPosY = app.height / 2 - (((filas * tamCasilla) / 2) - 50);

		if (crearMatriz) {
			app.delay(500);
			grid = new Casilla[columnas][filas];
			for (int i = 0; i < columnas; i++) {
				for (int j = 0; j < filas; j++) {
					grid[i][j] = (new Casilla(matrixPosX + i * tamCasilla, matrixPosY + j * tamCasilla, tamCasilla));
					casillas.add(grid[i][j]);
				}
			}
			t_actualizado = t;
			crearMatriz = false;
		}

		if (contando) {
			if (estado == 0) {
				for (int i = 0; i < prendidos; i++) {
					int casillaPrendida;
					casillaPrendida = (int) app.random(0, casillas.size());
					Casilla c = casillas.get(casillaPrendida);
					if (!c.isMostrar()) {
						c.setMostrar(true);
					} else {
						prendidos++;
					}
				}
			}

			estado++;

			if (t > t_actualizado + 3000) {
				estado = 0;
				contando = false;
				for (int i = 0; i < casillas.size(); i++) {
					casillas.get(i).setMostrar(false);
				}
			}
		}

		for (int i = 0; i < casillas.size(); i++) {
			casillas.get(i).pintar(app);
		}

		if (dificultad > 3) {
			if (destapados == dificultad - 1) {
				if (acertadas == destapados) {
					switch (dificultad) {
					case 0:
					case 1:
					case 2:
					case 3:
					case 4:
						puntaje += 1;
						break;
					case 5:
						puntaje += 1;
						break;
					case 6:
						puntaje += 2;
						break;
					case 7:
						puntaje += 2;
						break;
					case 8:
						puntaje += 2;
						break;
					case 9:
						puntaje += 4;
						break;
					case 10:
						puntaje += 5;
						break;
					case 11:
						puntaje += 6;
						break;
					case 12:
						puntaje += 8;
						break;
					case 13:
						puntaje += 9;
						break;
					}
				}
				contando = true;
				dificultad++;
				destapados = 0;
				crearMatriz = true;
				casillas.clear();
				t_actualizado = t;
				acertadas = 0;
			}

		} else {
			if (destapados == dificultad + 2) {
				contando = true;
				dificultad++;
				destapados = 0;
				crearMatriz = true;
				casillas.clear();

			}
		}
	}

	public int getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}
	
	public void saveData() {
		TableRow newRow = table.addRow();
		newRow.setString("Tipo", tipoInteligencia);
		newRow.setInt("Puntaje", puntaje);
		newRow.setInt("Autopuntaje", ui.getAutoScore());
		newRow.setInt("Posicion", ui.getPosition());
		System.out.println("Saving CSV");

		app.saveTable(table, "data/new.csv");
		dataSaved = true;
	}
	public boolean getDataSaved() {
		return dataSaved;
	}
}
