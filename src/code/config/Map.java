package code.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import code.other.App;

/**
 * ��ͼ��
 * @author Creeper
 *
 */

public class Map {
	//��ͼ·��
	String path = "/data/map.fbm";
	
	//��ͼ����
	public int mapNum = 0;
	
	//��ͼ·��
	public List<String> mapPath = new ArrayList<String>();
	
	//��ͼ����
	public List<String> name = new ArrayList<String>();
	
	//��ȡ��ͼ��Ϣ
	BufferedReader rd = App.getFile(path);
	
	//��ȡ��������
	String info;
	
	public Map() {
		try {
			while((info = rd.readLine()) != null) {
				//��ȡ��ͼ·��
				if(info.startsWith("<") && info.endsWith(">")) {
					mapPath.add(info.substring(1 ,info.length() - 1));
					
					mapNum ++;
				}
				
				//��ȡ����
				if(info.startsWith("-name=")) {
					name.add(info.substring(6 ,info.length()));
				}
			}

			//�ر�
			rd.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
