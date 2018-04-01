import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class ChartUtil {

	private static List<String> scores = new ArrayList<String>();

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	static {
		try {
			loadScores();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/** 
	 * 创建数据集合 
	 * @return dataSet 
	 */
	public static CategoryDataset createDataSet() {
		// 实例化DefaultCategoryDataset对象  
		DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
		int i = 0;
		for (String string : scores) {
			i++;
			String[] split = string.split("score");
			dataSet.addValue(Integer.parseInt(split[1]), "得分成绩", "第" + i + "次(" + split[1] + ")");
		}
		// 向数据集合中添加数据  
		return dataSet;
	}

	/**
	 * 加载历史成绩
	 * @throws IOException 
	 */
	private static void loadScores() throws IOException {
		File file = new File("d://scores.db");
		if (file.exists()) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			while (true) {
				String readLine = reader.readLine();
				if (readLine == null) {
					break;
				}
				scores.add(readLine);
			}
			reader.close();
		} else {
			file.createNewFile();
		}
	}

	public static void addScore(Integer score) {
		String format = sdf.format(new Date());
		if (scores.size() >= 10) {//最多十次数据
			scores.remove(0);
		}
		scores.add(format + "score" + score);
		try {
			writeScores();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存成绩到文件
	 * @throws FileNotFoundException 
	 */
	private static void writeScores() throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(new File("d://scores.db"));
		for (String string : scores) {
			pw.println(string);
		}
		pw.flush();
		pw.close();
	}

	/** 
	 * 创建JFreeChart对象 
	 * @return chart 
	 */
	public static JFreeChart createChart() {
		StandardChartTheme standardChartTheme = new StandardChartTheme("CN"); //创建主题样式  
		standardChartTheme.setExtraLargeFont(new Font("隶书", Font.BOLD, 20)); //设置标题字体  
		standardChartTheme.setRegularFont(new Font("宋体", Font.PLAIN, 15)); //设置图例的字体  
		standardChartTheme.setLargeFont(new Font("宋体", Font.PLAIN, 15)); //设置轴向的字体  
		ChartFactory.setChartTheme(standardChartTheme);//设置主题样式  
		// 通过ChartFactory创建JFreeChart  
		JFreeChart chart = ChartFactory.createBarChart(
				"历史成绩统计图", //图表标题  
				"历史成绩", //横轴标题  
				"成绩（分）", //纵轴标题  
				createDataSet(), //数据集合  
				PlotOrientation.VERTICAL, //图表方向  
				true, //是否显示图例标识  
				false, //是否显示tooltips  
				false);//是否支持超链接  
		return chart;
	}

	public static void show() {
		ChartFrame cf = new ChartFrame("历史成绩统计图", createChart());
		cf.pack();
		cf.setVisible(true);
		cf.setLocationRelativeTo(null);
	}
}
