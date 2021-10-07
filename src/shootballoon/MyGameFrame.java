package shootballoon;

import java.awt.*;
import javax.swing.*;
import people.Boss;
import people.Ninja;
import people.Woman;
import weapon.*;
import java.awt.event.*;
import java.util.ArrayList;

/*
 * 
 * 【主窗口】
 * 
 */
public class MyGameFrame extends JFrame {

	// 素材加载
	Image gamescreen = (new ImageIcon(Constant.IMAGEPATH + "gamescreen.jpg")).getImage(); // 背景
	Image gun = (new ImageIcon(Constant.IMAGEPATH + "gun.png")).getImage(); // 枪
	Image icon = (new ImageIcon(Constant.IMAGEPATH + "icon.png")).getImage(); // 许文强
	Image gameover = (new ImageIcon(Constant.IMAGEPATH + "over.jpg")).getImage();

	JPanel launchpanel = new MyGamePanel(); // 实例化面板
	Gun gun_obj = new Gun(); // 实例化枪
	Explode explode = new Explode(); // 实例化爆炸动画
	Grenade grenade = new Grenade(); // 实例化手雷
	Bullet bullet = new Bullet(); // 创建子弹对象
	PaintThread paintthread = new PaintThread(); // 创建线程对象

	public static ArrayList<Bullet> Bullets = new ArrayList<Bullet>(); // 子弹容器
	private ArrayList<Ninja> Ninjas = new ArrayList<Ninja>(); // 忍者容器
	private ArrayList<Boss> Bosses = new ArrayList<Boss>(); // boss容器
	private ArrayList<Woman> Women = new ArrayList<Woman>(); // 女人（人质）容器
	
	public static int ms = 0; // 毫秒
	protected static int s; // 秒
	private static int Score = 0; // 得分
	public  static boolean run = true; // 判断执行线程
	public boolean pause =false;
	protected boolean over=false;
	
	/*
	 * 
	 * 【方法】窗口初始化
	 * 
	 */
	public MyGameFrame() {
		
		this.setTitle("血战上海滩"); // 标题
		this.setIconImage(icon); // 图标
		this.setVisible(true); // 窗口可见
		this.setSize(Constant.SCREEN_WIDTH, Constant.SCREEN_HEIGHT); // 大小
		this.setLocation(200, 10); // 坐标
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 关闭窗口
		this.setLayout(null); // 取消布局
		this.setResizable(false);
		this.add(launchpanel);
		addKeyListener(new Key_monitor(this)); // 添加按键监听,(匿名对象)

		for (int i = 0; i < 15; i++) // 初始化十五忍者
		{
			Ninja ninja = new Ninja();
			Ninjas.add(ninja);
		}
		for (int i = 0; i < 5; i++) // 初始化五个boss
		{
			Boss boss = new Boss();
			Bosses.add(boss);
		}
		for (int i = 0; i < 5; i++) // 初始化五个女人（人质）
		{
			Woman woman = new Woman();
			Women.add(woman);
		}
	}

	/*
	 * 
	 * Start 开启窗口
	 * 
	 */
	//	public void Start()
	//	{
	//		new MyGameFrame();     										//创建框架对象
	//																
	//	}

	/*
	 * 
	 * 【内部类】自定义面板
	 * 
	 */
	public class MyGamePanel extends JPanel {
		// 构造
		public MyGamePanel() {
			this.setBounds(0, 0, 1020, 768);
			this.setVisible(true);
			this.setLayout(null);

			new PaintThread().start(); // 以匿名对象开启画图线程

		}

		/*
		 * 
		 * 主 画 图 ( 主 paint 方 法 )
		 *
		 *
		 */
		public void paint(Graphics g) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("Dialog", 1, 20));

			if ((Bullet.b <= Constant.BULLET_QUANTITY) && (s <= Constant.GAME_TIME))
				g.drawImage(gamescreen, 0, 0, 1020, 768, null); // 开始画开始界面

			if ((Bullet.b <= Constant.BULLET_QUANTITY) && (s <= Constant.GAME_TIME))
				g.drawString("剩余子弹", 900, 700); // 剩余子弹提示

			if (Bullet.b <= Constant.BULLET_QUANTITY && (s <= Constant.GAME_TIME))
				g.drawString("" + (Constant.BULLET_QUANTITY - Bullet.b), 930, 740); // 子弹数提示
			else
				g.drawString("0", 930, 740);
			// 子弹数提示

			if ((Bullet.b <= Constant.BULLET_QUANTITY) && (s <= Constant.GAME_TIME))
				g.drawString("时间:", 25, 730); // time记时

