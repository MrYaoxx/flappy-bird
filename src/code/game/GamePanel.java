package code.game;

import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

import code.other.Maths;
import code.config.Path;
import code.config.Player;
import code.other.App;
import code.config.Map;

@SuppressWarnings("serial")
public class GamePanel extends JPanel{
	//游戏状态
	/*
	 * home:首页
	 * ready:准备
	 * game:开始
	 * die:死亡
	 */
	String condition;
	
	//玩家信息
	List<Player> pl = new ArrayList<Player>();
	int usePl = 0;//当前玩家
	
	//图片
	BufferedImage fb,//Flappy Bird图标
				bird,//鸟
				mny,//金币
				rd,//准备
				st,//开始
				rk,//排行榜
				left,//左箭头
				right,//右箭头
				go,//GameOver图标
				medalList,//奖章菜单
				medal1,//金奖
				medal2,//银奖
				medal3,//铜奖
				ps,//暂停
				con;//继续
	BufferedImage[] num;//数字
	Thing thing = new Thing();//装扮
	
	//鸟的血量
	int life = Default.life;
	
	//鸟的分数
	long score = 0;
	
	//鸟飞过的距离
	long dis = Default.dis;
	
	//鸟是否加速下降
	boolean isLand = false;
	
	//鸟是否在跳跃
	boolean isJump = false;
	
	//鸟翅膀现在的状态(第几张图片)
	int now = 1;
	
	//鸟翅膀上一次的状态
	int last = 2;
	
	//鸟的皮肤
	String skin;
	
	//鸟的所有图片
	BufferedImage img1;
	BufferedImage img2;
	BufferedImage img3;
	
	//鸟的坐标和大小
	int x = Default.x;//x坐标始终在中间
	int y = Default.y;
	int w ,h;
	
	//暂停
	boolean pause = false;

	//关卡
		//地图
		Map map = new Map();
		
		//关卡编号
		int mapId = 0;
		
		//背景
		BufferedImage bg;
		
		//背景坐标
		int bg1Pos = 0 , bg2Pos = Window.w - 1;
		
		//管道
			//图片
			BufferedImage pipe;
			
			//管道位置
			int[] pipeup = new int[10];//上方的管道
			int[] pipedown = new int[10];//下方的管道
			int[] pipex = new int[10];//管道y坐标
	
		//地面
		BufferedImage ground;

		//地面坐标
		int gd1Pos = 0 , gd2Pos = Window.w;
	
	//鸟还要跳跃几次
	int restJump = 0;
	
