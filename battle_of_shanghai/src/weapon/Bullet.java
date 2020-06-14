package weapon;
import javax.swing.*;
import shootballoon.*;
import people.BodyObject;

import java.awt.*;
import java.awt.event.*;
public class Bullet extends BodyObject{

	Image reloading =(new ImageIcon(Constant.IMAGEPATH+"bullet2.png")).getImage();

	public static int b=0;														//记录按下空格键的次数(发射的子弹个数)
	public static boolean fire=false;											//是否已经开火
	private int sec=0;													//射击延迟定义
	//初始构造
	public Bullet() 
	{
		img=(new ImageIcon(Constant.IMAGEPATH+"bullet.png")).getImage();	
		y=640;
		width=img.getWidth(null);
		height=img.getHeight(null);
		speed=70;
	}

	// 【次Paint】 画子弹
	public void draw_ObjectSelf(Graphics g) 
	{	
		y-= speed;	
		g.drawImage(img, x, y, width, height,null);

		for(int i=0;i<MyGameFrame.Bullets.size();i++)					//遍历子弹，删除已经越界的，或者以击中的
		{	
			Bullet b=MyGameFrame.Bullets.get(i);
			if(b.y<0)
			MyGameFrame.Bullets.remove(i);
		}
	}
	
	public void drawreloading(Graphics g)								//画剩余弹药
	{											
		int x=820,y=730;
		for(int i=0;i<(Constant.BULLET_QUANTITY-Bullet.b);i++) 
		{
		g.drawImage(reloading, x, y, null);
		y-=6;
		}
}
	
	public void shoot(KeyEvent e)
	{
		if(e.getKeyCode()==KeyEvent.VK_SPACE) 
		{
			if(MyGameFrame.ms>sec)    							//  控制发射频率    
			{
									//建立新对象，加到Bullets容器
				MyGameFrame.Bullets.add(new Bullet());

				MyGameFrame.Bullets.get(MyGameFrame.Bullets.size()-1).x=(Gun.x_gun+25);					//初始横坐标
				fire=true;               						 //开火已确认
				b++;
				sec=MyGameFrame.ms+300;
			}
		}


	}
}
