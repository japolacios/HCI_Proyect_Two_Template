package Balanzas;
import processing.core.PApplet;

public class Ui {
	private PApplet app;
	private boolean doneHere;
	int autoScore, position;
	public Ui(PApplet _app) {
		app = _app;
		doneHere = false;
		autoScore = 3;
		position = 9;
	}
	
	public void paint() {
		//Here we paint the UI
		app.text("Como crees que te fue en la prueba", app.width/2, app.height/3);
		for (int i = 0; i < 10; i++) {
			app.fill(0);
			app.rect(((app.width/10)*i)+((app.width/10)/2), (app.height/3)+50, app.width/10, 50);
			app.fill(255);
			app.text(i+1, ((app.width/10)*i)+((app.width/10)/2), (app.height/3)+50);
		}
		app.fill(0);
		app.text("Como crees que estas con respecto al resto de participantes", app.width/2, (app.height/3)*2);
		for (int i = 0; i < 10; i++) {
			app.fill(0);
			app.rect(((app.width/10)*i)+((app.width/10)/2), (app.height/3)*2+50, app.width/10, 50);
			app.fill(255);
			app.text(i+1, ((app.width/10)*i)+((app.width/10)/2), (app.height/3)*2+50);
		}
	}
	
	public int getAutoScore() {
		return autoScore;
	}
	
	public int getPosition() {
		return position;
	}
	
	public boolean getDoneHere() {
		return doneHere;
	}
	
	public void click() {
		//For next Minigame and saveData
		if(app.mouseX >= 1000) {
			doneHere = true;
		}
	}
	
	
}
