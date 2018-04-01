public class Question {
	private String express;
	private String answer;
	public String getExpress() {
		return express;
	}
	public void setExpress(String express) {
		this.express = express;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public Question() {
	}
	public Question(String express, String answer) {
		super();
		this.express = express;
		this.answer = answer;
	}
	
}