			if ((Bullet.b <= Constant.BULLET_QUANTITY) && (s <= Constant.GAME_TIME))
				g.drawString("" + s, 110, 730);

			if ((Bullet.b <= Constant.BULLET_QUANTITY) && (s <= Constant.GAME_TIME))
				g.drawString("声望", 20, 30); // 得分

			if ((Bullet.b <= Constant.BULLET_QUANTITY) && (s <= Constant.GAME_TIME))
				g.drawString("" + Score, 30, 50);

			
				grenade.draw_ObjectSelf(g); // 画扔手雷

			// 爆炸动画和检测
if ((Bullet.b <= Constant.BULLET_QUANTITY) && (s <= Constant.GAME_TIME)) // 剩余子弹
				bullet.drawreloading(g);

			if ((Bullet.b <= Constant.BULLET_QUANTITY) && (s <= Constant.GAME_TIME))
				gun_obj.draw_ObjectSelf(g); // 调用Gun的画自己方法,画枪

			if (Grenade.throwed)
			if (Explode.bomb && Explode.number < 16) {
				explode.drawbomb(g);

				// 取出所有忍者
				for (int j = 0; j < Ninjas.size(); j++) // 这里的所有忍者已经初始化过了
				{
					Ninja ninja = Ninjas.get(j); // 也就是说虽然没画，但是它的坐标已经刻在背景中了
					// 碰撞检测
					if (ninja.draw && !ninja.get_shoot) // 所以要判断是否画出，再做碰撞检测
					{
						if (explode.getRect().intersects(ninja.getRect())) // 检测
						{
							explode.drawexplode(g, ninja.x, ninja.y); // 爆炸动画
							Score = Score + 10; // 分数加10
							ninja.get_shoot = true; // 被击中，不再画这个
						}
					}
				}

				// 取出所有boss
				for (int k = 0; k < Bosses.size(); k++) {
					Boss boss = Bosses.get(k);

					// 碰撞检测
					if (boss.draw && !boss.get_shoot) {
						if (explode.getRect().intersects(boss.getRect())) {
							explode.drawexplode(g, boss.x, boss.y);
							Score = Score + 50;
							boss.get_shoot = true;
						}
					}
				}

				// 取出所有女人
				for (int l = 0; l < Women.size(); l++) {
					Woman woman = Women.get(l);

					// 碰撞检测
					if (woman.draw && !woman.get_shoot) {
						if (explode.getRect().intersects(woman.getRect())) {
							explode.drawexplode(g, woman.x, woman.y);
							if (Score >= 20)
								Score -= 30;
							if (Score < 20)
								Score = 0;
							woman.get_shoot = true;
						}
					}
				}

			}

			// 【画子弹】

			if ((Bullet.b <= Constant.BULLET_QUANTITY) && (s <= Constant.GAME_TIME))
				if (Bullet.fire) // 首先确认是否已开火，然后再开始遍历所有子弹，并画出来
				{
					for (int i = 0; i < Bullets.size(); i++) {
						Bullet b = Bullets.get(i);
						b.draw_ObjectSelf(g);

						// 取出所有忍者
						for (int j = 0; j < Ninjas.size(); j++) // 这里的所有忍者已经初始化过了
						{
							Ninja ninja = Ninjas.get(j); // 也就是说虽然没画，但是它的坐标已经刻在背景中了
							// 碰撞检测
							if (ninja.draw && !ninja.get_shoot) // 所以要判断是否画出，再做碰撞检测
							{
								if (b.getRect().intersects(ninja.getRect())) // 检测
								{
									explode.drawexplode(g, ninja.x, ninja.y); // 爆炸动画
									Score = Score + 10; // 分数加10
									ninja.get_shoot = true; // 被击中，不再画这个
								}
							}
						}

						// 取出所有boss
						for (int k = 0; k < Bosses.size(); k++) {
							Boss boss = Bosses.get(k);

							// 碰撞检测
							if (boss.draw && !boss.get_shoot) {
								if (b.getRect().intersects(boss.getRect())) {
									explode.drawexplode(g, boss.x, boss.y);
									Score = Score + 50;
									boss.get_shoot = true;
								}
							}
						}

						// 取出所有女人
						for (int l = 0; l < Women.size(); l++) {
							Woman woman = Women.get(l);

							// 碰撞检测
							if (woman.draw && !woman.get_shoot) {
								if (b.getRect().intersects(woman.getRect())) {
									explode.drawexplode(g, woman.x, woman.y);
									if (Score >= 20)
										Score -= 30;
									if (Score < 20)
										Score = 0;
									woman.get_shoot = true;
								}
							}
						}

					}
				}

