package code.config;

/**
 * ��ȡ�û���
 * @author Creeper
 *
 */

import java.util.ArrayList;
import java.util.List;

public class Player {
	
	//�����Ϣ
	public String name = "";//����
	
	public List<String> bird = new ArrayList<String>();//��
	int use = 0;//ʹ���е���
	
	long[] score = new long[10];//����
	int scoreTmp = 0;
	
	//���
	public long money = 0;
	
	/**
	 * @param name Ҫ���ҵ����
	 */
	public Player(String name) {
		name = this.name;
		
		/* 
		 * //���� BufferedReader br; String s; List<String> line = new
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
		 * if(s.startsWith("<bird>")) {//�� while(++i < line.size()) { s = line.get(i);
		 * 
		 * if(s.startsWith("-#")) { use = i; bird.add(s.substring(2, s.length())); }else
		 * if(s.startsWith("-")) { bird.add(s.substring(1, s.length())); }else if(s !=
		 * "") break; } i--; } else if(s.startsWith("<score>")) {//���� while(++i <
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
	 * �л���ɫ
	 * @param dir ��ɫ�ľ���
	 */
	public void changeBird(int dir) {
		use += dir;
		
		if(use < 0)use = bird.size()-1;
		else if(use >= bird.size())use = 0;
	}
	
	/**
	 * ��ȡ��ɫ
	 * @return
	 */
	public String getBird() {
		return bird.get(use);
	}
	
	/**
	 * ���ӷ���
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
	 * ����
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