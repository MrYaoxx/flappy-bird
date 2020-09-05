package code.other;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;


/**
 * �����ļ��Ĺ�����
 * @author Creeper
 *
 */

public class App {
	/**
	 * ��ȡָ��λ�õ�ͼƬ
	 * @param path
	 * @return
	 */
	public static BufferedImage getImg (String path) {
		//Read.class�ҵ�Read���·��
		try {
			BufferedImage Img = ImageIO.read(App.class.getResource(path));
			return Img;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ��ȡָ��λ�õ��ļ�
	 * @param path
	 * @return
	 */
	public static BufferedReader getFile(String path){
		//line = br.readLine()���ų���������װ��line��
		//����ĩβʱ����null
		BufferedReader br = new BufferedReader(new InputStreamReader(App.class.getResourceAsStream(path)));
		return br;
		
	}
	
	/**
	 * �����ļ���
	 * @param path ·��
	 */
	public static void makeFolder(String path){
		File file=new File(path);
		if(!file.exists()){//����ļ��в�����
			file.mkdir();//�����ļ���
		}
	}
	
	/**
	 * д���ļ��ĺ���
	 * @param path ·��
	 * @return 
	 */
	public static BufferedWriter writerFile(String path) {
		try{
			//���û�оͻᴴ�����ļ�
			BufferedWriter bw=new BufferedWriter(new FileWriter(path));
			return bw;
		}catch(IOException e){
			e.printStackTrace();
		}
		return null;
	}
}