			// 根据时间画15个忍者，并移除时间到的忍者，每个敌人都是独立的！！！！都有各自的出现时间和存活时间!!!
			if ((Bullet.b <= Constant.BULLET_QUANTITY) && (s <= Constant.GAME_TIME)) {

				if (s >= 2 && s <= 4) {
					Ninjas.get(0).draw_ObjectSelf(g);
					Ninjas.get(0).draw = true;
				}
				if (s >= 4) {
					Ninjas.get(0).x = -300;
					Ninjas.get(0).y = -300;
				}
				if (s >= 5 && s <= 7) {
					Ninjas.get(1).draw_ObjectSelf(g);
					Ninjas.get(1).draw = true;
				}
				if (s >= 7) {
					Ninjas.get(1).x = -300;
					Ninjas.get(1).y = -300;
				}
				if (s >= 8 && s <= 10) {
					Ninjas.get(2).draw_ObjectSelf(g);
					Ninjas.get(2).draw = true;
				}
				if (s >= 10) {
					Ninjas.get(2).x = -300;
					Ninjas.get(2).y = -300;
				}
				if (s >= 11 && s <= 13) {
					Ninjas.get(3).draw_ObjectSelf(g);
					Ninjas.get(3).draw = true;
				}
				if (s >= 13) {
					Ninjas.get(3).x = -300;
					Ninjas.get(3).y = -300;
				}
				if (s >= 14 && s <= 16) {
					Ninjas.get(4).draw_ObjectSelf(g);
					Ninjas.get(4).draw = true;
				}
				if (s >= 16) {
					Ninjas.get(4).x = -300;
					Ninjas.get(4).y = -300;
				}

				if (s >= 17 && s <= 19) {
					Ninjas.get(5).draw_ObjectSelf(g);
					Ninjas.get(5).draw = true;
				}
				if (s >= 19) {
					Ninjas.get(5).x = -300;
					Ninjas.get(5).y = -300;
				}
				if (s >= 20 && s <= 22) {
					Ninjas.get(6).draw_ObjectSelf(g);
					Ninjas.get(6).draw = true;
				}
				if (s >= 22) {
					Ninjas.get(6).x = -300;
					Ninjas.get(6).y = -300;
				}

				if (s >= 21 && s <= 23) {
					Ninjas.get(7).draw_ObjectSelf(g);
					Ninjas.get(7).draw = true;
				}
				if (s >= 23) {
					Ninjas.get(7).x = -300;
					Ninjas.get(7).y = -300;
				}
				if (s >= 22 && s <= 24) {
					Ninjas.get(8).draw_ObjectSelf(g);
					Ninjas.get(8).draw = true;
				}
				if (s >= 24) {
					Ninjas.get(8).x = -300;
					Ninjas.get(8).y = -300;
				}
				if (s >= 22 && s <= 24) {
					Ninjas.get(9).draw_ObjectSelf(g);
					Ninjas.get(9).draw = true;
				}
				if (s >= 24) {
					Ninjas.get(9).x = -300;
					Ninjas.get(9).y = -300;
				}
				if (s >= 23 && s <= 25) {
					Ninjas.get(10).draw_ObjectSelf(g);
					Ninjas.get(10).draw = true;
				}
				if (s >= 25) {
					Ninjas.get(10).x = -300;
					Ninjas.get(10).y = -300;
				}
				if (s >= 25 && s <= 27) {
					Ninjas.get(11).draw_ObjectSelf(g);
					Ninjas.get(11).draw = true;
				}
				if (s >= 27) {
					Ninjas.get(11).x = -300;
					Ninjas.get(11).y = -300;
				}
				if (s >= 28 && s <= 30) {
					Ninjas.get(12).draw_ObjectSelf(g);
					Ninjas.get(12).draw = true;
				}
				if (s >= 30) {
					Ninjas.get(12).x = -300;
					Ninjas.get(12).y = -300;
				}
				if (s >= 29 && s <= 31) {
					Ninjas.get(13).draw_ObjectSelf(g);
					Ninjas.get(13).draw = true;
				}
				if (s >= 31) {
					Ninjas.get(13).x = -300;
					Ninjas.get(13).y = -300;
				}
				if (s >= 30 && s <= 32) {
					Ninjas.get(14).draw_ObjectSelf(g);
					Ninjas.get(14).draw = true;
				}
				if (s >= 32) {
					Ninjas.get(14).x = -300;
					Ninjas.get(14).y = -300;
				}

			}

