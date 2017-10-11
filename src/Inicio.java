import processing.core.PApplet;

public class Inicio {
	
	PApplet app;
	private boolean gameOver;
	
	public Inicio(PApplet _app) {
		app = _app;
		gameOver = false;
	}
	
	public void paint() {
		app.text("Pantalla de Inicio", app.width/2, app.height/2);
		app.rectMode(app.CENTER);
		app.rect(((app.width/2)), ((app.height/3)*2), 200, 30);
		app.fill(255);
		app.text("Iniciar", app.width/2, ((app.height/3)*2)+6);
	}
	
	
	public boolean getGameOver() {
		return gameOver;
	}
	
	//Click method that interacts with each inner class
	public void click() {
		//Method for rect hotZone
		if (app.mouseX >= ((app.width/2)-100) && app.mouseX <= ((app.width/2)+100) && app.mouseY >= ((app.height/3)*2)-15 && app.mouseY <= ((app.height/3)*2)+15) {
			System.out.println("Click HotZone");
			gameOver = true;
		}
	}
}
