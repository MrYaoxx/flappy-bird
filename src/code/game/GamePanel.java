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
	//��Ϸ״̬
	/*
	 * home:��ҳ
	 * ready:׼��
	 * game:��ʼ
	 * die:����
	 */
	String condition;
	
	//�����Ϣ
	List<Player> pl = new ArrayList<Player>();
	int usePl = 0;//��ǰ���
	
	//ͼƬ
	BufferedImage fb,//Flappy Birdͼ��
				bird,//��
				mny,//���
				rd,//׼��
				st,//��ʼ
				rk,//���а�
				left,//���ͷ
				right,//�Ҽ�ͷ
				go,//GameOverͼ��
				medalList,//���²˵�
				medal1,//��
				medal2,//����
				medal3,//ͭ��
				ps,//��ͣ
				con;//����
	BufferedImage[] num;//����
	Thing thing = new Thing();//װ��
	
	//���Ѫ��
	int life = Default.life;
	
	//��ķ���
	long score = 0;
	
	//��ɹ��ľ���
	long dis = Default.dis;
	
	//���Ƿ�����½�
	boolean isLand = false;
	
	//���Ƿ�����Ծ
	boolean isJump = false;
	
	//�������ڵ�״̬(�ڼ���ͼƬ)
	int now = 1;
	
	//������һ�ε�״̬
	int last = 2;
	
	//���Ƥ��
	String skin;
	
	//�������ͼƬ
	BufferedImage img1;
	BufferedImage img2;
	BufferedImage img3;
	
	//�������ʹ�С
	int x = Default.x;//x����ʼ�����м�
	int y = Default.y;
	int w ,h;
	
	//��ͣ
	boolean pause = false;

	//�ؿ�
		//��ͼ
		Map map = new Map();
		
		//�ؿ����
		int mapId = 0;
		
		//����
		BufferedImage bg;
		
		//��������
		int bg1Pos = 0 , bg2Pos = Window.w - 1;
		
		//�ܵ�
			//ͼƬ
			BufferedImage pipe;
			
			//�ܵ�λ��
			int[] pipeup = new int[10];//�Ϸ��Ĺܵ�
			int[] pipedown = new int[10];//�·��Ĺܵ�
			int[] pipex = new int[10];//�ܵ�y����
	
		//����
		BufferedImage ground;

		//��������
		int gd1Pos = 0 , gd2Pos = Window.w;
	
	//��Ҫ��Ծ����
	int restJump = 0;
	
	//�߳�
	Thread birdFly = new Thread(){
		public void run(){
				while(true) {
					//�л����״̬
					if(now==1) {
						if(last==2)now = 3;
						else now = 2;
						last = 1;
					} else {
						last = now;
						now = 1;
					}
					//�л����ͼƬ
					bird = now==1 ? img1 : (now==2 ? img2 : img3);
				
					//�ػ�
					repaint();
					
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
		}
	};//����е��߳�
	
	
	public GamePanel(GameFrame frame) {
		//��ʼ��
		//״̬
		condition = "home";
		//ͼƬ
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
		//���
		/* BufferedReader br = App.getFileAbsolute("D:\\Creeper\\flappy_bird\\player\\name.ini");
		 *
		 * try { String s = br.readLine(); if(s.startsWith("#")) { s = s.substring(1
		 * ,s.length()); usePl = pl.size(); }
		 * 
		 * //pl.add(new Player("admin")); } catch (IOException e) { e.printStackTrace();
		 * }
		 */
		pl.add(new Player("admin"));
		//Ƥ��
		skin = pl.get(usePl).getBird();
		img1 = App.getImg("/img/bird/" + skin + "/1.png");
		img2 = App.getImg("/img/bird/" + skin + "/2.png");
		img3 = App.getImg("/img/bird/" + skin + "/3.png");
		w = img1.getWidth();
		h = img1.getHeight();
		//�߳�
		birdFly.start();
		

		//��������
		MouseAdapter mouseAd = new MouseAdapter() {
			//����
			public void mousePressed(MouseEvent e) {
				int mx = e.getX() ,my = e.getY(),//��������
					bt = e.getButton();//���İ���
				
				if(condition == "home") {
					if(bt == MouseEvent.BUTTON1 && touch(mx ,my ,1 ,1 ,Window.w/2 - st.getWidth() - 10 ,350 ,st.getWidth() ,st.getHeight())) {
						//��ʼ
						start();
					}else if(bt == MouseEvent.BUTTON1 && touch(mx ,my ,1 ,1 ,Window.w/2 + 10 ,350 ,rk.getWidth() ,rk.getHeight())) {
						//���а�
						rank();
					}else if(bt ==MouseEvent.BUTTON1 && touch(mx ,my ,1 ,1 ,20, Window.h/2 - left.getHeight()/2 - 30, left.getWidth() ,left.getHeight())) {
						mapId = mapId==0 ? map.mapNum-1 : mapId-1;//ѡ���ͼ	
						bg = App.getImg("/img/map/"+map.mapPath.get(mapId)+"/bg.png");//ˢ��
						repaint();
					}else if(bt ==MouseEvent.BUTTON1 && touch(mx ,my ,1 ,1 ,Window.w - right.getWidth() - 20, Window.h/2 - right.getHeight()/2 - 30, right.getWidth() ,right.getHeight())) {
						mapId = mapId==map.mapNum-1 ? 0 : mapId+1;//ѡ���ͼ
						bg = App.getImg("/img/map/"+map.mapPath.get(mapId)+"/bg.png");//ˢ��
						repaint();
					}
				} else if(condition == "game") {
					if(bt == MouseEvent.BUTTON1)isJump = true;//��Ծ
					if(bt == MouseEvent.BUTTON3)isLand = true;//�����½�
				} else if(condition == "die") {
					if(touch(mx ,my ,1 ,1 ,Window.w/2 - st.getWidth() - 10 ,350 ,st.getWidth() ,st.getHeight())) {
						//��ҳ
						condition = "home";
						repaint();
					}
					else if(touch(mx ,my ,1 ,1 ,Window.w/2 + 10 ,350 ,rk.getWidth() ,rk.getHeight()))rank();//���а�
					
				}
			}
			
			//�ɿ�
			public void mouseReleased(MouseEvent e) {
				int bt = e.getButton();//����
				
				if(bt == MouseEvent.BUTTON3)isLand = false;
				else if(bt == MouseEvent.BUTTON1)isJump = false;
			}
			
			//�ƶ�
			public void mouseMoved(MouseEvent e) {
				super.mouseMoved(e);
				
				int mx = e.getX(),
					my = e.getY();
				
				if(condition == "home") {
					if(touch(mx ,my ,1 ,1 ,Window.w/2 - img1.getWidth()*3/2/2, Default.y, w*3/2 ,h*3/2)) {
						//��ʾѡ��ɫ����
						condition = "chooseBird";
						repaint();
					}
				} else if(condition == "chooseBird") {
					if(!touch(mx ,my ,1 ,1 ,Window.w/2 - left.getWidth()*3/2/2 - left.getWidth()/3*2 - 10 ,Default.y ,2*left.getWidth()*2/3 + img1.getWidth()*3/2 + 20 ,img1.getHeight()*3/2)) {
						//ȡ��ѡ���ɫ
						condition = "home";
						repaint();
					}
				}
			}
			
			//����
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				
				int mx = e.getX(),
					my = e.getY();
				
				if(condition == "chooseBird") {
					if(touch(mx ,my ,1 ,1 ,Window.w/2 - left.getWidth()*3/2/2 - left.getWidth()/3*2 - 10 , Default.y + 7, left.getWidth()/3*2, left.getHeight()/3*2)) {
						//�л���ɫ��
						pl.get(usePl).changeBird(-1);
						
						skin = pl.get(usePl).getBird();
						img1 = App.getImg("/img/bird/" + skin + "/1.png");
						img2 = App.getImg("/img/bird/" + skin + "/2.png");
						img3 = App.getImg("/img/bird/" + skin + "/3.png");
						
						repaint();
					} else if(touch(mx ,my ,1 ,1 ,Window.w/2 + right.getWidth()*3/2/2 + 10 , Default.y + 7, right.getWidth()/3*2, right.getHeight()/3*2)) {
						//�л���ɫ��
						pl.get(usePl).changeBird(1);
						
						skin = pl.get(usePl).getBird();
						img1 = App.getImg("/img/bird/" + skin + "/1.png");
						img2 = App.getImg("/img/bird/" + skin + "/2.png");
						img3 = App.getImg("/img/bird/" + skin + "/3.png");
					}
				} else if(condition == "game") {
					if(touch(mx ,my ,1 ,1 ,Window.w/2 + 10, 425, ps.getWidth() ,ps.getHeight()) && !pause) {
						//��ͣ
						pause = true;
						repaint();
					} else if(touch(mx ,my ,1 ,1 ,Window.w/2 + 10, 425, con.getWidth() ,con.getHeight()) && pause) {
						//����
						pause = false;
						repaint();
					}
				}
			}
		};
		//������������
		addMouseListener(mouseAd);
		addMouseMotionListener(mouseAd);
		
		
		//���̼�����
		KeyAdapter keyAd = new KeyAdapter() {
			@Override
			//����
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				int keyCode = e.getKeyCode();
				
				if(condition == "home") {
					if(keyCode == KeyEvent.VK_SPACE)start();//��ʼ
					else if(keyCode == KeyEvent.VK_LEFT) {
						mapId = mapId==0 ? map.mapNum-1 : mapId-1;//ѡ���ͼ	
						bg = App.getImg("/img/map/"+map.mapPath.get(mapId)+"/bg.png");//ˢ��
						repaint();
					} else if(keyCode == KeyEvent.VK_RIGHT) {
						mapId = mapId==map.mapNum-1 ? 0 : mapId+1;//ѡ���ͼ
						bg = App.getImg("/img/map/"+map.mapPath.get(mapId)+"/bg.png");//ˢ��
						repaint();
					}
				} else if(condition == "game") {
					if(keyCode == KeyEvent.VK_SPACE)isJump = true;//��Ծ
					if(keyCode == KeyEvent.VK_CONTROL)isLand = true;//�����½�
				} else if(condition == "die") {
					if(keyCode == KeyEvent.VK_SPACE) {
						//��ҳ
						condition = "home";
						repaint();
					}
				}
			}
			
			//�ɿ�
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
	 * ׼����ʼ�ĺ���
	 */
	protected void ready() {
		//״̬
		condition = "ready";
		
		//��ʾget readyͼ��
		repaint();
		
		//�ȴ�
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ʼ��Ϸ
	 * @param checkPoint 
	 */
	protected void start() {
		//׼��
		//ready();
		
		//�ؿ�
		String checkPoint = map.mapPath.get(mapId);
		
		//����״̬
		condition="game";
		
		//�����
		Random rd = new Random();
		
		//��ʼ��ͼƬ
		bg = App.getImg("/img/map/" + checkPoint + "/bg.png"); //����
		pipe = App.getImg("/img/map/" + checkPoint + "/pipe.png"); //�ܵ�
		ground = App.getImg("/img/map/" + checkPoint + "/ground.png");//����
		score = 0;//����
		dis = 0;//����
		
		//��ʼ������
		life = Default.life;//Ѫ��
		score = 0;//����
		dis = Default.dis;//����
		isLand = false;
		now = 1;//����
		last = 2;
		y = Default.y;//����
		bg1Pos = 0 ;//��������
		bg2Pos = bg.getWidth() - 1;//-1:��ֹ���ַ�϶
		gd1Pos = 0;//��������
		gd2Pos = bg.getWidth();
		restJump = 0;//��Ҫ��Ծ����
		pause = false;//��ͣ
			
		//��ʼ���ܵ�
		for(int i = 0 ; i < 10 ; i ++) {
			//x��
			pipex[i] = Default.pipe * (i+1); 
			
			//�Ϸ��ܵ�y��(��Χ��[20,250])
			pipeup[i] = rd.nextInt(250 - 20) + 20; 
			
			//�·��ܵ�y��
			pipedown[i] = (int) (pipeup[i] + rd.nextInt(30) + w*4.5);
			
			//��ֹ�·��ܵ�����
			if(pipedown[i] >= 350){
				pipeup[i] = (int) (350 - w*4.5);    
				pipedown[i] = 350;
			}
		}
		
		//�߳�
		Thread birdMove = new Thread() {
			public void run() {
						while(condition == "game"/*δ����*/) {
						//�������
						up();
					
						//����½�
						down();
						
						//�ж��������ܵ��������߽�
						shoot();
						
						//�ж����Ƿ��ڼ����½�
						land();
						
						//�ж����Ƿ�����Ծ
						jump();
						
						//�ػ�
						repaint();
						
						try {
							Thread.sleep(5);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
			}
		};//���ƶ����߳�
		
		Thread bgMove = new Thread() {
			public void run() {
						while(condition == "game"/*δ����*/) {
						//�����ƶ�(����)
						bg1Pos -= 1 ;
						bg2Pos -= 1 ;
						//���׺�����
						if(bg1Pos == -bg.getWidth()) {
							bg1Pos = 0;
							bg2Pos = Window.w - 1;//-1����ֹ�з�϶
						}
						
						//�����ƶ�
						gd1Pos -= 2;
						gd2Pos -= 2;
						//���׺�����
						if(gd1Pos == -ground.getWidth()) {
							gd1Pos = 0;
							gd2Pos = Window.w;
						}
					
						//�ܵ��ƶ�
						for(int i = 0 ; i < 10 ; i ++) {
							pipex[i]-=3;
							//���ùܵ�
							if(pipex[i] < -pipe.getWidth()) {
								repipe(i);
							}
						}
					
						//��ķ��о���
						dis ++;
						
						//�������
						score = dis/30;
						
						//�ػ�
						repaint();
						
						try {
							Thread.sleep(13);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
			}
		};//�ܵ�&�����ƶ����߳�

		//�߳�
		birdMove.start();
		bgMove.start();
		
		//�����Ϣ
		System.out.println("��Ϸ��ʼ��\n�ؿ���" + map.name.get(mapId) + "(" + map.mapPath.get(mapId) +")" 
		+ "\nƤ����" + skin + "\n����ֵ��" + life + "\n");
	}
	
	/**
	 * ���а�
	 */
	protected void rank() {
		System.out.println("�����С�����");
	}

	/**
	 * �ж�A�Ƿ�����B
	 * @param ax A������
	 * @param ay
	 * @param aw A�Ĵ�С
	 * @param ah
	 * @param bx B������
	 * @param by
	 * @param bw B�Ĵ�С
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
		 * ����Ծ�ĺ���
		 */
		public void jump() {
			if(restJump <= 5 && isJump) {
				restJump = 60;
			}
		}
			
		/**
			 * �������ĺ���
		 */
		public void up() {
			if(restJump >= 1) {
				restJump --;
					y -= 3;
			}
			//��������һ���߶Ⱥ�ļ���
			if(restJump >= 10 && restJump <= 20) {
				y += 1;
			} else if(restJump >= 1 &&restJump <10) {
				y += 2;
			}
		}
		
		/**
		 * ������½�
		 */
		public void land() {
			if(isLand) {
				y += 2;
			}
		}
			
		/**
		* ���½��ĺ���
		*/
		public void down() {
			y += 1;
		}
			
			/**
			 * �ж����Ƿ������ܵ�
			 */
			public void shoot() {
				for(int i = 0;i < 10 ;i ++) {
					//����ֵ
					boolean a = false ,b = false ,c = false ,res;
					//�Ϸ��ܵ�������
					int px1 = pipex[i],
						py1 = 0,
						pw1 = pipe.getWidth(),
						ph1 = pipeup[i];
					//�ж����Ƿ������Ϸ��ܵ�
					a = x<=px1+pw1 && 
					  x>=px1-w && 
					  y<=py1+ph1 && 
					  y>=py1-h;
					  
					//�·��ܵ�������
					int px2 = pipex[i],
						py2 = pipedown[i],
						pw2 = pipe.getWidth(),
						ph2 = Window.h;
					//�ж����Ƿ������·��ܵ�
					b = x<=px2+pw2 && 
					  x>=px2-w && 
					  y<=py2+ph2 && 
					  y>=py2-h;
					  
					//�ж����Ƿ������߽�
					c = y < 0 || y + img1.getHeight() > Window.h - ground.getHeight();
						
					//���
					res = a || b || c;
					
					if(res) {
						//��������
						relive(i , c == true ? "out" : "pipe");
						return;
					}
				}
			}

			/**
			 * ��ĸ���
			 * @param index ������ʱ���Ǹ��ܵ�
			 * @param reason �����˵�ԭ��
			 */
			public void relive(int index , String reason) {
				if(life >= 1 ) {
					life --;//��Ѫ
				} else if(life == -1) {
					//-1Ϊ�޵�
					repipe(index);
				}
				
				//��������>0����������������ʧ
				if(life > 0 && reason == "pipe")repipe(index);//���ñ�ײ�Ĺܵ�
				else if(reason == "out")y = Default.y;//��������
				
				//����ֵ=0
				if(life == 0) {	
					//����
					pl.get(usePl).addScore(score);
					pl.get(usePl).money += score / 2;
					
					//����
					pl.get(usePl).save();
					
					//����
					condition = "die";
					
					//�����Ϣ
					System.out.println("��Ϸ������\n������" + score + "\n\n");
					
					//������������
					repaint();
				}
			}

			/**
			 * 
			 * �ܵ����굥������
			 * @param index ������ʱ���Ǹ��ܵ���
			 */
			public void repipe(int index) {
				int pre = index == 0 ? 9 : index-1;
				pipex[index] = pipex[pre] + Default.pipe;
			}
		

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		if(condition == "home") {
			//����
			g.drawImage(bg ,0 ,0 , null);
		
			//����
			g.drawImage(fb ,Window.w/2 - fb.getWidth()/2 ,75 , null);
		
			//��ʼ��ť
			g.drawImage(st ,Window.w/2 - st.getWidth() - 10 ,330 , null);
			
			//���а�ť
			g.drawImage(rk ,Window.w/2 + 10 ,330 , null);
			
			//��
			g.drawImage(bird, Window.w/2 - img1.getWidth()*3/2/2, Default.y, w*3/2 ,h*3/2 ,null);

			//���Ҽ�ͷ
			g.drawImage(left, 20, Window.h/2 - left.getHeight()/2 - 30, null);
			g.drawImage(right, Window.w - right.getWidth() - 20, Window.h/2 - right.getHeight()/2 - 30, null);
		} else if(condition == "chooseBird") {
			//����
			g.drawImage(bg ,0 ,0 , null);
		
			//����
			g.drawImage(fb ,Window.w/2 - fb.getWidth()/2 ,75 , null);
		
			//��ʼ��ť
			g.drawImage(st ,Window.w/2 - st.getWidth() - 10 ,330 , null);
			
			//���а�ť
			g.drawImage(rk ,Window.w/2 + 10 ,330 , null);
			
			//��
			g.drawImage(bird, Window.w/2 - img1.getWidth()*3/2/2, Default.y, w*3/2 ,h*3/2 ,null);

			//���Ҽ�ͷ��
			g.drawImage(left, 20, Window.h/2 - left.getHeight()/2 - 30, null);
			g.drawImage(right, Window.w - right.getWidth() - 20, Window.h/2 - right.getHeight()/2 - 30, null);
		
			//���Ҽ�ͷС
			g.drawImage(left, Window.w/2 - left.getWidth()*3/2/2 - left.getWidth()/3*2 - 10 , Default.y + 7, left.getWidth()/3*2, left.getHeight()/3*2, null);
			g.drawImage(right, Window.w/2 + right.getWidth()*3/2/2 + 10 , Default.y + 7, right.getWidth()/3*2, right.getHeight()/3*2, null);
		} else if(condition == "game" || condition == "die" || condition == "ready") {
			//������
			g.drawImage(bg , bg1Pos , 0 , null);
			g.drawImage(bg, bg2Pos , 0 , null);
			
			//����
			g.drawImage(bird , x , y , null);
			
			//������
			for(int i = 0 ; i < 10 ; i ++) {
				g.drawImage(pipe , pipex[i] , pipeup[i] - pipe.getHeight() , null);
				g.drawImage(pipe  ,pipex[i] , pipedown[i] , null);
			}
			
			//������
			g.drawImage(ground , gd1Pos , Window.h - ground.getHeight() , null);
			g.drawImage(ground , gd2Pos , Window.h - ground.getHeight() , null);

			//������ɡ
			if(isLand) {
				g.drawImage(thing.umbrella ,x - 15 ,y - thing.umbrella.getHeight() + 15 ,null);
			}
			
			//����ֵ
			BufferedImage lifeImg = img1;
			for(int i=0;i<life;i++) {
				g.drawImage(lifeImg , i*35+10 , 10 , null);
			}
			
			if(condition != "die") {
				//������
				//��ȡ��ֺ�ķ���
				int[] scoreNum = Maths.resolution(score);
				int pos = 0;
				//����ǰ������0
				while(scoreNum[pos ++] == 0 && pos < scoreNum.length - 1/*����������*/);
				pos --;
				//������
				for(int i = pos , posX = Window.w - (scoreNum.length-pos)*num[0].getWidth() - 10 ;i < scoreNum.length ;i ++ , posX += num[0].getWidth()) {
					g.drawImage(num[scoreNum[i]] ,posX ,10 , num[scoreNum[i]].getWidth(), num[scoreNum[i]].getHeight(),null);
				}
			}
			
			//����
			if(condition == "die") {
				//��GameOverͼ��
				g.drawImage(go ,Window.w/2 - go.getWidth()/2 ,Window.h/2 - ground.getHeight()/2 - go.getHeight()/2 - 90 ,null);
				
				//������������
				int x = Window.w/2 - medalList.getWidth()/2,
					y = Window.h/2 - ground.getHeight()/2 - medalList.getHeight()/2 + 30;
				//����������
				g.drawImage(medalList ,x ,y ,null);

				//������
				g.drawImage(score >= Default.medal1 ? medal1 : (score >= Default.medal2 ? medal2 : medal3) ,x + 27 ,y + 42 ,null);

				//������
				//��ȡ��ֺ�ķ���
				int[] scoreNum1 = Maths.resolution(score);
				int pos1 = 0;
				//����ǰ������0
				while(scoreNum1[pos1 ++] == 0 && pos1 < scoreNum1.length - 1/*����������*/);
				pos1 --;
				//������
				for(int i = pos1 , posX = 200 ;i < scoreNum1.length ;i ++ , posX += num[0].getWidth()/2 ) {
					g.drawImage(num[scoreNum1[i]] ,posX ,207 , num[scoreNum1[i]].getWidth()/2, num[scoreNum1[i]].getHeight()/2,null);
				}
				
				//��ʼ��ť
				g.drawImage(st ,Window.w/2 - st.getWidth() - 10 ,330 , null);
				
				//���а�ť
				g.drawImage(rk ,Window.w/2 + 10 ,330 , null);
			} else if(condition == "ready") {
				System.out.println("aa");
				//׼��ͼ��
				g.drawImage(rd ,0 ,0 ,null);
			} 
		}
		
		if(condition != "ready" && condition != "game") {
			//�����
			g.drawImage(mny, 5, 5, mny.getWidth()/5*4, mny.getHeight()/5*4,null);
			
			//��ȡ��ֺ�Ľ��
			int[] moneyNum = Maths.resolution(pl.get(usePl).money);
			int pos1 = 0;
			//����ǰ������0
			while(moneyNum[pos1 ++] == 0 && pos1 < moneyNum.length - 1/*����������*/);
			pos1 --;
			//������
			for(int i = pos1 , posX = mny.getWidth()/5*4+8 ;i < moneyNum.length ;i ++ , posX += num[0].getWidth()/5*4 ) {
				g.drawImage(num[moneyNum[i]] ,posX ,8 , num[moneyNum[i]].getWidth()/3*2, num[moneyNum[i]].getHeight()/3*2,null);
			}
		}
	}
}