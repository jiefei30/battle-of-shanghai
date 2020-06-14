package weapon;
import javax.swing.*;

import people.BodyObject;

import java.awt.*;
import java.awt.event.*;
import shootballoon.Constant;
import shootballoon.MyGameFrame;
/*
 * 
 * 设Gun继承BodyObject
 * 
 */
public class Gun extends BodyObject{
	
	//构造方法，需要接受参数
	public Gun()
	{
		img=(new ImageIcon(Constant.IMAGEPATH+"gun.png")).getImage();	
		x=495;
		y=620;
	}
	
	//全局变量声明
	
	boolean left,right;

	
	static int x_gun;
	
	//画自己，被主框架paint调用
	
	public void draw_ObjectSelf(Graphics g)
	{
		int speed=20 ;
		
		if(left&&x>0)
		{
			x-=speed;
		}								//判断，更改速度
		
		if(right&&x<950) 
		{
			x+=speed;
		}							//判断，更改速度
		
		x_gun=x;
		
		g.drawImage(img,x, y, null);				//画
	}

	//设置移动按键判断，被主矿框架的按键监听调用
	public void gun_move(KeyEvent e)
	{
		switch (e.getKeyCode())
		{
		case KeyEvent.VK_LEFT:
			left=true;break;
		case KeyEvent.VK_RIGHT:
			right=true;break;
		}
	}
	//设置停止按键判断，被主矿框架的按键监听调用
	public void gun_stop(KeyEvent e)
	{
		switch (e.getKeyCode()) 
		{
		case KeyEvent.VK_LEFT:
			left=false;break;
		case KeyEvent.VK_RIGHT:
			right=false;break;
		}
	}
}
