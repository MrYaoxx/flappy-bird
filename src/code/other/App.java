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
 * 处理文件的工具类
 * @author Creeper
 *
 */

public class App {
	/**
	 * 读取指定位置的图片
	 * @param path
	 * @return
	 */
	public static BufferedImage getImg (String path) {
		//Read.class找到Read类的路径
		try {
			BufferedImage Img = ImageIO.read(App.class.getResource(path));
			return Img;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 读取指定位置的文件
	 * @param path
	 * @return
	 */
	public static BufferedReader getFile(String path){
		//line = br.readLine()将放出来的数据装到line中
		//读到末尾时返回null
		BufferedReader br = new BufferedReader(new InputStreamReader(App.class.getResourceAsStream(path)));
		return br;
		
	}
	
	/**
	 * 创建文件夹
	 * @param path 路径
	 */
	public static void makeFolder(String path){
		File file=new File(path);
		if(!file.exists()){//如果文件夹不存在
			file.mkdir();//创建文件夹
		}
	}
	
	/**
	 * 写入文件的函数
	 * @param path 路径
	 * @return 
	 */
	public static BufferedWriter writerFile(String path) {
		try{
			//如果没有就会创建该文件
			BufferedWriter bw=new BufferedWriter(new FileWriter(path));
			return bw;
		}catch(IOException e){
			e.printStackTrace();
		}
		return null;
	}
}