			// 根据时间画5个Boss
			if ((Bullet.b <= Constant.BULLET_QUANTITY) && (s <= Constant.GAME_TIME)) {
				if (s >= 5 && s <= 8) {
					Bosses.get(0).draw_ObjectSelf(g);
					Bosses.get(0).draw = true;
				} else if (s >= 12 && s <= 15) {
					Bosses.get(1).draw_ObjectSelf(g);
					Bosses.get(1).draw = true;
				} else if (s >= 19 && s <= 22) {
					Bosses.get(2).draw_ObjectSelf(g);
					Bosses.get(2).draw = true;
				} else if (s >= 26 && s <= 29) {
					Bosses.get(3).draw_ObjectSelf(g);
					Bosses.get(3).draw = true;
				} else if (s >= 31 && s <= 34) {
					Bosses.get(4).draw_ObjectSelf(g);
					Bosses.get(4).draw = true;
				}
			}

			// 根据时间画5个人质
			/**
			 * 
			 * 
			 * 
			 */
			if ((Bullet.b <= Constant.BULLET_QUANTITY) && (s <= Constant.GAME_TIME)) {
				if (s >= 6 && s <= 8) {
					Women.get(0).draw_ObjectSelf(g);
					Women.get(0).draw = true;
				}
				if (s >= 8) {
					Women.get(0).x = -500;
					Women.get(0).y = -500;

				}
				if (s >= 11 && s <= 13) {
					Women.get(1).draw_ObjectSelf(g);
					Women.get(1).draw = true;
				}
				if (s >= 13) {
					Women.get(1).x = -500;
					Women.get(1).y = -500;
				}
				if (s >= 17 && s <= 19) {
					Women.get(2).draw_ObjectSelf(g);
					Women.get(2).draw = true;
				}
				if (s >= 19) {
					Women.get(2).x = -500;
					Women.get(2).y = -500;

				}
				if (s >= 25 && s <= 27) {
					Women.get(3).draw_ObjectSelf(g);
					Women.get(3).draw = true;
				}
				if (s >= 27) {
					Women.get(3).x = -500;
					Women.get(3).y = -500;
				}
				if (s >= 30 && s <= 32) {
					Women.get(4).draw_ObjectSelf(g);
					Women.get(4).draw = true;
				}
				if (s >= 32) {
					Women.get(4).x = -500;
					Women.get(4).y = -500;
				}
				// 根据时间移除未击中的人质
			}


			g.setFont(new Font("Dialog", 1, 100)); // 画游戏结束

			if(Bullet.b==20) {
				g.drawString("子弹已空" , 100, 400);
				g.drawString("请按空格结束游戏" , 100, 500);
			}

			if (( Bullet.fire && (Bullet.b > Constant.BULLET_QUANTITY)) | s == Constant.GAME_TIME) // 已开火&&已经达到子弹数为0

			{
				g.drawImage(gameover, 0, 0, 1020, 768, null);
				g.drawString("" + Score, 100, 720);
				MyGameFrame.run = false; // 结束线程
				over=true;               //游戏已结束

			}

		}

	}

	/*
	 *
	 * 【内部类】画图线程，让画面动起来
	 */
	public class PaintThread extends Thread {
		public void run() {
			while (run) {
				
				repaint(); // 手动重画
				ms += 42;
				s = (int) (ms / 1000);
				System.out.println(ms+"---"+s);
				try {
					Thread.sleep(Constant.SCREEN_REFRESH_RATE); // 延迟
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/*
	 * 
	 * 【内部类】定义键盘监听， 移动枪支
	 * 
	 */
	
	public class Key_monitor extends KeyAdapter {
		
		JFrame jf;													
		public Key_monitor(JFrame jf) {													//传框架
			this.jf=jf;
		}
		public void keyPressed(KeyEvent e) {
			if(!pause) {															//如果没有暂停
				gun_obj.gun_move(e);
				new Bullet().shoot(e);
				grenade.throwed(e); 
			}
			
			if( e.getKeyCode()==KeyEvent.VK_E && over) {    						 //退出游戏
				jf.dispose();
			}
			
			if(e.getKeyCode()==KeyEvent.VK_P  ){									//暂停游戏
				if(pause==false) {
					pause=true;
				}
				else {
					pause=false;

				}
			}

			
			if(e.getKeyCode()==KeyEvent.VK_R) {										//重新开始游戏
				if(over) {
					ms=0;
					s=0;
					Score=0;
					Bullet.b=0;
					Bullet.fire=false;
					Grenade.throwed=false;
					Explode.bomb=false;
					Explode.number=0;
					Bosses.removeAll(Bosses);
					Ninjas.removeAll(Ninjas);
					Women.removeAll(Women);
					Bullets.removeAll(Bullets);
					jf.dispose();
					MyGameFrame.run=true;                                          
					new MyGameFrame();
				}
			}
		}
		public void keyReleased(KeyEvent e) {
			gun_obj.gun_stop(e);

		} 

	}

}

