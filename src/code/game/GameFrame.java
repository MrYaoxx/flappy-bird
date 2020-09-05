package code.game;

import javax.swing.JFrame;

/**
 * ������Ϣ
 * @author Creeper
 *
 */

@SuppressWarnings("serial")
public class GameFrame extends JFrame{
	public GameFrame() {
		//����
		setTitle(Window.title);
		
		//��С
		setSize(Window.w ,Window.h);
		
		//����
		setLocationRelativeTo(null);
		
		//������ı䴰���С
		setResizable(false);
		
		//�رճ���ʱ��������
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String[] args) {
		//����������󣺵��ô���ķ���
		GameFrame frame = new GameFrame();
		//��ʼ
		GamePanel panel = new GamePanel(frame);
		//��ʾ����
		frame.setVisible(true);
		//���������󣺵������ķ���
		frame.add(panel);
	}
}
