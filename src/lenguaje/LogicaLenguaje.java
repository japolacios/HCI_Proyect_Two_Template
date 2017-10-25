package lenguaje;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.data.Table;
import processing.data.TableRow;

public class LogicaLenguaje {

	private PApplet app;
	private ArrayList<MultipleChoiseModel> multiples;
	private Ui ui;
	private int correct, question, insNum;
	private boolean start, gameOver, dataSaved, released, instrucciones, leer;
	private Table table; // Se recibe desde Ejecutable para guardar los datos
	public String tipoInteligencia = "Inteligencia Linguistica";
	public PImage bg_q;
	public ArrayList<PImage> insImg;
	private PFont[] nunito;

	public LogicaLenguaje(PApplet _app, Table _table) {
		app = _app;
		start = false;
		gameOver = false;
		dataSaved = false;
		correct = 0;
		table = _table;
		multiples = new ArrayList<MultipleChoiseModel>();
		ui = new Ui(app);
		populateMultiple();
		released = true;
		question = 0; // DevPurpose
		bg_q = app.loadImage("./data/lenguaje/fondo_lenguaje.png");
		instrucciones = true;
		insNum = 1;
		insImg = new ArrayList<PImage>();
		populateInstructions();
		nunito = new PFont[3];
		nunito[0] = app.createFont("font/Nunito-Light.ttf", 20);
		nunito[1] = app.createFont("font/Nunito-Regular.ttf", 20);
		nunito[2] = app.createFont("font/Nunito-Bold.ttf", 20);
	}

	public void populateInstructions() {
		PImage temp1 = app.loadImage("./data/lenguaje/analogias.jpg");
		PImage temp2 = app.loadImage("./data/lenguaje/comprension.jpg");
		PImage temp3 = app.loadImage("./data/lenguaje/palabras.jpg");
		PImage temp4 = app.loadImage("./data/lenguaje/vocabulario.jpg");
		PImage temp5 = app.loadImage("./data/lenguaje/parrafo.jpg");
		insImg.add(temp1);
		insImg.add(temp2);
		insImg.add(temp3);
		insImg.add(temp4);
		insImg.add(temp5);
	}

	public void paint() {
		app.textSize(15);
		app.textFont(nunito[2]);
		if (start == false) {
			app.text("Pruebas de Lenguaje", app.width / 2, app.height / 2);
		} else {
			if (start == true && gameOver == false) {
				app.image(bg_q, 0, 0);

				if (instrucciones == true) {
					if (insNum == 1) {
						app.image(insImg.get(0), 0, 0);
					}
					if (insNum == 2) {
						app.image(insImg.get(1), 0, 0);
						if (leer == true) {
							app.image(insImg.get(4), 0, 0);
						}
					}
					if (insNum == 3) {
						app.image(insImg.get(2), 0, 0);
					}
					if (insNum == 4) {
						app.image(insImg.get(3), 0, 0);
					}
				} else {
					if (question <= 18) {
						paintQuestions(question);
					}
				}
			}
			if (gameOver == true) {
				ui.paint();
				if (ui.getDoneHere() == true) {
					saveData();
				}
			}
		}
	}

	public void paintQuestions(int _q) {
		app.fill(255);

		app.text(multiples.get(_q).getQuestion(), (app.width / 2), (app.height / 4));

		app.text((String) multiples.get(_q).gewtAnswerOne().get(0), (app.width / 3), (app.height / 4) * 2);
		app.text((String) multiples.get(_q).gewtAnswerOne().get(1), (app.width / 3), (app.height / 4) * 3);
		app.text((String) multiples.get(_q).gewtAnswerOne().get(2), (app.width / 3) * 2, (app.height / 4) * 2);
		app.text((String) multiples.get(_q).gewtAnswerOne().get(3), (app.width / 3) * 2, (app.height / 4) * 3);
		
	}

