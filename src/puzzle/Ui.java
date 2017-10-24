package puzzle;
import processing.core.PApplet;

public class Ui {
	private PApplet app;
	private boolean doneHere, first,second;
	int autoScore, position;
	public Ui(PApplet _app) {
		app = _app;
		doneHere = false;
		first= false;
		second = false;
		autoScore = 5;
		position = 5;
	}
	
	public void paint() {
		//Here we paint the UI
		app.text("Como crees que te fue en la prueba", app.width/2, app.height/3);
		for (int i = 0; i < 10; i++) {
			app.fill(230,112,129);
			app.rect(((app.width/10)*i)+((app.width/10)/2), (app.height/3)+50, app.width/10, 50);
			app.fill(255);
			app.text(i+1, ((app.width/10)*i)+((app.width/10)/2), (app.height/3)+50);
		}
		app.fill(0);
		app.text("Como crees que estas con respecto al resto de participantes", app.width/2, (app.height/3)*2);
		for (int i = 0; i < 10; i++) {
			app.fill(230,112,129);
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
		int x = app.mouseX;
		if(app.mouseY >= app.height/3 && app.mouseY <= (app.height/3)+100) {
			for (int i = 0; i < 10; i++) {
				if(x < (app.width/10)+((app.width/10)*i) && x >= ((app.width/10)*i) ) {
					System.out.println(i+1);
					autoScore = i+1;
					first = true;
				}
			}
		}
		
		if(app.mouseY >= (app.height/3)*2 && app.mouseY <= (app.height/3)*2+100) {
			for (int i = 0; i < 10; i++) {
				if(x < (app.width/10)+((app.width/10)*i) && x >= ((app.width/10)*i) ) {
					System.out.println(i+1);
					position = i+1;
					second = true;
				}
			}
		}
		
		
		if(first == true && second == true) {
			doneHere = true;
		}
	}
	
	
}
