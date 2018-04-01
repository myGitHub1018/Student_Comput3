import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.script.ScriptException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Calculator extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton startGame = new JButton("开始测试");//开始测试按钮
	private JButton showScore = new JButton("查看历史成绩");//开始测试按钮
	private JTextField question = new JTextField();//显示当前题目内容
	private JTextField answer = new JTextField();//填写答案的地方
	private JButton nextQuestion = new JButton("下一题");
	private JTextField tips = new JTextField();
	private JTextField time = new JTextField();
	private String currentAnswer = "";
	protected int point = 0;
	private long startTime = 0;

	/**
	 * 界面初始化
	 */
	public Calculator() {
		this.setTitle("测试");
		this.setSize(500, 300);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
		this.initComponet();
		this.bindEvent();
		this.startTimer();
	}

	private void startTimer() {
		new Thread() {
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000);
						long seconds = (new Date().getTime() - startTime) / 1000;
						long min = seconds / 60;
						long second = seconds % 60;

						time.setText("计时: " + (min < 10 ? "0" + min : "" + min) + ":" + (second < 10 ? "0" + second : "" + second));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
		;
	}

	/**
	 * 初始化组件位置与大小
	 */
	public void initComponet() {
		//开始测试按鈕
		startGame.setBounds(10, 10, 100, 30);//位置
		this.add(startGame);

		//题目内容
		question.setBounds(10, 50, 200, 30);//位置
		question.setEditable(false);//不可编辑
		question.setBorder(null);
		question.setHorizontalAlignment(JTextField.RIGHT);//文本居中
		question.setVisible(false);
		this.add(question);

		//填写答案的地方
		answer.setBounds(230, 50, 100, 30);//位置
		answer.setVisible(false);
		this.add(answer);

		//下一题
		nextQuestion.setBounds(340, 50, 80, 30);//位置
		nextQuestion.setVisible(false);
		this.add(nextQuestion);
		//得分提示
		tips.setBounds(120, 10, 100, 30);
		tips.setEditable(false);
		tips.setBorder(null);
		tips.setVisible(false);
		this.add(tips);

		showScore.setBounds(350, 240, 150, 30);
		this.add(showScore);

		time.setBounds(250, 10, 100, 30);
		time.setEditable(false);
		time.setBorder(null);
		time.setVisible(false);
		this.add(time);
	}

	/**
	 * 绑定事件
	 */
	public void bindEvent() {
		//开始测试点击事件
		startGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startGame();
			}
		});
		nextQuestion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String text = answer.getText();
				if (currentAnswer.equals(text)) {
					point += 5;
				}
				doNextQuestion();
			}
		});
		showScore.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ChartUtil.show();
			}
		});
	}

	/**
	 * 开始测试
	 */
	public void startGame() {
		startTime = new Date().getTime();
		time.setText("计时: 00:00");
		startGame.setEnabled(false);
		question.setVisible(true);
		answer.setVisible(true);
		nextQuestion.setVisible(true);
		tips.setVisible(true);
		time.setVisible(true);
		point = 0;
		try {
			Exam.buildQuestions();//随机创建试题
			doNextQuestion();
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}

	public void doNextQuestion() {
		tips.setText("当前得分:" + point + " 分");
		Question q = Exam.getQuestion();
		if (q != null) {
			question.setText(q.getExpress().replaceAll("\\*", "×").replaceAll("/", "÷") + " =");
			currentAnswer = q.getAnswer();
		} else {
			endGame();
		}
	}

	private void endGame() {

		startGame.setEnabled(true);
		question.setVisible(false);
		answer.setVisible(false);
		nextQuestion.setVisible(false);
		tips.setVisible(false);
		time.setVisible(false);
		JOptionPane.showMessageDialog(this, "本次测试得分:" + point + " 分");
		ChartUtil.addScore(point);
	}

	public static void main(String[] args) {
		new Calculator();
	}
}