	public void populateMultiple() {
		System.out.println("Creating Questions DataBase");
		// Correcta 1
		MultipleChoiseModel q1 = new MultipleChoiseModel("Internet es a comunicar, como carro es a:", "Transportar",
				"Correr", "Impuestos", "Alcohol");
		// Correcta 2
		MultipleChoiseModel q2 = new MultipleChoiseModel("Sentar es a silla, como acostar es a:", "Mesa", "Cama",
				"Asiento", "Sala");
		// correcta 1
		MultipleChoiseModel q3 = new MultipleChoiseModel("Aleta es a pez, como una mano es a:", "Humano", "Cerdo",
				"Pájaro", "Anfibio");
		// correcta 2
		MultipleChoiseModel q4 = new MultipleChoiseModel("Volar es a cielo, cómo caer a:", "Pared", "Suelo", "Patio",
				"Techo");
		// correcta 4
		MultipleChoiseModel q5 = new MultipleChoiseModel("Comida es a restaurante, como libro es a:", "Hospital",
				"Supermercado", "Aeropuerto", "Biblioteca");
		// correcta 1
		MultipleChoiseModel q6 = new MultipleChoiseModel("Leche es a vaca como, Lana es a:", "Oveja", "Abeja", "Salmon",
				"Navaja");
		// correcta 4
		MultipleChoiseModel q7 = new MultipleChoiseModel("Luna es a noche, como sol es a:", "Nube", "Eclipse",
				"Estrella", "Día");

		// Breakpoint
		// correcta 2
		MultipleChoiseModel q8 = new MultipleChoiseModel("¿De qué nos habla el texto? Elige la respuesta más correcta.",
				"Del jardín de la casa", "Del cuarto de baño de la casa del molinero", "De la chimenea",
				"De la casa del molinero");
		// correcta 3
		MultipleChoiseModel q9 = new MultipleChoiseModel("¿Cómo era el lavamanos del cuarto de baño?", "Acogedor ",
				"Mármol", "Blanco", "Blanco con forma de serpiente");
		// correcta 3
		MultipleChoiseModel q10 = new MultipleChoiseModel("¿Dónde estaba el jardín?",
				"En la parte delantera de la casa", "No tiene jardín", "En la parte trasera de la casa.",
				"En el patio interior");

		// Breakpoint
		// correcta 2
		MultipleChoiseModel q11 = new MultipleChoiseModel("aamnadirn", "Idioma", "Fruta", "Animal", "Color");
		// correcta 1
		MultipleChoiseModel q12 = new MultipleChoiseModel("oeoenltf", "Objeto", "Pais", "Ciudad", "Numero");
		// correcta 4
		MultipleChoiseModel q13 = new MultipleChoiseModel("uanighr", "Animal", "Color", "Pronombre", "Pais");
		// correcta 1
		MultipleChoiseModel q14 = new MultipleChoiseModel("ppurrau", "Color", "Objeto", "Pais", "Ciudad");
		// correcta 3
		MultipleChoiseModel q15 = new MultipleChoiseModel("aensito", "Pais", "Animal", "Objeto", "Color");

		// BreakPoint
		// correcta 4
		MultipleChoiseModel q16 = new MultipleChoiseModel("Fuimos a comer donde ___ mariscos con chocolate", "Ahí",
				"Ay", "Hai", "Hay");
		// correcta 4
		MultipleChoiseModel q17 = new MultipleChoiseModel(
				"Sinonimo de nostalgia:  -palabras parecidas pero que no son cotidianas", "Arañazo", "Añuranza",
				"Aruñanza", "Añoranza");
		// correcta 1
		MultipleChoiseModel q18 = new MultipleChoiseModel("Los diferentes tipos de antónimos son:",
				"Gradual, complementario, recíproco", "Complementario, negador, sinónimo",
				"Inequívoco, gradual, igualitario", "Recíproco, complementario, nulo");
		// correcta 3
		MultipleChoiseModel q19 = new MultipleChoiseModel("Un verbo indica un:", "Acción, operación, nombre",
				"Descripción, acción, operación", "Acción, proceso, estado", "Actuacion, nombre, estado");

		multiples.add(q1);
		multiples.add(q2);
		multiples.add(q3);
		multiples.add(q4);
		multiples.add(q5);
		multiples.add(q6);
		multiples.add(q7);
		multiples.add(q8);
		multiples.add(q9);
		multiples.add(q10);
		multiples.add(q11);
		multiples.add(q12);
		multiples.add(q13);
		multiples.add(q14);
		multiples.add(q15);
		multiples.add(q16);
		multiples.add(q17);
		multiples.add(q18);
		multiples.add(q19);
		start = true;
	}

	public void saveData() {
		int score =(int) (correct/question)*100;
		TableRow newRow = table.addRow();
		newRow.setString("Tipo", tipoInteligencia);
		newRow.setInt("Puntaje",  correct);
		newRow.setInt("Autopuntaje", ui.getAutoScore());
		newRow.setInt("Posicion", ui.getPosition());
		System.out.println("Saving CSV");

		app.saveTable(table, "data/new.csv");
		dataSaved = true;
	}

