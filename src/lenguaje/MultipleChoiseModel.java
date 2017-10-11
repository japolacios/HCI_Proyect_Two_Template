package lenguaje;

import java.util.ArrayList;

public class MultipleChoiseModel {
	
	private String question, answerOne, answerTwo, answerThree, answerFour;
	
	private boolean correct;
	private ArrayList<String> answers;
	
	
	public MultipleChoiseModel(String _question, String _answerOne,String _answerTwo,String _answerThree,String _answerFour) {
		//First Answer is the correct answer
		
		question = _question;
		answerOne = _answerOne;
		answerTwo = _answerTwo;
		answerThree = _answerThree;
		answerFour = _answerFour;
		
		answers = new ArrayList<String>();
		populateData();
	}
	
	public void populateData() {
		answers.add(answerOne);
		answers.add(answerTwo);
		answers.add(answerThree);
		answers.add(answerFour);
	}
	
	public String getQuestion() {
		return question;
	}
	
	public ArrayList gewtAnswerOne() {
		return answers;
	}
	
	
}
