package weapon;
import shootballoon.Constant;
import shootballoon.Explode;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

import people.BodyObject;

public class Grenade extends BodyObject{
		
		public static boolean throwed=false;							//是否扔出
		
		public static int x_grenade;									//手雷横坐标
		
		//初始构造
		public Grenade() 
		{
			img=(new ImageIcon(Constant.IMAGEPATH+"grenade.png")).getImage();	
			y=640;
			width=img.getWidth(null);
			height=img.getHeight(null);
			speed=40;
		}
		
		//画自己
		public void draw_ObjectSelf(Graphics g) 
		{	
			
			if(y>160)
			{
			y-= speed;	
			g.drawImage(img, x_grenade, y, width, height,null);
			}
			else Explode.bomb=true;
		}
		
		//判断扔出监听
		public void throwed(KeyEvent e)
		{
			if(e.getKeyCode()==KeyEvent.VK_A) 
			{
				x_grenade=(Gun.x_gun+25);
				throwed=true;
			}
		}

}
