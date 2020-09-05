package code.game;

import javax.swing.JFrame;

/**
 * 窗体信息
 * @author Creeper
 *
 */

@SuppressWarnings("serial")
public class GameFrame extends JFrame{
	public GameFrame() {
		//标题
		setTitle(Window.title);
		
		//大小
		setSize(Window.w ,Window.h);
		
		//居中
		setLocationRelativeTo(null);
		
		//不允许改变窗体大小
		setResizable(false);
		
		//关闭程序时结束程序
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String[] args) {
		//创建窗体对象：调用窗体的方法
		GameFrame frame = new GameFrame();
		//开始
		GamePanel panel = new GamePanel(frame);
		//显示窗体
		frame.setVisible(true);
		//创建面板对象：调用面板的方法
		frame.add(panel);
	}
}