	//线程
	Thread birdFly = new Thread(){
		public void run(){
				while(true) {
					//切换鸟的状态
					if(now==1) {
						if(last==2)now = 3;
						else now = 2;
						last = 1;
					} else {
						last = now;
						now = 1;
					}
					//切换鸟的图片
					bird = now==1 ? img1 : (now==2 ? img2 : img3);
				
					//重绘
					repaint();
					
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
		}
	};//鸟飞行的线程
	
	
	public GamePanel(GameFrame frame) {
		//初始化
		//状态
		condition = "home";
		//图片
		bg = App.getImg(Path.bg);
		fb = App.getImg(Path.fb);
		mny = App.getImg(Path.mny);
		rd = App.getImg(Path.rd);
		st = App.getImg(Path.st);
		rk = App.getImg(Path.rk);
		left = App.getImg(Path.left);
		right = App.getImg(Path.right);
		go = App.getImg(Path.go);
		medalList = App.getImg(Path.mL);
		medal1 = App.getImg(Path.m1);
		medal2 = App.getImg(Path.m2);
		medal3 = App.getImg(Path.m3);
		ps = App.getImg(Path.ps);
		con = App.getImg(Path.con);
		num = new BufferedImage[] {
			App.getImg(Path.num[0]),
			App.getImg(Path.num[1]),
			App.getImg(Path.num[2]),
			App.getImg(Path.num[3]),
			App.getImg(Path.num[4]),
			App.getImg(Path.num[5]),
			App.getImg(Path.num[6]),
			App.getImg(Path.num[7]),
			App.getImg(Path.num[8]),
			App.getImg(Path.num[9])
		};
		//玩家
		/* BufferedReader br = App.getFileAbsolute("D:\\Creeper\\flappy_bird\\player\\name.ini");
		 *
		 * try { String s = br.readLine(); if(s.startsWith("#")) { s = s.substring(1
		 * ,s.length()); usePl = pl.size(); }
		 * 
		 * //pl.add(new Player("admin")); } catch (IOException e) { e.printStackTrace();
		 * }
		 */
		pl.add(new Player("admin"));
		//皮肤
		skin = pl.get(usePl).getBird();
		img1 = App.getImg("/img/bird/" + skin + "/1.png");
		img2 = App.getImg("/img/bird/" + skin + "/2.png");
		img3 = App.getImg("/img/bird/" + skin + "/3.png");
		w = img1.getWidth();
		h = img1.getHeight();
		//线程
		birdFly.start();
		

		//鼠标监听器
		MouseAdapter mouseAd = new MouseAdapter() {
			//按下
			public void mousePressed(MouseEvent e) {
				int mx = e.getX() ,my = e.getY(),//鼠标的坐标
					bt = e.getButton();//鼠标的按键
				
				if(condition == "home") {
					if(bt == MouseEvent.BUTTON1 && touch(mx ,my ,1 ,1 ,Window.w/2 - st.getWidth() - 10 ,350 ,st.getWidth() ,st.getHeight())) {
						//开始
						start();
					}else if(bt == MouseEvent.BUTTON1 && touch(mx ,my ,1 ,1 ,Window.w/2 + 10 ,350 ,rk.getWidth() ,rk.getHeight())) {
						//排行榜
						rank();
					}else if(bt ==MouseEvent.BUTTON1 && touch(mx ,my ,1 ,1 ,20, Window.h/2 - left.getHeight()/2 - 30, left.getWidth() ,left.getHeight())) {
						mapId = mapId==0 ? map.mapNum-1 : mapId-1;//选择地图	
						bg = App.getImg("/img/map/"+map.mapPath.get(mapId)+"/bg.png");//刷新
						repaint();
					}else if(bt ==MouseEvent.BUTTON1 && touch(mx ,my ,1 ,1 ,Window.w - right.getWidth() - 20, Window.h/2 - right.getHeight()/2 - 30, right.getWidth() ,right.getHeight())) {
						mapId = mapId==map.mapNum-1 ? 0 : mapId+1;//选择地图
						bg = App.getImg("/img/map/"+map.mapPath.get(mapId)+"/bg.png");//刷新
						repaint();
					}
				} else if(condition == "game") {
					if(bt == MouseEvent.BUTTON1)isJump = true;//跳跃
					if(bt == MouseEvent.BUTTON3)isLand = true;//加速下降
				} else if(condition == "die") {
					if(touch(mx ,my ,1 ,1 ,Window.w/2 - st.getWidth() - 10 ,350 ,st.getWidth() ,st.getHeight())) {
						//首页
						condition = "home";
						repaint();
					}
					else if(touch(mx ,my ,1 ,1 ,Window.w/2 + 10 ,350 ,rk.getWidth() ,rk.getHeight()))rank();//排行榜
					
				}
			}
			
			//松开
			public void mouseReleased(MouseEvent e) {
				int bt = e.getButton();//按键
				
				if(bt == MouseEvent.BUTTON3)isLand = false;
				else if(bt == MouseEvent.BUTTON1)isJump = false;
			}
			
			//移动
			public void mouseMoved(MouseEvent e) {
				super.mouseMoved(e);
				
				int mx = e.getX(),
					my = e.getY();
				
				if(condition == "home") {
					if(touch(mx ,my ,1 ,1 ,Window.w/2 - img1.getWidth()*3/2/2, Default.y, w*3/2 ,h*3/2)) {
						//显示选角色界面
						condition = "chooseBird";
						repaint();
					}
				} else if(condition == "chooseBird") {
					if(!touch(mx ,my ,1 ,1 ,Window.w/2 - left.getWidth()*3/2/2 - left.getWidth()/3*2 - 10 ,Default.y ,2*left.getWidth()*2/3 + img1.getWidth()*3/2 + 20 ,img1.getHeight()*3/2)) {
						//取消选择角色
						condition = "home";
						repaint();
					}
				}
			}
			
			//单击
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				
				int mx = e.getX(),
					my = e.getY();
				
				if(condition == "chooseBird") {
					if(touch(mx ,my ,1 ,1 ,Window.w/2 - left.getWidth()*3/2/2 - left.getWidth()/3*2 - 10 , Default.y + 7, left.getWidth()/3*2, left.getHeight()/3*2)) {
						//切换角色左
						pl.get(usePl).changeBird(-1);
						
						skin = pl.get(usePl).getBird();
						img1 = App.getImg("/img/bird/" + skin + "/1.png");
						img2 = App.getImg("/img/bird/" + skin + "/2.png");
						img3 = App.getImg("/img/bird/" + skin + "/3.png");
						
						repaint();
					} else if(touch(mx ,my ,1 ,1 ,Window.w/2 + right.getWidth()*3/2/2 + 10 , Default.y + 7, right.getWidth()/3*2, right.getHeight()/3*2)) {
						//切换角色右
						pl.get(usePl).changeBird(1);
						
						skin = pl.get(usePl).getBird();
						img1 = App.getImg("/img/bird/" + skin + "/1.png");
						img2 = App.getImg("/img/bird/" + skin + "/2.png");
						img3 = App.getImg("/img/bird/" + skin + "/3.png");
					}
				} else if(condition == "game") {
					if(touch(mx ,my ,1 ,1 ,Window.w/2 + 10, 425, ps.getWidth() ,ps.getHeight()) && !pause) {
						//暂停
						pause = true;
						repaint();
					} else if(touch(mx ,my ,1 ,1 ,Window.w/2 + 10, 425, con.getWidth() ,con.getHeight()) && pause) {
						//继续
						pause = false;
						repaint();
					}
				}
			}
		};
		//加入鼠标监听器
		addMouseListener(mouseAd);
		addMouseMotionListener(mouseAd);
		
		
		//键盘监听器
		KeyAdapter keyAd = new KeyAdapter() {
			@Override
			//按下
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				int keyCode = e.getKeyCode();
				
				if(condition == "home") {
					if(keyCode == KeyEvent.VK_SPACE)start();//开始
					else if(keyCode == KeyEvent.VK_LEFT) {
						mapId = mapId==0 ? map.mapNum-1 : mapId-1;//选择地图	
						bg = App.getImg("/img/map/"+map.mapPath.get(mapId)+"/bg.png");//刷新
						repaint();
					} else if(keyCode == KeyEvent.VK_RIGHT) {
						mapId = mapId==map.mapNum-1 ? 0 : mapId+1;//选择地图
						bg = App.getImg("/img/map/"+map.mapPath.get(mapId)+"/bg.png");//刷新
						repaint();
					}
				} else if(condition == "game") {
					if(keyCode == KeyEvent.VK_SPACE)isJump = true;//跳跃
					if(keyCode == KeyEvent.VK_CONTROL)isLand = true;//加速下降
				} else if(condition == "die") {
					if(keyCode == KeyEvent.VK_SPACE) {
						//首页
						condition = "home";
						repaint();
					}
				}
			}
			
			//松开
			public void keyReleased(KeyEvent e) {
				super.keyPressed(e);
				int keyCode = e.getKeyCode();
				
				if(keyCode == KeyEvent.VK_SPACE)isJump = false;
				else if(keyCode == KeyEvent.VK_CONTROL)isLand = false;
			}
		};
		frame.addKeyListener(keyAd);
	}
	
	/**
	 * 准备开始的函数
	 */
	protected void ready() {
		//状态
		condition = "ready";
		
		//显示get ready图案
		repaint();
		
		//等待
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 开始游戏
	 * @param checkPoint 
	 */
	protected void start() {
		//准备
		//ready();
		
		//关卡
		String checkPoint = map.mapPath.get(mapId);
		
		//更新状态
		condition="game";
		
		//随机数
		Random rd = new Random();
		
		//初始化图片
		bg = App.getImg("/img/map/" + checkPoint + "/bg.png"); //背景
		pipe = App.getImg("/img/map/" + checkPoint + "/pipe.png"); //管道
		ground = App.getImg("/img/map/" + checkPoint + "/ground.png");//地面
		score = 0;//分数
		dis = 0;//距离
		
		//初始化变量
		life = Default.life;//血量
		score = 0;//分数
		dis = Default.dis;//距离
		isLand = false;
		now = 1;//动作
		last = 2;
		y = Default.y;//坐标
		bg1Pos = 0 ;//背景坐标
		bg2Pos = bg.getWidth() - 1;//-1:防止出现缝隙
		gd1Pos = 0;//地面坐标
		gd2Pos = bg.getWidth();
		restJump = 0;//鸟还要跳跃几次
		pause = false;//暂停
			
		//初始化管道
		for(int i = 0 ; i < 10 ; i ++) {
			//x轴
			pipex[i] = Default.pipe * (i+1); 
			
			//上方管道y轴(范围：[20,250])
			pipeup[i] = rd.nextInt(250 - 20) + 20; 
			
			//下方管道y轴
			pipedown[i] = (int) (pipeup[i] + rd.nextInt(30) + w*4.5);
			
			//防止下方管道过低
			if(pipedown[i] >= 350){
				pipeup[i] = (int) (350 - w*4.5);    
				pipedown[i] = 350;
			}
		}
		
		//线程
		Thread birdMove = new Thread() {
			public void run() {
						while(condition == "game"/*未死亡*/) {
						//鸟的上升
						up();
					
						//鸟的下降
						down();
						
						//判断鸟碰到管道或碰到边界
						shoot();
						
						//判断鸟是否在加速下降
						land();
						
						//判断鸟是否在跳跃
						jump();
						
						//重绘
						repaint();
						
						try {
							Thread.sleep(5);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
			}
		};//鸟移动的线程
		
		Thread bgMove = new Thread() {
			public void run() {
						while(condition == "game"/*未死亡*/) {
						//背景移动(向左)
						bg1Pos -= 1 ;
						bg2Pos -= 1 ;
						//到底后重置
						if(bg1Pos == -bg.getWidth()) {
							bg1Pos = 0;
							bg2Pos = Window.w - 1;//-1：防止有缝隙
						}
						
						//地面移动
						gd1Pos -= 2;
						gd2Pos -= 2;
						//到底后重置
						if(gd1Pos == -ground.getWidth()) {
							gd1Pos = 0;
							gd2Pos = Window.w;
						}
					
						//管道移动
						for(int i = 0 ; i < 10 ; i ++) {
							pipex[i]-=3;
							//重置管道
							if(pipex[i] < -pipe.getWidth()) {
								repipe(i);
							}
						}
					
						//鸟的飞行距离
						dis ++;
						
						//计算分数
						score = dis/30;
						
						//重绘
						repaint();
						
						try {
							Thread.sleep(13);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
			}
		};//管道&背景移动的线程

		//线程
		birdMove.start();
		bgMove.start();
		
		//输出信息
		System.out.println("游戏开始！\n关卡：" + map.name.get(mapId) + "(" + map.mapPath.get(mapId) +")" 
		+ "\n皮肤：" + skin + "\n生命值：" + life + "\n");
	}
	
	/**
	 * 排行榜
	 */
	protected void rank() {
		System.out.println("制作中···");
	}

	/**
	 * 判断A是否碰到B
	 * @param ax A的坐标
	 * @param ay
	 * @param aw A的大小
	 * @param ah
	 * @param bx B的坐标
	 * @param by
	 * @param bw B的大小
	 * @param bh
	 * @return
	 */
	protected boolean touch(int ax, int ay, int aw ,int ah ,int bx, int by, int bw, int bh) {
		boolean b = ax<=bx+bw && 
				  ax>=bx-aw && 
				  ay<=by+bh && 
				  ay>=by-ah;
		return b;
	}
		
		/**
		 * 鸟跳跃的函数
		 */
		public void jump() {
			if(restJump <= 5 && isJump) {
				restJump = 60;
			}
		}
			
		/**
			 * 鸟上升的函数
		 */
		public void up() {
			if(restJump >= 1) {
				restJump --;
					y -= 3;
			}
			//鸟上升到一定高度后的减速
			if(restJump >= 10 && restJump <= 20) {
				y += 1;
			} else if(restJump >= 1 &&restJump <10) {
				y += 2;
			}
		}
		
		/**
		 * 鸟加速下降
		 */
		public void land() {
			if(isLand) {
				y += 2;
			}
		}
			
		/**
		* 鸟下降的函数
		*/
		public void down() {
			y += 1;
		}
			
			/**
			 * 判断鸟是否碰到管道
			 */
			public void shoot() {
				for(int i = 0;i < 10 ;i ++) {
					//返回值
					boolean a = false ,b = false ,c = false ,res;
					//上方管道的坐标
					int px1 = pipex[i],
						py1 = 0,
						pw1 = pipe.getWidth(),
						ph1 = pipeup[i];
					//判断鸟是否碰到上方管道
					a = x<=px1+pw1 && 
					  x>=px1-w && 
					  y<=py1+ph1 && 
					  y>=py1-h;
					  
					//下方管道的坐标
					int px2 = pipex[i],
						py2 = pipedown[i],
						pw2 = pipe.getWidth(),
						ph2 = Window.h;
					//判断鸟是否碰到下方管道
					b = x<=px2+pw2 && 
					  x>=px2-w && 
					  y<=py2+ph2 && 
					  y>=py2-h;
					  
					//判断鸟是否碰到边界
					c = y < 0 || y + img1.getHeight() > Window.h - ground.getHeight();
						
					//结果
					res = a || b || c;
					
					if(res) {
						//碰到柱子
						relive(i , c == true ? "out" : "pipe");
						return;
					}
				}
			}

			/**
			 * 鸟的复活
			 * @param index 鸟受伤时在那根管道
			 * @param reason 鸟受伤的原因
			 */
			public void relive(int index , String reason) {
				if(life >= 1 ) {
					life --;//扣血
				} else if(life == -1) {
					//-1为无敌
					repipe(index);
				}
				
				//假如生命>0，被碰到的柱子消失
				if(life > 0 && reason == "pipe")repipe(index);//重置被撞的管道
				else if(reason == "out")y = Default.y;//重置坐标
				
				//生命值=0
				if(life == 0) {	
					//结算
					pl.get(usePl).addScore(score);
					pl.get(usePl).money += score / 2;
					
					//保存
					pl.get(usePl).save();
					
					//死亡
					condition = "die";
					
					//输出信息
					System.out.println("游戏结束！\n分数：" + score + "\n\n");
					
					//绘制死亡界面
					repaint();
				}
			}

			/**
			 * 
			 * 管道坐标单个重置
			 * @param index 鸟受伤时在那根管道上
			 */
			public void repipe(int index) {
				int pre = index == 0 ? 9 : index-1;
				pipex[index] = pipex[pre] + Default.pipe;
			}
		

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		if(condition == "home") {
			//背景
			g.drawImage(bg ,0 ,0 , null);
		
			//标题
			g.drawImage(fb ,Window.w/2 - fb.getWidth()/2 ,75 , null);
		
			//开始按钮
			g.drawImage(st ,Window.w/2 - st.getWidth() - 10 ,330 , null);
			
			//排行榜按钮
			g.drawImage(rk ,Window.w/2 + 10 ,330 , null);
			
			//鸟
			g.drawImage(bird, Window.w/2 - img1.getWidth()*3/2/2, Default.y, w*3/2 ,h*3/2 ,null);

			//左右箭头
			g.drawImage(left, 20, Window.h/2 - left.getHeight()/2 - 30, null);
			g.drawImage(right, Window.w - right.getWidth() - 20, Window.h/2 - right.getHeight()/2 - 30, null);
		} else if(condition == "chooseBird") {
			//背景
			g.drawImage(bg ,0 ,0 , null);
		
			//标题
			g.drawImage(fb ,Window.w/2 - fb.getWidth()/2 ,75 , null);
		
			//开始按钮
			g.drawImage(st ,Window.w/2 - st.getWidth() - 10 ,330 , null);
			
			//排行榜按钮
			g.drawImage(rk ,Window.w/2 + 10 ,330 , null);
			
			//鸟
			g.drawImage(bird, Window.w/2 - img1.getWidth()*3/2/2, Default.y, w*3/2 ,h*3/2 ,null);

			//左右箭头大
			g.drawImage(left, 20, Window.h/2 - left.getHeight()/2 - 30, null);
			g.drawImage(right, Window.w - right.getWidth() - 20, Window.h/2 - right.getHeight()/2 - 30, null);
		
			//左右箭头小
			g.drawImage(left, Window.w/2 - left.getWidth()*3/2/2 - left.getWidth()/3*2 - 10 , Default.y + 7, left.getWidth()/3*2, left.getHeight()/3*2, null);
			g.drawImage(right, Window.w/2 + right.getWidth()*3/2/2 + 10 , Default.y + 7, right.getWidth()/3*2, right.getHeight()/3*2, null);
		} else if(condition == "game" || condition == "die" || condition == "ready") {
			//画背景
			g.drawImage(bg , bg1Pos , 0 , null);
			g.drawImage(bg, bg2Pos , 0 , null);
			
			//画鸟
			g.drawImage(bird , x , y , null);
			
			//画柱子
			for(int i = 0 ; i < 10 ; i ++) {
				g.drawImage(pipe , pipex[i] , pipeup[i] - pipe.getHeight() , null);
				g.drawImage(pipe  ,pipex[i] , pipedown[i] , null);
			}
			
			//画地面
			g.drawImage(ground , gd1Pos , Window.h - ground.getHeight() , null);
			g.drawImage(ground , gd2Pos , Window.h - ground.getHeight() , null);

			//画降落伞
			if(isLand) {
				g.drawImage(thing.umbrella ,x - 15 ,y - thing.umbrella.getHeight() + 15 ,null);
			}
			
			//生命值
			BufferedImage lifeImg = img1;
			for(int i=0;i<life;i++) {
				g.drawImage(lifeImg , i*35+10 , 10 , null);
			}
			
			if(condition != "die") {
				//画分数
				//获取拆分后的分数
				int[] scoreNum = Maths.resolution(score);
				int pos = 0;
				//过滤前面多余的0
				while(scoreNum[pos ++] == 0 && pos < scoreNum.length - 1/*留下最后的零*/);
				pos --;
				//画分数
				for(int i = pos , posX = Window.w - (scoreNum.length-pos)*num[0].getWidth() - 10 ;i < scoreNum.length ;i ++ , posX += num[0].getWidth()) {
					g.drawImage(num[scoreNum[i]] ,posX ,10 , num[scoreNum[i]].getWidth(), num[scoreNum[i]].getHeight(),null);
				}
			}
			
			//死亡
			if(condition == "die") {
				//画GameOver图标
				g.drawImage(go ,Window.w/2 - go.getWidth()/2 ,Window.h/2 - ground.getHeight()/2 - go.getHeight()/2 - 90 ,null);
				
				//死亡界面坐标
				int x = Window.w/2 - medalList.getWidth()/2,
					y = Window.h/2 - ground.getHeight()/2 - medalList.getHeight()/2 + 30;
				//画死亡界面
				g.drawImage(medalList ,x ,y ,null);

				//画奖牌
				g.drawImage(score >= Default.medal1 ? medal1 : (score >= Default.medal2 ? medal2 : medal3) ,x + 27 ,y + 42 ,null);

				//画分数
				//获取拆分后的分数
				int[] scoreNum1 = Maths.resolution(score);
				int pos1 = 0;
				//过滤前面多余的0
				while(scoreNum1[pos1 ++] == 0 && pos1 < scoreNum1.length - 1/*留下最后的零*/);
				pos1 --;
				//画分数
				for(int i = pos1 , posX = 200 ;i < scoreNum1.length ;i ++ , posX += num[0].getWidth()/2 ) {
					g.drawImage(num[scoreNum1[i]] ,posX ,207 , num[scoreNum1[i]].getWidth()/2, num[scoreNum1[i]].getHeight()/2,null);
				}
				
				//开始按钮
				g.drawImage(st ,Window.w/2 - st.getWidth() - 10 ,330 , null);
				
				//排行榜按钮
				g.drawImage(rk ,Window.w/2 + 10 ,330 , null);
			} else if(condition == "ready") {
				System.out.println("aa");
				//准备图案
				g.drawImage(rd ,0 ,0 ,null);
			} 
		}
		
		if(condition != "ready" && condition != "game") {
			//画金币
			g.drawImage(mny, 5, 5, mny.getWidth()/5*4, mny.getHeight()/5*4,null);
			
			//获取拆分后的金币
			int[] moneyNum = Maths.resolution(pl.get(usePl).money);
			int pos1 = 0;
			//过滤前面多余的0
			while(moneyNum[pos1 ++] == 0 && pos1 < moneyNum.length - 1/*留下最后的零*/);
			pos1 --;
			//画分数
			for(int i = pos1 , posX = mny.getWidth()/5*4+8 ;i < moneyNum.length ;i ++ , posX += num[0].getWidth()/5*4 ) {
				g.drawImage(num[moneyNum[i]] ,posX ,8 , num[moneyNum[i]].getWidth()/3*2, num[moneyNum[i]].getHeight()/3*2,null);
			}
		}
	}
}