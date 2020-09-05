package code.config;

/**
 * 获取用户类
 * @author Creeper
 *
 */

import java.util.ArrayList;
import java.util.List;

public class Player {
	
	//玩家信息
	public String name = "";//名称
	
	public List<String> bird = new ArrayList<String>();//鸟
	int use = 0;//使用中的鸟
	
	long[] score = new long[10];//分数
	int scoreTmp = 0;
	
	//金币
	public long money = 0;
	
	/**
	 * @param name 要查找的玩家
	 */
	public Player(String name) {
		name = this.name;
		
		/* 
		 * //读入 BufferedReader br; String s; List<String> line = new
		 * ArrayList<String>();
		 * 
		 * br =
		 * App.getFileAbsolute("D:\\Creeper\\flappy_bird\\player\\" + this.name + ".fbp"
		 * );
		 * 
		 * try { while((s = br.readLine()) != null) { line.add(s); } } catch
		 * (IOException e) { e.printStackTrace(); }
		 * 
		 * for(int i = 0 ;i < line.size() ;i++) { s = line.get(i);
		 * 
		 * if(s.startsWith("<bird>")) {//鸟 while(++i < line.size()) { s = line.get(i);
		 * 
		 * if(s.startsWith("-#")) { use = i; bird.add(s.substring(2, s.length())); }else
		 * if(s.startsWith("-")) { bird.add(s.substring(1, s.length())); }else if(s !=
		 * "") break; } i--; } else if(s.startsWith("<score>")) {//分数 while(++i <
		 * line.size()) { s = line.get(i);
		 * 
		 * if(s.startsWith("-sc=")) { score[scoreTmp++] = Integer.parseInt(s.substring(4
		 * ,s.length())); if(scoreTmp>=10)break; } } } }
		 */

		bird.add("blue");
		bird.add("yellow");
		bird.add("red");
		use = 0;
	}
	
	/**
	 * 切换角色
	 * @param dir 角色的距离
	 */
	public void changeBird(int dir) {
		use += dir;
		
		if(use < 0)use = bird.size()-1;
		else if(use >= bird.size())use = 0;
	}
	
	/**
	 * 获取角色
	 * @return
	 */
	public String getBird() {
		return bird.get(use);
	}
	
	/**
	 * 增加分数
	 * @param sc
	 */
	public void addScore(long sc) {
		if(scoreTmp >= 10) {
			for(int i=0;i<10-1;i++)score[i] = score[i+1];
			scoreTmp--;
		}
		score[scoreTmp] = sc;
	}
	
	/**
	 * 保存
	 */
	public void save() {
		/*BufferedWriter bw = App.writerFile("D:\\Creeper\\flappy_bird\\player\\" + name + ".fbp");
		
		try {
			bw.write("<bird>");
			for(int i=0;i<bird.size();i++)bw.write("-" + (use==i ? "#" : "") + bird.get(i) + "\n");
			bw.write("\n<score>");
			
			bw.close();
			for(int i=0;i<10;i++)bw.write("-" + score[i] + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}
}