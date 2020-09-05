package code.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import code.other.App;

/**
 * 地图类
 * @author Creeper
 *
 */

public class Map {
	//地图路径
	String path = "/data/map.fbm";
	
	//地图数量
	public int mapNum = 0;
	
	//地图路径
	public List<String> mapPath = new ArrayList<String>();
	
	//地图名称
	public List<String> name = new ArrayList<String>();
	
	//读取地图信息
	BufferedReader rd = App.getFile(path);
	
	//读取到的内容
	String info;
	
	public Map() {
		try {
			while((info = rd.readLine()) != null) {
				//获取地图路径
				if(info.startsWith("<") && info.endsWith(">")) {
					mapPath.add(info.substring(1 ,info.length() - 1));
					
					mapNum ++;
				}
				
				//获取名称
				if(info.startsWith("-name=")) {
					name.add(info.substring(6 ,info.length()));
				}
			}

			//关闭
			rd.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