	public void click() {
//		System.out.println("Puntaje: " + correct + " - Question: " +question);
		if (gameOver == false) {
			if (released == true) {
				if (instrucciones == true && leer == false) {
					System.out.println("Click en Instrucciones: " + instrucciones + " -Question: " + question);
					released = false;
					instrucciones = false;
					System.out.println("insturcciones False");	
					return;
				}
				if (leer == true) {
					released = false;
					leer = false;
					return;
				} else {
					
					if (question == 0) {
						if (app.dist(app.mouseX, app.mouseY, app.width / 3, (app.height / 4) * 2) <= 100) {
							released = false;
							question++;
							correct++;
							insNum++;
							return;
						}
					}
					if (question == 1) {
						if (app.dist(app.mouseX, app.mouseY, (app.width / 3), (app.height / 4) * 3) <= 100) {
							question++;
							correct++;
							released = false;
							return;
						}
					}
					if (question == 2) {
						if (app.dist(app.mouseX, app.mouseY, app.width / 3, (app.height / 4) * 2) <= 100) {
							question++;
							correct++;
							released = false;
							return;
						}
					}
					if (question == 3) {
						if (app.dist(app.mouseX, app.mouseY, (app.width / 3), (app.height / 4) * 3) <= 100) {
							question++;
							correct++;
							released = false;
							return;
						}
					}
					if (question == 4) {
						if (app.dist(app.mouseX, app.mouseY, (app.width / 3) * 2, (app.height / 4) * 3) <= 100) {
							question++;
							correct++;
							released = false;
							return;
						}
					}
					if (question == 5) {
						if (app.dist(app.mouseX, app.mouseY, app.width / 3, (app.height / 4) * 2) <= 100) {
							question++;
							correct++;
							released = false;
							return;
						}
					}
					if (question == 6) {
						if (app.dist(app.mouseX, app.mouseY, (app.width / 3) * 2, (app.height / 4) * 3) <= 100) {
							question++;
							correct++;
							insNum=2;
							released = false;
							instrucciones = true;
							leer= true;
							return;
						}
					}
					if (question == 7) {
						if (app.dist(app.mouseX, app.mouseY, (app.width / 3) * 2, (app.height / 4) * 3) <= 100) {
							question++;
							correct++;
							released = false;
							
							System.out.println("insturcciones True");
							return;
						}

						// BreakPoint
					}
					if (question == 8) {
						if (app.dist(app.mouseX, app.mouseY, app.width / 3, (app.height / 4) * 2) <= 100) {
							question++;
							correct++;
							released = false;
							insNum++;
							return;
						}
					}
					if (question == 9) {
						if (app.dist(app.mouseX, app.mouseY, app.width / 3, (app.height / 4) * 2) <= 100) {
							question++;
							correct++;
							released = false;
							return;
						}
					}
					
					// BreakPoint
					if (question == 10) {
						if (app.dist(app.mouseX, app.mouseY, (app.width / 3), (app.height / 4) * 3) <= 100) {

							correct++;
							released = false;
							instrucciones = true;
							System.out.println("insturcciones True");
							return;
						}


					}
					if (question == 11) {
						if (app.dist(app.mouseX, app.mouseY, app.width / 3, (app.height / 4) * 2) <= 100) {
							question++;
							correct++;
							released = false;
							insNum=3;
							return;
						}
					}
					if (question == 12) {
						if (app.dist(app.mouseX, app.mouseY, (app.width / 3) * 2, (app.height / 4) * 3) <= 100) {
							question++;
							correct++;
							released = false;
							return;
						}
					}
					if (question == 13) {
						if (app.dist(app.mouseX, app.mouseY, app.width / 3, (app.height / 4) * 2) <= 100) {
							question++;
							correct++;
							released = false;
							return;
						}
					}
					if (question == 14) {
						if (app.dist(app.mouseX, app.mouseY, app.width / 3, (app.height / 4) * 2) <= 100) {
							question++;
							correct++;
							released = false;

							return;
						}
						if (question == 15) {
							if (app.dist(app.mouseX, app.mouseY, app.width / 3, (app.height / 4) * 2) <= 100) {

								correct++;
								released = false;
								instrucciones = true;
								insNum=4;
								System.out.println("Que sucede aqui¿?");
								return;
							}

							// BreakPoint
						}
						if (question == 16) {
							if (app.dist(app.mouseX, app.mouseY, (app.width / 3) * 2, (app.height / 4) * 3) <= 100) {
								question++;
								correct++;
								released = false;
								System.out.println("Porque no llega?");
								return;
							}
						}
						if (question == 17) {
							if (app.dist(app.mouseX, app.mouseY, (app.width / 3) * 2, (app.height / 4) * 3) <= 100) {
								question++;
								correct++;
								released = false;
								return;
							}
						}
						if (question == 18) {
							if (app.dist(app.mouseX, app.mouseY, app.width / 3, (app.height / 4) * 2) <= 100) {
								question++;
								correct++;
								released = false;
								return;
							}
						}
							if (question == 19) {
								if (app.dist(app.mouseX, app.mouseY, app.width / 3, (app.height / 4) * 2) <= 100) {
									// question++;
									correct++;
									released = false;
									instrucciones = false;
									gameOver = true;
									System.out.println("LastOne");
									return;
								}
							}
						else {
							System.out.println("LastOne");
							if(question >= 19) {
							gameOver = true;
							}
							return;
						}
					} else {
						if(question>=19) {
							gameOver= true;
							released = false;
							return;
						}
						if(question == 6) {
							question++;
							instrucciones = true;
							insNum = 2;
							leer = true;
							return;
						}
						if(question == 10) {
							question++;
							instrucciones = true;
							insNum = 3;
							return;
							
						}
						if(question == 15) {
							question++;
							instrucciones = true;
							insNum = 4;
							return;
						}
						question++;
						released = false;
						return;
						
					}
				
			}
		}
		
		}
		if (gameOver == true) {
			ui.click();
		}
	}

	public void released() {
		released = true;
		// System.out.println("Really released");
	}

	public boolean getDataSaved() {
		return dataSaved;
	}
}
