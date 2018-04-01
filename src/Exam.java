import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Exam {
	private static final ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");

	private static final Random random = new Random();

	private static final int QUESTION_COUNT = 20;//20 道题目

	public static final String[] operators = { "+", "-", "*", "/" };

	public static final List<Question> questions = new ArrayList<Question>();

	/**
	 * 生成算术题
	 * @throws ScriptException 
	 */
	public static void buildQuestions() throws ScriptException {
		questions.clear();
		int a = 0;//运算式的第一个随机数
		int b = 0;//运算式的第二个随机数
		int c = 0;//运算式的第三个随机数
		int d = 0;//运算式的第四个随机数
		int e = 0;//运算式的第五个随机数

		int num = 0;//结果
		String q = ""; //保存运算式
		Random random = new Random();
		int i = 0;
		while (i < QUESTION_COUNT) {
			a = (int) (Math.random() * 100 + 1);
			b = (int) (Math.random() * 100 + 1);
			c = (int) (Math.random() * 100 + 1);
			d = (int) (Math.random() * 100 + 1);
			e = (int) (Math.random() * 100 + 1);

			int z = random.nextInt(5) % (5 - 3 + 1) + 3;//产生一个3-5之间的随机数，用来设定运算式的数值个数		
			if (z == 3) {
				//3个数，则产生两个运算符
				int A = random.nextInt(4) % (4 - 1 + 1) + 1;
				switch (A) {
				case 1:
					num = a + b;
					q = a + "+" + b;
					break;
				case 2:
					num = a - b;
					q = a + "-" + b;
					break;
				case 3:
					num = a * b;
					q = a + "*" + b;
					break;
				default:
					num = a / b;
					q = a + "/" + b;
					break;
				}
				int B = random.nextInt(4) % (4 - 1 + 1) + 1;
				switch (B) {
				case 1:
					num = num + c;
					q = q + "+" + c;
					break;
				case 2:
					num = num - c;
					q = q + "-" + c;
					break;
				case 3:
					num = num * c;
					q = q + "*" + c;
					break;
				default:
					num = num / c;
					q = q + "/" + c;
					break;
				}
			}

			if (z == 4) {
				//4个数，则产生三个运算符
				int A = random.nextInt(4) % (4 - 1 + 1) + 1;
				switch (A) {
				case 1:
					num = a + b;
					q = a + "+" + b;
					break;
				case 2:
					num = a - b;
					q = a + "-" + b;
					break;
				case 3:
					num = a * b;
					q = a + "*" + b;
					break;
				default:
					num = a / b;
					q = a + "/" + b;
					break;
				}
				int B = random.nextInt(4) % (4 - 1 + 1) + 1;
				switch (B) {
				case 1:
					num = num + c;
					q = q + "+" + c;
					break;
				case 2:
					num = num - c;
					q = q + "-" + c;
					break;
				case 3:
					num = num * c;
					q = q + "*" + c;
					break;
				default:
					num = num / c;
					q = q + "/" + c;
					break;
				}

				int C = random.nextInt(4) % (4 - 1 + 1) + 1;
				switch (C) {
				case 1:
					num = num + d;
					q = q + "+" + d;
					break;
				case 2:
					num = num - d;
					q = q + "-" + d;
					break;
				case 3:
					num = num * d;
					q = q + "*" + d;
					break;
				default:
					num = num / d;
					q = q + "/" + d;
					break;
				}
			}

			if (z == 5) {
				//5个数，则产生4个运算符
				int A = random.nextInt(4) % (4 - 1 + 1) + 1;
				switch (A) {
				case 1:
					num = a + b;
					q = a + "+" + b;
					break;
				case 2:
					num = a - b;
					q = a + "-" + b;
					break;
				case 3:
					num = a * b;
					q = a + "*" + b;
					break;
				default:
					num = a / b;
					q = a + "/" + b;
					break;
				}
				int B = random.nextInt(4) % (4 - 1 + 1) + 1;
				switch (B) {
				case 1:
					num = num + c;
					q = q + "+" + c;
					break;
				case 2:
					num = num - c;
					q = q + "-" + c;
					break;
				case 3:
					num = num * c;
					q = q + "*" + c;
					break;
				default:
					num = num / c;
					q = q + "/" + c;
					break;
				}
				int C = random.nextInt(4) % (4 - 1 + 1) + 1;
				switch (C) {
				case 1:
					num = num + d;
					q = q + "+" + d;
					break;
				case 2:
					num = num - d;
					q = q + "-" + d;
					break;
				case 3:
					num = num * d;
					q = q + "*" + d;
					break;
				default:
					num = num / d;
					q = q + "/" + d;
					break;
				}
				int D = random.nextInt(4) % (4 - 1 + 1) + 1;
				switch (D) {
				case 1:
					q = q + "+" + e;
					break;
				case 2:
					q = q + "-" + e;
					break;
				case 3:
					q = q + "*" + e;
					break;
				default:
					num = num / d;
					q = q + "/" + e;
					break;
				}
			}
			String hush = String.valueOf(jse.eval(q));
			Double result = Double.parseDouble(hush);

			if (result < 0 || result % 1 != 0) {
				continue;
			} //结果不能为负数和非整数
			questions.add(new Question(q, jse.eval(q).toString()));
			q = "";
			result = (double) 0;
			i++; //重新置为空，重新开始
		}

	}

	public static int getSun(int number) {
		List<Integer> suns = new ArrayList<Integer>();
		for (int i = 1; i <= number; i++) {
			if (i % number == 0) {
				suns.add(i);
			}
		}
		return suns.get(random.nextInt(suns.size()));
	}

	public static void main(String[] args) throws ScriptException {
		buildQuestions();
	}

	public static Question getQuestion() {
		return questions.size() == 0 ? null : questions.remove(0);
	}
}
